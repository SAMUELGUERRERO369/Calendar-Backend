package com.example.demo.dtos.auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Username no puede estar vacío")
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank(message = "Password no puede estar vacío")
    @Size(min = 8, max = 72)
    private String password;
}