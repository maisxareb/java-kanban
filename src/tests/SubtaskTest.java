package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import task.Subtask;
import task.Epic;
import task.Status;

class SubtaskTest {
    @Test
    void testSubtaskEqualityById() {
        Subtask subtask1 = new Subtask("Subtask 1", "Description", 1, Status.NEW, 2);
        Subtask subtask2 = new Subtask("Subtask 1", "Description", 1, Status.NEW, 3);

        assertEquals(subtask1, subtask2);
    }

    @Test
    void testSubtaskCannotBeEpic() {
        Subtask subtask = new Subtask("Subtask 1", "Description", 1, Status.NEW, 2);
        Epic epic = new Epic("Epic 1", "Description", 2); // Этот эпик не назначен подзадаче

        assertNotEquals(epic.getId(), subtask.getEpicId(), "Subtask should not reference itself as an epic.");
    }
}
