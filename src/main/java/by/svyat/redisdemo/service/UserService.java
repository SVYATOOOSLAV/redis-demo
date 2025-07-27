package by.svyat.redisdemo.service;

import by.svyat.redisdemo.dto.UserDto;
import by.svyat.redisdemo.entity.UserEntity;
import by.svyat.redisdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final RedisService redisService;
    private final UserRepository userRepository;

    public UserDto getUser(Long id){
        UserEntity user = redisService.getUser(id);

        if(user == null){
            log.info("Cache missing get user from database with id {}", id);
            user = userRepository.findById(id).orElse(null);

            if(user != null){
                redisService.saveUser(user);
            }
        }

        return new UserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth()
        );
    }

    public void saveUser(UserDto userDto){
        userRepository.save(
                UserEntity.builder()
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .dateOfBirth(userDto.getBirthDate())
                .build()
        );
    }
}
