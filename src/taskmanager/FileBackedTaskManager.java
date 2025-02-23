package taskmanager;

import task.Task;
import task.Status;
import task.Epic;
import task.Subtask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public Task createTask(Task task) {
        Task newTask = super.createTask(task);
        save();
        return newTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic newEpic = super.createEpic(epic);
        save();
        return newEpic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        Subtask newSubtask = super.createSubtask(subtask);
        save();
        return newSubtask;
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeSubtask(int id) {
        super.removeSubtask(id);
        save();
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("id,type,name,status,description,epic\n");
            for (Task task : getAllTasks()) {
                writer.write(toString(task) + "\n");
            }
            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic) + "\n");
            }
            for (Subtask subtask : getAllSubtasks()) {
                writer.write(toString(subtask) + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения данных", e);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines.subList(1, lines.size())) {
                Task task = fromString(line);
                if (task instanceof Epic) {
                    manager.createEpic((Epic) task);
                } else if (task instanceof Subtask) {
                    manager.createSubtask((Subtask) task);
                } else {
                    manager.createTask(task);
                }
            }
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка загрузки данных", e);
        }
        return manager;
    }

    private String toString(Task task) {
        if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            return String.join(",",
                    String.valueOf(subtask.getId()),
                    "SUBTASK",
                    subtask.getTitle(),
                    subtask.getStatus().name(),
                    subtask.getDescription(),
                    String.valueOf(subtask.getEpicId()));
        } else if (task instanceof Epic) {
            Epic epic = (Epic) task;
            return String.join(",",
                    String.valueOf(epic.getId()),
                    "EPIC",
                    epic.getTitle(),
                    epic.getStatus().name(),
                    epic.getDescription(),
                    "");
        } else {
            return String.join(",",
                    String.valueOf(task.getId()),
                    "TASK",
                    task.getTitle(),
                    task.getStatus().name(),
                    task.getDescription(),
                    "");
        }
    }

    private static Task fromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        String type = parts[1];
        String title = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];
        int epicId = parts.length > 5 ? Integer.parseInt(parts[5]) : 0;

        switch (type) {
            case "TASK":
                return new Task(title, description, id, status);
            case "EPIC":
                return new Epic(title, description, id);
            case "SUBTASK":
                return new Subtask(title, description, id, status, epicId);
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
