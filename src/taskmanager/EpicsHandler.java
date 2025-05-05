package taskmanager;

import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import task.Epic;
import java.io.IOException;
import java.util.List;

public class EpicsHandler extends BaseHttpHandler {
    private final TaskManager manager;
    private static final Gson gson = new Gson();

    public EpicsHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            HttpMethod method = HttpMethod.valueOf(exchange.getRequestMethod());
            switch (method) {
                case GET -> {
                    List<Epic> list = (List<Epic>) manager.getAllEpics();
                    sendText(exchange, gson.toJson(list));
                }
                case POST -> {
                    String body = new String(exchange.getRequestBody().readAllBytes(), "UTF-8");
                    Epic epic = gson.fromJson(body, Epic.class);
                    Epic created = manager.createEpic(epic.getTitle(), epic.getDescription());
                    sendText(exchange, gson.toJson(created));
                }
                case DELETE -> {
                    ((taskmanager.InMemoryTaskManager) manager).epics.clear();
                    sendText(exchange, "All epics deleted");
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
