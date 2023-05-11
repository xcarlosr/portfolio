package br.com.gerencia.portfolio.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Carlos Roberto
 * @created 10/05/2023
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MembrosNaoSalvosResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private List<String> membrosNaoVinculados;
}
