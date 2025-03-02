package taskmanager;

import task.Task;
import task.Epic;
import task.Subtask;
import task.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, Subtask> subtasks = new HashMap<>();
    protected int nextId = 1;

    @Override
    public Task createTask(String title, String description, Duration duration, LocalDateTime startTime) {
        int id = nextId++;
        Task newTask = new Task(title, description, id, Status.NEW, duration, startTime);
        tasks.put(id, newTask);
        return newTask;
    }

    @Override
    public Epic createEpic(String title, String description) {
        int id = nextId++;
        Epic newEpic = new Epic(title, description, id, this);
        epics.put(id, newEpic);
        return newEpic;
    }

    @Override
    public Subtask createSubtask(String title, String description, int epicId, Duration duration, LocalDateTime startTime) {
        int id = nextId++;
        Subtask newSubtask = new Subtask(title, description, id, Status.NEW, epicId, duration, startTime);
        subtasks.put(id, newSubtask);

        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.addSubtask(id);
            epic.updateEpicData();
        }

        return newSubtask;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        return subtasks.get(subtaskId);
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
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(id);
                epic.updateEpicData();
            }
        }
    }

    @Override
    public Task getTask(int id) {
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        return subtasks.get(id);
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
    public Collection<Task> getPrioritizedTasks() {
        TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())));
        prioritizedTasks.addAll(getAllTasks());
        prioritizedTasks.addAll(getAllSubtasks());
        return prioritizedTasks;
    }

    @Override
    public Collection<Task> getHistory() {
        return new ArrayList<>();
    }
}
