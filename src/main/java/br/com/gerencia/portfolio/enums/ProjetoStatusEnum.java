package br.com.gerencia.portfolio.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Carlos Roberto
 * @created 08/05/2023
 * @since 1.0
 */
public enum ProjetoStatusEnum {

    EM_ANALISE("EM_ANALISE"),
    ANALIS_REALIZADA("ANALIS_REALIZADA"),
    ANALISE_APROVADA("ANALISE_APROVADA"),
    INICIADO("INICIADO"),
    PLANEJADO("PLANEJADO"),
    EM_ANDAMENTO("EM_ANDAMENTO"),
    ENCERRADO("ENCERRADO"),
    CANCELADO("CANCELADO");

    private String status;

    ProjetoStatusEnum(String status) {
        this.status = status;
    }

    public static ProjetoStatusEnum getStatusEnum(String status) {
        for (ProjetoStatusEnum value : ProjetoStatusEnum.values()) {
            if (value.status.equalsIgnoreCase(status)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Não existe o status informado: " + status);
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static ProjetoStatusEnum fromStatus(String status) {
        for (ProjetoStatusEnum enumValue : ProjetoStatusEnum.values()) {
            if (enumValue.status.equals(status)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + status);
    }

}
