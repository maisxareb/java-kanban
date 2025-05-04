package taskmanager;

import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import task.Task;

import java.io.IOException;
import java.util.Collection;

public class PrioritizedHandler extends BaseHttpHandler {
    private final TaskManager manager;
    private static final Gson gson = new Gson();

    public PrioritizedHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            HttpMethod method = HttpMethod.valueOf(exchange.getRequestMethod());
            if (method == HttpMethod.GET) {
                Collection<Task> prioritizedTasks = manager.getPrioritizedTasks(); // предполагается такой метод
                sendText(exchange, gson.toJson(prioritizedTasks));
            } else {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
            }
        } catch (IllegalArgumentException e) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
        } catch (Exception e) {
            sendError(exchange);
        }
    }
}
