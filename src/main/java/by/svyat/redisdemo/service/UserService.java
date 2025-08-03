package by.svyat.redisdemo.service;

import by.svyat.redisdemo.dto.UserDto;
import by.svyat.redisdemo.entity.UserEntity;
import by.svyat.redisdemo.repository.SimpleUserRepository;
import by.svyat.redisdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final SimpleUserRepository simpleUserRepository;

    @Cacheable(cacheNames = "events", key = "#id")
    public UserEntity getUser(Long id) {
        log.debug("Cache missing get user from database with id {}", id);
        return simpleUserRepository.getUserById(id);
    }

    public List<UserEntity> getAllUsers() {
        return simpleUserRepository.getUsers();
    }

    public void saveUser(UserDto userDto) {
        simpleUserRepository.saveUser(userDto);
    }

    public void updateLastNameForUser(Long id, UserDto userDto) {
        int cnt = simpleUserRepository.updateLastNameUser(id, userDto);
        if (cnt > 0) {
            log.debug("Successfully updated last name for user with id {}", id);
        } else {
            log.debug("Failed to update last name for user, possible user not found with id {}", id);
        }
    }

    public void deleteUser (Long id) {
        simpleUserRepository.deleteUser(id);
    }

}
