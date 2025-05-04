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
        try {
            HttpMethod method = HttpMethod.valueOf(exchange.getRequestMethod());
            switch (method) {
                case GET -> {
                    List<Subtask> list = (List<Subtask>) manager.getAllSubtasks();
                    sendText(exchange, gson.toJson(list));
                }
                case POST -> {
                    String body = new String(exchange.getRequestBody().readAllBytes(), "UTF-8");
                    Subtask subtask = gson.fromJson(body, Subtask.class);
                    Subtask created = manager.createSubtask(
                            subtask.getTitle(),
                            subtask.getDescription(),
                            subtask.getEpicId(),
                            subtask.getDuration(),
                            subtask.getStartTime());
                    sendText(exchange, gson.toJson(created));
                }
                case DELETE -> {
                    ((taskmanager.InMemoryTaskManager) manager).subtasks.clear();
                    sendText(exchange, "All subtasks deleted");
                }
                default -> {
                    exchange.sendResponseHeaders(405, -1);
                    exchange.close();
                }
            }
        } catch (IllegalArgumentException e) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
        } catch (Exception e) {
            sendError(exchange);
        }
    }
}
