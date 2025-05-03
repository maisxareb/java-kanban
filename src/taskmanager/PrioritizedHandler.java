package taskmanager;

import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Collection;
import task.Task;

public class PrioritizedHandler extends BaseHttpHandler {
    private final TaskManager manager;
    private static final Gson gson = new Gson();

    public PrioritizedHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                Collection<Task> history = manager.getHistory();
                sendText(exchange, gson.toJson(history));
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        } catch (Exception e) {
            sendError(exchange);
        }
    }
}
