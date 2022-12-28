import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class Task {

    private String name;
    private String description;
    private String typeTask;
    private LocalDate dateStart;
    private LocalTime time;
    private String repeatability;
    private int id;

    public Task(){
        generatorId();
        TaskMetods.dailyPlanner.put(this.id, this);
    }

    public Task(String name, String description, String typeTask,
                LocalDate date, LocalTime time, String repeatability) {
        try{
            setName(name);
        }catch (NoDataEnteredException e){
            System.err.println("Название задачи не введено");
        }

        try{
        setDescription(description);
        }catch (NoDataEnteredException e){
            System.err.println("Опсание задачи не введено");
        }

        try{
        setTypeTask(typeTask);
        }catch (NoDataEnteredException e){
            System.err.println("Не указан тип задачи");
        }catch (IncorrectInputException e){
            System.err.println("Не верно указан тип задачи");
        }

        this.dateStart = date;
        this.time = time;

        try{
            setRepeatability(repeatability);
        }catch (NoDataEnteredException e){
            System.err.println("Не указана переодичность задачи");
        }catch (IncorrectInputException e){
            System.err.println("Не верно указана переодичность задачи");
        }

        generatorId();
        TaskMetods.dailyPlanner.put(this.id, this);
    }

    private void generatorId() {
        do{
            this.id = (int)(Math.random()*1000000);
        }while (searchMatchesId(this.id) || !Pattern.matches("[1-9]{1}[0-9]{5}", this.id + ""));
    }

    private boolean searchMatchesId(int id){
        boolean matches = false;
        for (int key : TaskMetods.dailyPlanner.keySet()) {
            if (key == id){
                matches = true;
                break;
            }
        }
        return matches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NoDataEnteredException {
        if (name != null && !name.isEmpty() && !name.isBlank()) {
            this.name = name;
        } else {
            throw new NoDataEnteredException();
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws NoDataEnteredException {
        if (description != null && !description.isBlank() && !description.isEmpty()) {
            this.description = description;
        } else {
            throw new NoDataEnteredException();
        }
    }

    public String getTypeTask() {
        return typeTask;
    }

    public void setTypeTask(String typeTask) throws NoDataEnteredException, IncorrectInputException{
        if (typeTask == null || typeTask.isEmpty() || typeTask.isBlank()) {
            throw new NoDataEnteredException();
        } else if (!typeTask.equalsIgnoreCase("рабочая") &&
                !typeTask.equalsIgnoreCase("личная")) {
            throw new IncorrectInputException();
        } else {
            this.typeTask = typeTask;
        }
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getRepeatability() {
        return repeatability;
    }

    public void setRepeatability(String repeatability) throws NoDataEnteredException, IncorrectInputException{
        if (repeatability == null || repeatability.isEmpty() || repeatability.isBlank()) {
            throw new NoDataEnteredException();
        } else {
            switch (repeatability) {
                case "однократная":
                case "ежедневная":
                case "еженедельная":
                case "ежемесечная":
                case "ежегодная":
                    this.repeatability = repeatability;
                    break;
                default:
                    throw new IncorrectInputException();
            }
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() { // переделать
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", typeTask='" + typeTask + '\'' +
                ", date='" + dateStart + '\'' +
                ", time='" + time + '\'' +
                ", repeatability='" + repeatability + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}