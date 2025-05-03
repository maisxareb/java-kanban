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
        String method = exchange.getRequestMethod();
        try {
            switch (method) {
                case "GET": {
                    List<Epic> list = (List<Epic>) manager.getAllEpics();
                    sendText(exchange, gson.toJson(list));
                    break;
                }
                case "POST": {
                    String body = new String(exchange.getRequestBody().readAllBytes(), "UTF-8");
                    Epic epic = gson.fromJson(body, Epic.class);
                    Epic created = manager.createEpic(epic.getTitle(), epic.getDescription());
                    sendText(exchange, gson.toJson(created));
                    break;
                }
                case "DELETE": {
                    ((taskmanager.InMemoryTaskManager) manager).epics.clear();
                    sendText(exchange, "All epics deleted");
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
