package by.svyat.redisdemo.repository;

import by.svyat.redisdemo.dto.UserDto;
import by.svyat.redisdemo.entity.UserEntity;
import io.netty.util.internal.ResourcesUtil;
import liquibase.license.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SimpleUserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserEntity getUserById(Long id) {
        String sql = loadSql("/sql/get_user_by_id.sql");

        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource().addValue("id", id),
                (rs, rowNum) -> new UserEntity(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birth_date").toLocalDate()
                )
        );
    }

    public List<UserEntity> getUsers() {
        String sql = loadSql("/sql/get_users.sql");

        return namedParameterJdbcTemplate.query(
                sql,
                (rs, rowNum) -> new UserEntity(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birth_date").toLocalDate()
                )
        );
    }

    public void saveUser(UserDto userDto) {
        String sql = loadSql("/sql/save_user.sql");

        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("first_name", userDto.getFirstName())
                        .addValue("last_name", userDto.getLastName())
                        .addValue("birth_date", userDto.getBirthDate())
        );
    }

    public int updateLastNameUser(Long id, UserDto userDto) {
        String sql = loadSql("/sql/update_last_name_for_user.sql");

        return namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("id", id)
                        .addValue("last_name", userDto.getLastName())

        );
    }

    public void deleteUser(Long id) {
        String sql = loadSql("/sql/delete_user.sql");

        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource().addValue("id", id)
        );
    }

    private String loadSql(String fileName) {
        try (InputStream input = new ClassPathResource(fileName).getInputStream()) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Ошибка загрузки SQL-файла: " + fileName, e);
        }
    }

}
