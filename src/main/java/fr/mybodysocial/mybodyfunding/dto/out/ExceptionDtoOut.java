package fr.mybodysocial.mybodyfunding.dto.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import fr.mybodysocial.mybodyfunding.service.exception.EntityNotFoundException;
import fr.mybodysocial.mybodyfunding.service.exception.ParameterException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * The dto class for the exception
 */
@Data
@JsonInclude(Include.NON_NULL)
@Schema(description = "Represents an exception.")
public class ExceptionDtoOut implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger();

    @Schema(description = "The exception classe name.")
    private final String error;
    @Schema(description = "The exception package name.")
    private final String exceptionPackageName;
    @Schema(description = "The exception message.")
    private String exceptionMessage;
    @Schema(description = "The exception cause.")
    private String exceptionCause;
    @Schema(description = "The parameter name responsible of the exception.")
    private String targetedParameter;
    @Schema(description = "The element id responsible of the exception.")
    private String targetedEntityPk;
    @Schema(description = "The HTTP status, if any. Will use HttpServletResponse.SC_XXX")
    private Integer status;
    @Schema(description = "Date and time. Will format using DateTimeFormatter.ISO_LOCAL_DATE_TIME.")
    private final String timestamp;

    /**
     * Constructor of the object.
     */
    public ExceptionDtoOut() {
        super();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.error = this.getClass().getSimpleName();
        this.exceptionPackageName = this.getClass().getPackageName();
    }

    /**
     * Constructor of the object.
     *
     * @param pException where to find information for building the DTO
     */
    public ExceptionDtoOut(Throwable pException) {
        super();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.error = pException.getClass().getSimpleName();
        this.exceptionPackageName = pException.getClass().getPackageName();
        this.setExceptionMessage(pException.getMessage());
        this.setStatus(400);
        if (pException.getCause() != null) {
            try (var sw = new StringWriter(); var pw = new PrintWriter(sw)) {
                pException.getCause().printStackTrace(pw);
                this.setExceptionCause(sw.toString());
            } catch (Exception e) {
                ExceptionDtoOut.LOG.fatal("Erreur lors de la recuperation de la cause", e);
            }
        }
        if (pException instanceof ParameterException) {
            this.targetedParameter = ((ParameterException) pException).getParameterName();
        }
        if (pException instanceof EntityNotFoundException) {
            this.targetedEntityPk = String.valueOf(((EntityNotFoundException) pException).getEntityId());
        }
    }


}
