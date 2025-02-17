package task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collection;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description, int id) {
        super(title, description, id, Status.NEW);
    }

    public void addSubtask(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public void removeSubtask(int subtaskId) {
        subtaskIds.remove((Integer) subtaskId);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void updateStatus(Collection<Subtask> subtasks) {
        if (subtaskIds.isEmpty()) {
            updateStatus(Status.NEW);
            return;
        }

        boolean allDone = true;
        boolean anyInProgress = false;

        for (int subtaskId : subtaskIds) {
            Status subtaskStatus = getSubtaskStatus(subtasks, subtaskId);
            if (subtaskStatus == Status.DONE) {
                continue;
            } else if (subtaskStatus == Status.IN_PROGRESS) {
                anyInProgress = true;
                allDone = false;
                break;
            } else {
                allDone = false;
                break;
            }
        }

        if (allDone) {
            updateStatus(Status.DONE);
        } else if (anyInProgress) {
            updateStatus(Status.IN_PROGRESS);
        } else {
            updateStatus(Status.NEW);
        }
    }

    private Status getSubtaskStatus(Collection<Subtask> subtasks, int subtaskId) {
        for (Subtask subtask : subtasks) {
            if (subtask.getId() == subtaskId) {
                return subtask.getStatus();
            }
        }
        return Status.NEW;
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
