package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.pojo.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
