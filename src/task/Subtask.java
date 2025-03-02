package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String title, String description, int id, Status status, int epicId, Duration duration, LocalDateTime startTime) {
        super(title, description, id, status, duration, startTime);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return String.join(",",
                String.valueOf(getId()),
                getType().name(),
                getTitle(),
                getStatus().name(),
                getDescription(),
                String.valueOf(epicId),
                String.valueOf(getDuration().toMinutes()),
                getStartTime() != null ? getStartTime().toString() : "");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Subtask)) return false;
        Subtask other = (Subtask) obj;
        return this.getId() == other.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
