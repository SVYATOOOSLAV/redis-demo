package by.svyat.redisdemo.service;

import by.svyat.redisdemo.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, UserEntity> redisTemplate;

    public UserEntity getUser(Long id) {
        UserEntity user = redisTemplate.opsForValue().get(String.valueOf(id));
        return user;
    }

    public void saveUser(UserEntity user) {
        redisTemplate.opsForValue().set(String.valueOf(user.getId()), user, Duration.ofSeconds(5));
    }
}
