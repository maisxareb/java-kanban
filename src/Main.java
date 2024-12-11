public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Переезд", "Собрать коробки", 1, Status.NEW);
        taskManager.createTask(task1);
        System.out.println("Создана задача: " + task1.getTitle() + ", Статус: " + task1.getStatus());

        Task task2 = new Task("Курить мебель", "Купить стул и стулья", 2, Status.NEW);
        taskManager.createTask(task2);
        System.out.println("Создана задача: " + task2.getTitle() + ", статус: " + task2.getStatus());

        Epic epic = new Epic("Организация праздника", "Подготовить к празднику", 3);
        taskManager.createEpic(epic);
        System.out.println("Создан эпик: " + epic.getTitle());

        Subtask subtask1 = new Subtask("Пригласить гостей", "Составить список", 4, Status.NEW, 3);
        taskManager.createSubtask(subtask1);
        System.out.println("Создана подзадача: " + subtask1.getTitle() + ", статус: " + subtask1.getStatus());

        Subtask subtask2 = new Subtask("Закупить еду", "Купить закуски", 5, Status.NEW, 3);
        taskManager.createSubtask(subtask2);
        System.out.println("Создана подзадача: " + subtask2.getTitle() + ", статус: " + subtask2.getStatus());

        System.out.println("\nВсе задачи: " + taskManager.getAllTasks());
        System.out.println("Все эпики: " + taskManager.getAllEpics());
        System.out.println("Все подзадачи: " + taskManager.getAllSubtasks());

        task1.updateStatus(Status.IN_PROGRESS);
        System.out.println("\nОбновлён статус задачи '" + task1.getTitle() + "' на: " + task1.getStatus());

        subtask1.updateStatus(Status.DONE);
        System.out.println("Обновлён статус подзадачи '" + subtask1.getTitle() + "' на: " + subtask1.getStatus());

        epic.updateStatus();
        System.out.println("Обновлён статус эпика '" + epic.getTitle() + "' на: " + epic.getStatus());

        taskManager.removeTask(1);
        taskManager.removeEpic(3);
        System.out.println("\nУдалены задачи с ID 1 и эпик с ID 3.");

        System.out.println("Все задачи после удаления: " + taskManager.getAllTasks());
        System.out.println("Все эпики после удаления: " + taskManager.getAllEpics());
    }
}