import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TaskMetods {
    public static Map<Integer, Task> dailyPlanner = new HashMap<>();

    public static LocalDate getNextDate(Task task){
        return getNextDate(task, 1);
    }
    public static LocalDate getNextDate(Task task, int count) {

        LocalDate nextDate = task.getDateStart();

        switch (task.getRepeatability()) {
            case "ежедневная":
                    nextDate = nextDate.plusDays(1 * count);

                break;
            case "еженедельная":
                    nextDate = nextDate.plusDays(7 * count);
                break;
            case "ежемесечная":
                    nextDate = nextDate.plusMonths(1 * count);
                break;
            case "ежегодная":
                    nextDate = nextDate.plusYears(1 * count);
                break;
        }
        return nextDate;
    }

    public static void removeTask(Scanner scanner) {
        String forTheCodeToWorkCorretly = scanner.nextLine(); // без этой строки пропускает scanner для dateString
        System.out.println("Введите название задачи, которую надо удалить: ");
        String taskName = scanner.nextLine();

        for (Map.Entry<Integer, Task> task : TaskMetods.dailyPlanner.entrySet()) {
            if (taskName.equalsIgnoreCase(task.getValue().getName())) {
                TaskMetods.dailyPlanner.remove(task.getKey());
                System.out.println("Задача " + taskName + " удалена");
                break;
            } else {
                System.out.println("Не удалось удалить задачу. Задачи " + taskName + " нет");
            }
        }
    }


    public static void inputTask(Scanner scanner) {
        String forTheCodeToWorkCorretly = scanner.nextLine(); // без этой строки пропускает scanner для taskName
        Task task = new Task();
        while (true) {
            try {
                System.out.println("Введите название задачи: ");
                String taskName = scanner.nextLine();
                task.setName(taskName);
                break;
            } catch (NoDataEnteredException e) {
                System.err.println("Название задачи не введено");
            }
        }

        while (true) {
            try {
                System.out.println("Введите описание задачи: ");
                String taskDescription = scanner.nextLine();
                task.setDescription(taskDescription);
                break;
            } catch (NoDataEnteredException e) {
                System.err.println("Описание задачи не введено");
            }
        }

        while (true) {
            try {
                System.out.println("Введите тип задачи (рабочая или личная): ");
                String taskType = scanner.nextLine();
                task.setTypeTask(taskType);
                break;
            } catch (NoDataEnteredException e) {
                System.err.println("Не указан тип задачи");
            } catch (IncorrectInputException e) {
                System.err.println("Неверно указан тип задачи. Задача может быть рабочая или личная");
            }
        }

        while (true) {
            try {
                System.out.println("Введите дату выполнения задачи (в формате 12.03.2022): ");
                String dateString = scanner.nextLine();
                if (dateString.matches("[0-3][0-9.][0-3][0-9.][1-2][0-9]{3}")) {
                    throw new IncorrectInputException();
                } else if (dateString == null || dateString.isBlank() || dateString.isEmpty()) {
                    throw new NoDataEnteredException();
                } else {
                    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate date = LocalDate.parse(dateString, formatterDate);
                    task.setDateStart(date);
                }
                break;
            } catch (NoDataEnteredException e) {
                System.err.println("Дата не введена");
            } catch (IncorrectInputException e) {
                System.err.println("Дата введена некорректно");
            } catch (DateTimeParseException e) {
                System.err.println("Дата введена некорректно");
            }
        }

        while (true) {
            try {
                System.out.println("Введите время выполнения задачи (в формате 12:00): ");
                String timeString = scanner.nextLine();
                if (timeString.matches("[0-2][0-9:][0-5][0-9]")) {
                    throw new IncorrectInputException();
                } else if (timeString == null || timeString.isBlank() || timeString.isEmpty()) {
                    throw new NoDataEnteredException();
                } else {
                    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime time = LocalTime.parse(timeString, formatterTime);
                    task.setTime(time);
                }
                break;
            } catch (NoDataEnteredException e) {
                System.err.println("Время не указана");
            } catch (IncorrectInputException e) {
                System.err.println("Время введено некорректно");
            } catch (DateTimeParseException e) {
                System.err.println("Время введено некорректно");
            }
        }

        while (true) {
            try {
                System.out.println("Введите переодичность задачи (однократная, ежедневная, еженедельная, ежемесечная, ежегодная): ");
                String taskRepeatability = scanner.nextLine();
                task.setRepeatability(taskRepeatability);
                break;
            } catch (NoDataEnteredException e) {
                System.err.println("Не указана переодичность задачи");
            } catch (IncorrectInputException e) {
                System.err.println("Неверно указана переодичность задачи. " +
                        "Задача может быть однократная, ежедневная, еженедельная, ежемесечная или ежегодная");
            }
        }
    }

    public static void outputTasksForTheDay(Scanner scanner) {
        String forTheCodeToWorkCorretly = scanner.nextLine(); // без этой строки пропускает scanner для dateString
        while (true) {
            try {
                System.out.println("Введите дату: ");
                String dateString = scanner.nextLine();
                if (dateString.matches("[0-3][0-9.][0-3][0-9.][1-2][0-9]{3}")) {
                    throw new IncorrectInputException();
                } else if (dateString == null || dateString.isBlank() || dateString.isEmpty()) {
                    throw new NoDataEnteredException();
                } else {
                    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate date = LocalDate.parse(dateString, formatterDate);
                    System.out.println("Задачи на " + date.format(formatterDate));

                    int resoult;
                    int count = 0;

                    for (Map.Entry<Integer, Task> entry : TaskMetods.dailyPlanner.entrySet()) {

                        resoult = date.compareTo(entry.getValue().getDateStart());

                        if (resoult == 0) {
                            System.out.println(entry.getValue().getName() + " " + entry.getValue().getTime());
                            count++;
                        } else if (resoult > 0) {
                            int i = 1;
                            do {
                                resoult = date.compareTo(TaskMetods.getNextDate(entry.getValue(), i));
                                if (resoult == 0) {
                                    System.out.println(entry.getValue().getName() + " " + entry.getValue().getTime());
                                    count++;
                                }
                                i++;
                            } while (resoult > 0);
                        }
                    }

                    if (count == 0) {
                        System.out.println("Нет задач");
                    }
                }
                break;
            } catch (NoDataEnteredException e) {
                System.err.println("Дата не введена");
            } catch (IncorrectInputException e) {
                System.err.println("Дата введена некорректно");
            } catch (DateTimeParseException e) {
                System.err.println("Дата введена некорректно");
            }
        }
    }
}