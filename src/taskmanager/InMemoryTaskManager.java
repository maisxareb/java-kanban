package taskmanager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private List<Task> history = new ArrayList<>();
    private int nextId = 0;

    @Override
    public Task createTask(Task task) {
        tasks.put(++nextId, task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epics.put(++nextId, epic);
        return epic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        subtasks.put(++nextId, subtask);
        if (epics.containsKey(subtask.getEpicId())) {
            epics.get(subtask.getEpicId()).addSubtask(subtask.getId());
            epics.get(subtask.getEpicId()).updateStatus();
        }
        return subtask;
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            addToHistory(task);
        }
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            addToHistory(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            addToHistory(subtask);
        }
        return subtask;
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }

    @Override
    public void removeSubtask(int id) {
        subtasks.remove(id);
    }

    @Override
    public Collection<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Collection<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    private void addToHistory(Task task) {
        if (history.size() >= 10) {
            history.remove(0);
        }
        history.add(task);
    }
}
//с