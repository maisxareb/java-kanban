package taskmanager;

import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_HISTORY_SIZE = 10;
    private final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        if (history.size() >= MAX_HISTORY_SIZE) {
            history.remove(0);
        }

        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
// Подскажите тесты ведь ломают мой проект, я думал вообше габелла будет. У меня же не запускается ничего.
//И по тестам я все сделал по инструкции, но отдельной папаки как SRС только Test не появляется.
//Я максимально запутался. Если вы можете, обьясните пожалуйста, что мне делать чтобы все грамотно было.