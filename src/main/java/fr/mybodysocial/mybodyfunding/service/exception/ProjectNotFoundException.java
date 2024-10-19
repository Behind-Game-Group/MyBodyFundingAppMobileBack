package fr.mybodysocial.mybodyfunding.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends AbstractFunctionalException {

    private static final long serialVersionUID = 1L;

    private final Serializable entityId;

    /**
     * Constructs a new exception with {@code null} as its detail message. The cause
     * is not initialized, and may subsequently be initialized by a call to
     * {@link #initCause}.
     */
    public ProjectNotFoundException() {
        this(null, null);
    }

    /**
     * Constructs a new exception with the specified detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to
     * {@link #initCause}.
     *
     * @param pMessage  the detail message. The detail message is saved for later
     *                  retrieval by the {@link #getMessage()} method.
     * @param pEntityId id used for searching entity
     */
    public ProjectNotFoundException(String pMessage, Serializable pEntityId) {
        super(pMessage);
        this.entityId = pEntityId;
    }

    /**
     * Gets the attribute value.
     *
     * @return the entityId value.
     */
    public Object getEntityId() {
        return this.entityId;
    }

}
