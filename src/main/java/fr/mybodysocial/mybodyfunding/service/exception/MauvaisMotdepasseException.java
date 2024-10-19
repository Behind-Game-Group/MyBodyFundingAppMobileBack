package fr.mybodysocial.mybodyfunding.service.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MauvaisMotdepasseException extends FonctionnelleException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public MauvaisMotdepasseException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public MauvaisMotdepasseException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public MauvaisMotdepasseException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
