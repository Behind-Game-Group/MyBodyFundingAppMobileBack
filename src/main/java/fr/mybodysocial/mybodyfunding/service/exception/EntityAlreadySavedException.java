package fr.mybodysocial.mybodyfunding.service.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityAlreadySavedException extends FonctionnelleException {


    public EntityAlreadySavedException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public EntityAlreadySavedException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public EntityAlreadySavedException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
