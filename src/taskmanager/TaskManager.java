package taskmanager;

import task.Epic;
import task.Subtask;
import task.Task;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;

public interface TaskManager {
    Task createTask(String title, String description, Duration duration, LocalDateTime startTime);

    Epic createEpic(String title, String description);

    Subtask createSubtask(String title, String description, int epicId, Duration duration, LocalDateTime startTime);

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    Subtask getSubtaskById(int subtaskId);

    void removeTask(int id);

    void removeEpic(int id);

    void removeSubtask(int id);

    Collection<Task> getAllTasks();

    Collection<Epic> getAllEpics();

    Collection<Subtask> getAllSubtasks();

    Collection<Task> getPrioritizedTasks();

    Collection<Task> getHistory();
}
