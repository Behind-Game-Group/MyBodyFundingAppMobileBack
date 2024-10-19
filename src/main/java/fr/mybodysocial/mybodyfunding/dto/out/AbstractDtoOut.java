package fr.mybodysocial.mybodyfunding.dto.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AbstractDtoOut implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


}
