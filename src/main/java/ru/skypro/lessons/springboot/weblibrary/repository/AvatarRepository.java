package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.pojo.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
