package Test;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Status;
import taskmanager.TaskManager;
import taskmanager.Managers;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    @Test
    void testEpicEqualityById() {
        Epic epic1 = new Epic("Epic 1", "Description", 1);
        Epic epic2 = new Epic("Epic 1", "Description", 1);

        assertEquals(epic1, epic2);
    }

    @Test
    void testEpicCannotBeSubtask() {
        Epic epic = new Epic("Epic 1", "Description", 1);
        Subtask subtask = new Subtask("Not Allowed", "Description", 2, Status.NEW, 1);

        TaskManager taskManager = Managers.getDefault();
        taskManager.createEpic(epic);

        assertNotEquals(epic.getId(), subtask.getEpicId());
    }

    @Test
    void testEpicSubtaskManagement() {
        Epic epic = new Epic("Epic 1", "Description", 1);
        Subtask subtask1 = new Subtask("Subtask 1", "Description", 2, Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Description", 3, Status.NEW, epic.getId());

        TaskManager taskManager = Managers.getDefault();
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        assertEquals(2, epic.getSubtaskIds().size());
    }

    @Test
    void testEpicNoStaleSubtaskIdsOnRemove() {
        Epic epic = new Epic("Epic 1", "Description", 1);
        Subtask subtask1 = new Subtask("Subtask 1", "Description", 2, Status.NEW, epic.getId());

        TaskManager taskManager = Managers.getDefault();
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);

        taskManager.removeSubtask(subtask1.getId());
        assertTrue(epic.getSubtaskIds().isEmpty());
    }
}
