import org.junit.jupiter.api.Test;
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
        Subtask subtask = new Subtask("Not Allowed", "Description", 2, Status.NEW, 1); // Attempt to use epic as subtask

        // Assuming taskManager is initialized
        TaskManager taskManager = Managers.getDefault();
        taskManager.createEpic(epic);

        // Subtask cannot reference itself as epic
        assertNotEquals(epic.getId(), subtask.getEpicId());
    }
}
