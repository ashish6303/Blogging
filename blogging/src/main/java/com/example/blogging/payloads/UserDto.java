package com.example.blogging.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.SecondaryRows;

@NoArgsConstructor
@Getter
@Setter()
public class UserDto {
    private int id;

//    This is a validation.
    @NotEmpty
    private String name;

    @Email(message = "Email is not valid!!")
    private String email;

    @NotEmpty(message = "Password must be minimum of 6 characters")
    @Size(min = 5, max = 12)
    private String password;

    @NotEmpty
    private String about;
}
