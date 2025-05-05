package taskmanager;

import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import task.Task;

import java.io.IOException;
import java.util.Collection;

public class HistoryHandler extends BaseHttpHandler {
    private final TaskManager manager;
    private static final Gson gson = new Gson();

    public HistoryHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            HttpMethod method = HttpMethod.valueOf(exchange.getRequestMethod());
            if (method == HttpMethod.GET) {
                Collection<Task> history = manager.getHistory();
                sendText(exchange, gson.toJson(history));
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
