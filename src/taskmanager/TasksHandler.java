package taskmanager;

import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import task.Task;

import java.io.IOException;
import java.util.List;

public class TasksHandler extends BaseHttpHandler {
    private final TaskManager manager;
    private static final Gson gson = new Gson();

    public TasksHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            HttpMethod method = HttpMethod.valueOf(exchange.getRequestMethod());
            switch (method) {
                case GET -> {
                    List<Task> tasks = (List<Task>) manager.getAllTasks();
                    sendText(exchange, gson.toJson(tasks));
                }
                case POST -> {
                    String body = new String(exchange.getRequestBody().readAllBytes(), "UTF-8");
                    Task task = gson.fromJson(body, Task.class);
                    Task created = manager.createTask(task.getTitle(), task.getDescription(), task.getDuration(), task.getStartTime());
                    sendText(exchange, gson.toJson(created));
                }
                case DELETE -> {
                    ((taskmanager.InMemoryTaskManager) manager).tasks.clear();
                    sendText(exchange, "All tasks deleted");
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
