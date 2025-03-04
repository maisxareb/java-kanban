import task.Task;
import task.Epic;
import task.Subtask;
import task.Status;
import taskmanager.TaskManager;
import taskmanager.Managers;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = taskManager.createTask("Переезд", "Собрать коробки", Duration.ofMinutes(120), LocalDateTime.now());
        System.out.println("Создана задача: " + task1.getTitle() + ", Статус: " + task1.getStatus());

        Task task2 = taskManager.createTask("Курить мебель", "Купить стул и стулья", Duration.ofMinutes(90), LocalDateTime.now().plusHours(1));
        System.out.println("Создана задача: " + task2.getTitle() + ", статус: " + task2.getStatus());

        Epic epic = taskManager.createEpic("Организация праздника", "Подготовить к празднику");
        System.out.println("Создан эпик: " + epic.getTitle());

        Subtask subtask1 = taskManager.createSubtask("Пригласить гостей", "Составить список", epic.getId(), Duration.ofMinutes(30), LocalDateTime.now().plusDays(1));
        System.out.println("Создана подзадача: " + subtask1.getTitle() + ", статус: " + subtask1.getStatus());

        Subtask subtask2 = taskManager.createSubtask("Закупить еду", "Купить закуски", epic.getId(), Duration.ofMinutes(45), LocalDateTime.now().plusDays(1));
        System.out.println("Создана подзадача: " + subtask2.getTitle() + ", статус: " + subtask2.getStatus());

        System.out.println("\nВсе задачи: " + taskManager.getAllTasks());
        System.out.println("Все эпики: " + taskManager.getAllEpics());
        System.out.println("Все подзадачи: " + taskManager.getAllSubtasks());

        task1.updateStatus(Status.IN_PROGRESS);
        System.out.println("\nОбновлён статус задачи '" + task1.getTitle() + "' на: " + task1.getStatus());

        subtask1.updateStatus(Status.DONE);
        System.out.println("Обновлён статус подзадачи '" + subtask1.getTitle() + "' на: " + subtask1.getStatus());

        epic.updateEpicData();
        System.out.println("Обновлён статус эпика '" + epic.getTitle() + "' на: " + epic.getStatus());

        taskManager.removeTask(1);
        taskManager.removeEpic(epic.getId());
        System.out.println("\nУдалены задачи с ID 1 и эпик с ID " + epic.getId() + ".");

        System.out.println("Все задачи после удаления: " + taskManager.getAllTasks());
        System.out.println("Все эпики после удаления: " + taskManager.getAllEpics());
    }
}
