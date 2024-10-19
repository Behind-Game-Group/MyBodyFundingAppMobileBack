package fr.mybodysocial.mybodyfunding.dto.out;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor(force = true)
public class LoginAppUserDtoOut {

    private String username;
    private String email;

    private Long id;

    private String jwtToken;

}
