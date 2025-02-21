import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import task.Subtask;
import task.Epic;
import task.Status;

class SubtaskTest {
    @Test
    void testSubtaskEqualityById() {
        Subtask subtask1 = new Subtask("Subtask 1", "Description", 1, Status.NEW, 2);
        Subtask subtask2 = new Subtask("Subtask 2", "Description", 3, Status.NEW, 2);

        assertNotEquals(subtask1, subtask2, "Subtasks with different IDs should not be equal.");
    }

    @Test
    void testSubtaskCannotBeEpic() {
        Subtask subtask = new Subtask("Subtask 1", "Description", 1, Status.NEW, 3);
        Epic epic = new Epic("Epic 1", "Description", 2); // Эпик с id = 2
        assertNotEquals(epic.getId(), subtask.getEpicId(), "Subtask should not reference itself as an epic.");
    }
}
