package ru.skypro.lessons.springboot.weblibrary.exeption;

public class IncorrectAvatarIdException extends  RuntimeException{
    public IncorrectAvatarIdException(long id) {
        super("Incorrect avatar ID: " + id);
    }
}

