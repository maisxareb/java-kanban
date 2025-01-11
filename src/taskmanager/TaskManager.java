package taskmanager;

import task.Task;
import task.Epic;
import task.Subtask;

import java.util.Collection;
import java.util.List;

public interface TaskManager {
    Task createTask(Task task);
    Epic createEpic(Epic epic);
    Subtask createSubtask(Subtask subtask);
    Task getTask(int id);
    Epic getEpic(int id);
    Subtask getSubtask(int id);
    void removeTask(int id);
    void removeEpic(int id);
    void removeSubtask(int id);
    Collection<Task> getAllTasks();
    Collection<Epic> getAllEpics();
    Collection<Subtask> getAllSubtasks();
    List<Task> getHistory(); // Новый метод для истории
}