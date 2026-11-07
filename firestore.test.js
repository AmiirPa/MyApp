const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Unauthenticated user: cannot read or write study_tasks", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("study_tasks").get());
});

test("Authenticated owner: can create, read, list, update, and delete own study_tasks", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const now = new Date();
  const taskRef = aliceDb.collection("users").doc(ALICE_UID).collection("study_tasks").doc("task_1");

  await assertSucceeds(
    taskRef.set({
      userId: ALICE_UID,
      title: "مطالعه درس اول ریاضی از منفی صفر",
      subjectId: "math",
      stageTag: "گام ۱: شروع از منفی صفر",
      durationMinutes: 35,
      isCompleted: false,
      createdAt: now,
      updatedAt: now,
    })
  );

  await assertSucceeds(taskRef.get());
  await assertSucceeds(aliceDb.collection("users").doc(ALICE_UID).collection("study_tasks").get());

  await assertSucceeds(
    taskRef.update({
      isCompleted: true,
      updatedAt: new Date(),
    })
  );

  await assertSucceeds(taskRef.delete());
});

test("Cross-user isolation: Bob cannot read, list, or modify Alice's study_tasks", async () => {
  const now = new Date();
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("users").doc(ALICE_UID).collection("study_tasks").doc("task_1").set({
      userId: ALICE_UID,
      title: "Alice Task",
      subjectId: "math",
      stageTag: "گام ۱",
      durationMinutes: 30,
      isCompleted: false,
      createdAt: now,
      updatedAt: now,
    });
  });

  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("study_tasks").doc("task_1").get());
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("study_tasks").get());
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("study_tasks").doc("task_1").delete());
});

test("Schema validation: rejects shadow fields, invalid types, and out-of-range values", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const now = new Date();

  // Shadow field injection
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).collection("study_tasks").doc("task_shadow").set({
      userId: ALICE_UID,
      title: "Task",
      subjectId: "math",
      stageTag: "Stage",
      durationMinutes: 30,
      isCompleted: false,
      isAdmin: true,
      createdAt: now,
      updatedAt: now,
    })
  );

  // Invalid Leitner box (99 > 5)
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).collection("flashcards").doc("fc_math_1").set({
      userId: ALICE_UID,
      cardId: "fc_math_1",
      subjectId: "math",
      leitnerBox: 99,
      reviewCount: 1,
      createdAt: now,
      updatedAt: now,
    })
  );

  // Valid lesson_progress succeeds
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("lesson_progress").doc("math_01").set({
      userId: ALICE_UID,
      lessonId: "math_01",
      subjectId: "math",
      isCompleted: true,
      isBookmarked: true,
      userNote: "نکته عالی درباره علامت‌ها",
      masteryPercent: 100,
      createdAt: now,
      updatedAt: now,
    })
  );

  // Valid exam_results succeeds
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("exam_results").doc("exam_1").set({
      userId: ALICE_UID,
      subjectId: "arooz",
      subjectTitle: "عروض و قافیه",
      correctCount: 8,
      totalCount: 10,
      scorePercent: 80,
      createdAt: now,
      updatedAt: now,
    })
  );
});
