import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private int nextId = 0;

    public Task createTask(Task task) {
        tasks.put(++nextId, task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        epics.put(++nextId, epic);
        return epic;
    }

    public Subtask createSubtask(Subtask subtask) {
        subtasks.put(++nextId, subtask);
        if (epics.containsKey(subtask.getEpicId())) {
            epics.get(subtask.getEpicId()).addSubtask(subtask.getId());
            epics.get(subtask.getEpicId()).updateStatus();
        }
        return subtask;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.updateStatus();
        }
    }

    public Collection<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Collection<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public Collection<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public Collection<Subtask> getSubtasksByEpicId(int epicId) {
        List<Subtask> epicSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                epicSubtasks.add(subtask);
            }
        }
        return epicSubtasks;
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeEpic(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }

    public void removeSubtask(int id) {
        subtasks.remove(id);
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }
}