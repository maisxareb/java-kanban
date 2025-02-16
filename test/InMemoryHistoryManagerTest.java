import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import taskmanager.HistoryManager;
import taskmanager.InMemoryHistoryManager;
import task.Task;
import task.Status;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void testHistoryMaintainsAddedTasks() {
        Task task = new Task("Task for History", "History description", 1, Status.NEW);
        historyManager.add(task);

        assertEquals(1, historyManager.getHistory().size());
        assertEquals(task, historyManager.getHistory().get(0));
    }

    @Test
    void testHistoryLimitedToTen() {
        for (int i = 0; i < 12; i++) {
            Task task = new Task("Task " + i, "Description", i, Status.NEW);
            historyManager.add(task);
        }

        assertEquals(10, historyManager.getHistory().size());
    }

    @Test
    void testRemoveUpdatesHistoryCorrectly() {
        Task task1 = new Task("Task 1", "Description 1", 1, Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", 2, Status.NEW);

        historyManager.add(task1);
        historyManager.add(task2);

        historyManager.remove(task1.getId());
        assertEquals(1, historyManager.getHistory().size());
        assertEquals(task2, historyManager.getHistory().get(0));
    }
}
