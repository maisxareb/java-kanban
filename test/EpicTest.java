import task.Epic;
import task.Subtask;
import task.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taskmanager.Managers;
import taskmanager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

class EpicTest {

    @Test
    void testEpicEqualityById() {
        TaskManager taskManager = Managers.getDefault();
        taskManager.createEpic("Epic 1", "Description");
        Epic epic1 = taskManager.getEpic(1);
        Epic epic2 = taskManager.getEpic(1);

        Assertions.assertEquals(epic1, epic2, "Epics should be equal when fetched by the same ID.");
    }

    @Test
    void testEpicCannotBeSubtask() {
        TaskManager taskManager = Managers.getDefault();
        taskManager.createEpic("Epic 1", "Description");
        Epic epic = taskManager.getEpic(1);

        Subtask subtask = new Subtask("Not Allowed", "Description", 2, Status.NEW, epic.getId(), Duration.ofMinutes(30), LocalDateTime.now());

        Assertions.assertNotEquals(epic.getId(), subtask.getId(), "Epic ID should not equal Subtask's ID.");
    }

    @Test
    void testEpicSubtaskManagement() {
        TaskManager taskManager = Managers.getDefault();
        taskManager.createEpic("Epic 1", "Description");
        Epic epic = taskManager.getEpic(1);

        Subtask subtask1 = taskManager.createSubtask("Subtask 1", "Description", epic.getId(), Duration.ofMinutes(30), LocalDateTime.now());
        Subtask subtask2 = taskManager.createSubtask("Subtask 2", "Description", epic.getId(), Duration.ofMinutes(30), LocalDateTime.now());

        Assertions.assertEquals(2, epic.getSubtaskIds().size(), "Epic should have 2 subtasks.");
    }
}
