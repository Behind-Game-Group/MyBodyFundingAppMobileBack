package fr.mybodysocial.mybodyfunding.dto.in;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class ConnectionDtoIn {

    @NonNull
    private String email;

    @NonNull
    private String password;
}
