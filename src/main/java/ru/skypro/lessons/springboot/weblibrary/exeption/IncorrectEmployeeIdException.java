package ru.skypro.lessons.springboot.weblibrary.exeption;

public class IncorrectEmployeeIdException extends RuntimeException{

    private final long employeeId;

    public IncorrectEmployeeIdException(long employeeId) {
        super("Incorrect employee ID: " + employeeId);
        this.employeeId = employeeId;
    }

    public long getEmployeeId() {
        return employeeId;
    }
}
