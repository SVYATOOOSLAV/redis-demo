package by.svyat.redisdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
