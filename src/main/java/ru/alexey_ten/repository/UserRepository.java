package ru.alexey_ten.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexey_ten.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
