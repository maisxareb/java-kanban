package taskmanager;

import task.Task;
import task.Epic;
import task.Subtask;
import task.Status;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final Path filePath;

    public FileBackedTaskManager(String fileName) {
        this.filePath = Path.of(fileName);
        loadTasks();
    }

    public static FileBackedTaskManager loadFromFile(String fileName) {
        return new FileBackedTaskManager(fileName);
    }

    private void loadTasks() {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");

                if (parts.length < 7) continue;

                String taskType = parts[1];
                System.out.println("Loading line: " + line);
                switch (taskType) {
                    case "TASK":
                        addTaskFromLine(parts);
                        break;
                    case "EPIC":
                        addEpicFromLine(parts);
                        break;
                    case "SUBTASK":
                        addSubtaskFromLine(parts);
                        break;
                    default:
                        System.err.println("Unknown task type: " + taskType);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
    }

    private void addTaskFromLine(String[] parts) {
        try {
            String title = parts[2];
            String description = parts[3];
            int id = Integer.parseInt(parts[0]);
            Status status = Status.valueOf(parts[4]);
            Duration duration = Duration.ofMinutes(Long.parseLong(parts[5]));
            LocalDateTime startTime = "null".equals(parts[6]) ? null : LocalDateTime.parse(parts[6]);

            Task newTask = new Task(title, description, id, status, duration, startTime);
            tasks.put(id, newTask);
        } catch (NumberFormatException e) {
            System.err.println("Number format error while parsing task from line: " + String.join(",", parts));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Array index out of bounds while parsing task from line: " + String.join(",", parts));
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument error while parsing task from line: " + String.join(",", parts));
        }
    }

    private void addEpicFromLine(String[] parts) {
        try {
            String title = parts[2];
            String description = parts[3];
            int id = Integer.parseInt(parts[0]);

            if (epics.containsKey(id)) {
                System.err.println("Epic with ID " + id + " already exists, skipping...");
                return;
            }

            Epic newEpic = createEpic(title, description);
            epics.put(id, newEpic);
            System.out.println("Added Epic: " + newEpic);
        } catch (NumberFormatException e) {
            System.err.println("Number format error while parsing epic from line: " + String.join(",", parts));
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument error while parsing epic from line: " + String.join(",", parts));
        }
    }


    private void addSubtaskFromLine(String[] parts) {
        try {
            String title = parts[2];
            String description = parts[3];
            int epicId = Integer.parseInt(parts[4]);
            int id = Integer.parseInt(parts[0]);
            Duration duration = Duration.ofMinutes(Long.parseLong(parts[6]));
            LocalDateTime startTime = "null".equals(parts[7]) ? null : LocalDateTime.parse(parts[7]);
            Subtask newSubtask = new Subtask(title, description, id, Status.valueOf(parts[5]), epicId, duration, startTime);
            subtasks.put(id, newSubtask);
            Epic epic = epics.get(epicId);
            if (epic != null) {
                epic.addSubtask(id);
                epic.updateEpicData();
            }
        } catch (NumberFormatException e) {
            System.err.println("Number format error while parsing subtask from line: " + String.join(",", parts));
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument error while parsing subtask from line: " + String.join(",", parts));
        }
    }

    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Task task : getAllTasks()) {
                writer.write(taskToString(task));
                writer.newLine();
            }
            for (Epic epic : getAllEpics()) {
                writer.write(epicToString(epic));
                writer.newLine();
            }
            for (Subtask subtask : getAllSubtasks()) {
                writer.write(subtaskToString(subtask));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    private String taskToString(Task task) {
        return String.join(",",
                String.valueOf(task.getId()),
                "TASK",
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                String.valueOf(task.getDuration().toMinutes()),
                task.getStartTime() != null ? task.getStartTime().toString() : "null"  // Корректно обработать startTime
        );
    }

    private String epicToString(Epic epic) {
        return String.join(",",
                String.valueOf(epic.getId()),
                "EPIC",
                epic.getTitle(),
                epic.getDescription(),
                epic.getStatus().name(),
                epic.getDuration() != null ? String.valueOf(epic.getDuration().toMinutes()) : "0", // Изменено, чтобы избежать NullPointerException
                epic.getStartTime() != null ? epic.getStartTime().toString() : "null"
        );
    }

    private String subtaskToString(Subtask subtask) {
        return String.join(",",
                String.valueOf(subtask.getId()),
                "SUBTASK",
                subtask.getTitle(),
                subtask.getDescription(),
                String.valueOf(subtask.getEpicId()),
                subtask.getStatus().name(),
                String.valueOf(subtask.getDuration().toMinutes()),
                subtask.getStartTime() != null ? subtask.getStartTime().toString() : "null"
        );
    }
}
