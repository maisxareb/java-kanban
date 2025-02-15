package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;
import task.Epic;
import task.Subtask;
import task.Status;
import taskmanager.TaskManager;
import taskmanager.Managers;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    @Test
    void testCreateAndGetDifferentTaskTypes() {
        Task task = new Task("Task 1", "Description", 1, Status.NEW);
        Epic epic = new Epic("Epic 1", "Epic Description", 2);
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description", 3, Status.NEW, 2);

        taskManager.createTask(task);
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask);

        assertEquals(task, taskManager.getTask(1));
        assertEquals(epic, taskManager.getEpic(2));
        assertEquals(subtask, taskManager.getSubtask(3));
    }

    @Test
    void testTaskNonConflictById() {
        Task task1 = new Task("Task 1", "Description", 1, Status.NEW);
        Task task2 = new Task("Task 2", "Description", 2, Status.NEW);

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        assertEquals(task1, taskManager.getTask(1));
        assertEquals(task2, taskManager.getTask(2));
    }

    @Test
    void testImmutableTaskUponAddition() {
        Task task = new Task("Test Task", "Test Description", 1, Status.NEW);
        taskManager.createTask(task);

        Task retrievedTask = taskManager.getTask(1);
        assertNotSame(task, retrievedTask, "Task should not be the same instance as the added task");
        assertEquals(task.getTitle(), retrievedTask.getTitle());
        assertEquals(task.getDescription(), retrievedTask.getDescription());
        assertEquals(task.getStatus(), retrievedTask.getStatus());
    }

    @Test
    void testRemoveSubtaskUpdatesEpic() {
        Epic epic = new Epic("Epic 1", "Description", 1);
        Subtask subtask = new Subtask("Subtask 1", "Description", 2, Status.NEW, epic.getId());

        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask);

        assertEquals(1, epic.getSubtaskIds().size()); // Проверяем, что подзадача добавлена

        taskManager.removeSubtask(subtask.getId());
        assertTrue(epic.getSubtaskIds().isEmpty()); // Убедимся, что подзадача удалена
    }
}
