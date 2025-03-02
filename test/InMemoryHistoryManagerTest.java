import org.junit.jupiter.api.Test;
import task.Task;
import task.Status;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    @Test
    void testCreateTask() {
        Task task = new Task("Task Title", "Task Description", 1, Status.NEW, Duration.ofMinutes(30), LocalDateTime.now());

        assertEquals("Task Title", task.getTitle());
        assertEquals("Task Description", task.getDescription());
        assertEquals(1, task.getId());
        assertEquals(Status.NEW, task.getStatus());
        assertEquals(Duration.ofMinutes(30), task.getDuration());
    }
}
