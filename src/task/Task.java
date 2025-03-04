package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String title;
    private String description;
    private final int id;
    private Status status;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String title, String description, int id, Status status, Duration duration, LocalDateTime startTime) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.startTime != null ? this.startTime.plus(this.duration) : null;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public int getId() {
        return this.id;
    }

    public Status getStatus() {
        return this.status;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public TaskType getType() {
        return TaskType.TASK;
    }

    @Override
    public String toString() {
        return String.join(",",
                String.valueOf(this.id),
                getType().name(),
                this.title,
                this.status.name(),
                this.description,
                String.valueOf(this.duration.toMinutes()),
                this.startTime != null ? this.startTime.toString() : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return this.id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
