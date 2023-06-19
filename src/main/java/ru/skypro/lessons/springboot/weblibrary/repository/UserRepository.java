package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.pojo.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
