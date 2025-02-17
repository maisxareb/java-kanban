package task;

import java.util.Objects;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String title, String description, int id, Status status, int epicId) {
        super(title, description, id, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;
        Subtask subtask = (Subtask) o;
        return getId() == subtask.getId() &&  // Сравнение по ID
                epicId == subtask.epicId &&   // Сравнение по epicId
                getTitle().equals(subtask.getTitle()) &&
                getDescription().equals(subtask.getDescription()) &&
                getStatus() == subtask.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), epicId, getTitle(), getDescription(), getStatus());
    }
}
