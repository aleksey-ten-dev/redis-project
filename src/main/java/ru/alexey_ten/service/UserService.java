package ru.alexey_ten.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.alexey_ten.entity.User;
import ru.alexey_ten.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        simulateSlowService();
        return userRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @CacheEvict(value = "users", key = "#user.id")
    public void deleteUserById(User user) {
        userRepository.delete(user);
    }

    @CachePut(value = "users", key = "#user.id")
    public void updateUser(User user) {
        User updatedUser = userRepository.findById(user.getId()).orElseThrow();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setPassword(user.getPassword());
        userRepository.save(user);
    }

    @CachePut(value = "users", key = "#user.id")
    public User createUser(User user) {
        return userRepository.save(user);
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
