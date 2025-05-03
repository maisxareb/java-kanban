package taskmanager;

import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import task.Subtask;
import java.io.IOException;
import java.util.List;

public class SubtasksHandler extends BaseHttpHandler {
    private final TaskManager manager;
    private static final Gson gson = new Gson();

    public SubtasksHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        try {
            switch (method) {
                case "GET": {
                    List<Subtask> list = (List<Subtask>) manager.getAllSubtasks();
                    sendText(exchange, gson.toJson(list));
                    break;
                }
                case "POST": {
                    String body = new String(exchange.getRequestBody().readAllBytes(), "UTF-8");
                    Subtask subtask = gson.fromJson(body, Subtask.class);
                    Subtask created = manager.createSubtask(
                            subtask.getTitle(),
                            subtask.getDescription(),
                            subtask.getEpicId(),
                            subtask.getDuration(),
                            subtask.getStartTime());
                    sendText(exchange, gson.toJson(created));
                    break;
                }
                case "DELETE": {
                    ((taskmanager.InMemoryTaskManager) manager).subtasks.clear();
                    sendText(exchange, "All subtasks deleted");
                    break;
                }
                default:
                    exchange.sendResponseHeaders(405, -1);
            }
        } catch (Exception e) {
            sendError(exchange);
        }
    }
}
