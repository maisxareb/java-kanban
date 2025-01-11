import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void testTaskEqualityById() {
        Task task1 = new Task("Task 1", "Description", 1, Status.NEW);
        Task task2 = new Task("Task 1", "Description", 1, Status.NEW);

        assertEquals(task1, task2);
    }
}
