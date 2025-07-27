package by.svyat.redisdemo.service;

import by.svyat.redisdemo.dto.UserDto;
import by.svyat.redisdemo.entity.UserEntity;
import by.svyat.redisdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Cacheable(cacheNames = "events", key = "#id")
    public UserEntity getUser(Long id) {
        log.debug("Cache missing get user from database with id {}", id);
        return userRepository.findById(id).orElse(null);
    }

    public void saveUser(UserDto userDto) {
        userRepository.save(
                UserEntity.builder()
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .dateOfBirth(userDto.getBirthDate())
                        .build()
        );
    }
}
