package task;

import taskmanager.TaskManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();
    private final TaskManager taskManager;

    public Epic(String title, String description, int id, TaskManager taskManager) {
        super(title, description, id, Status.NEW, Duration.ZERO, null);
        this.taskManager = taskManager;
    }

    public void addSubtask(int subtaskId) {
        this.subtaskIds.add(subtaskId);
        updateEpicData();
    }

    public void removeSubtask(int subtaskId) {
        this.subtaskIds.remove((Integer) subtaskId);
        updateEpicData();
    }

    public List<Integer> getSubtaskIds() {
        return this.subtaskIds;
    }

    public void updateEpicData() {
        Duration totalDuration = Duration.ZERO;
        LocalDateTime earliestStartTime = null;
        LocalDateTime latestEndTime = null;

        for (int subtaskId : this.subtaskIds) {
            Subtask subtask = taskManager.getSubtaskById(subtaskId);
            totalDuration = totalDuration.plus(subtask.getDuration());
            LocalDateTime subtaskStartTime = subtask.getStartTime();
            if (earliestStartTime == null || (subtaskStartTime != null && subtaskStartTime.isBefore(earliestStartTime))) {
                earliestStartTime = subtaskStartTime;
            }
            LocalDateTime subtaskEndTime = subtask.getEndTime();
            if (latestEndTime == null || (subtaskEndTime != null && subtaskEndTime.isAfter(latestEndTime))) {
                latestEndTime = subtaskEndTime;
            }
        }

        setDuration(totalDuration);
        setStartTime(earliestStartTime);
    }

    @Override
    public LocalDateTime getEndTime() {
        return getStartTime() != null ? getStartTime().plus(getDuration()) : null;
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        return String.join(",",
                String.valueOf(getId()),
                getType().name(),
                getTitle(),
                getStatus().name(),
                getDescription(),
                "",
                String.valueOf(getDuration().toMinutes()),
                getStartTime() != null ? getStartTime().toString() : "");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Epic)) return false;
        Epic other = (Epic) obj;
        return this.getId() == other.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
