import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        System.out.println("Курсовая работа №2! Ежедневник!");

        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.println("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            TaskMetods.inputTask(scanner);
                            break;
                        case 2:
                            TaskMetods.removeTask(scanner);
                            break;
                        case 3:
                            TaskMetods.outputTasksForTheDay(scanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println(
                "\n1. Добавить задачу" +
                "\n2. Удалить задачу" +
                "\n3. Получить задачу на указаный день" +
                "\n0. Выход");
    }
}