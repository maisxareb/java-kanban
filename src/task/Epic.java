package task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description, int id) {
        super(title, description, id, Status.NEW);
    }

    public void addSubtask(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void updateStatus() {
        if (subtaskIds.isEmpty()) {
            updateStatus(Status.NEW);
        } else {
            boolean allDone = true;
            boolean anyInProgress = false;

            for (int subtaskId : subtaskIds) {
            }
            if (allDone) {
                updateStatus(Status.DONE);
            } else if (anyInProgress) {
                updateStatus(Status.IN_PROGRESS);
            } else {
                updateStatus(Status.NEW);
            }
        }
    }
}
