package ru.skypro.lessons.springboot.weblibrary.exeption;

public class IncorrectEmployeeIdException extends RuntimeException{
    public IncorrectEmployeeIdException(int id) {
        super("Incorrect employee ID: " + id);
    }
}
