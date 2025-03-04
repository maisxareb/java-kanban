import org.junit.jupiter.api.Test;
import task.Subtask;
import task.Status;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtaskTest {

    @Test
    void testCreateSubtask() {
        int epicId = 1;
        Subtask subtask = new Subtask("Subtask Title", "Subtask Description", 2, Status.NEW, epicId, Duration.ofMinutes(20), LocalDateTime.now());

        assertEquals("Subtask Title", subtask.getTitle());
        assertEquals("Subtask Description", subtask.getDescription());
        assertEquals(2, subtask.getId());
        assertEquals(Status.NEW, subtask.getStatus());
        assertEquals(epicId, subtask.getEpicId());
        assertEquals(Duration.ofMinutes(20), subtask.getDuration());
        assertEquals(LocalDateTime.now().toLocalDate(), subtask.getStartTime().toLocalDate());
    }
}
