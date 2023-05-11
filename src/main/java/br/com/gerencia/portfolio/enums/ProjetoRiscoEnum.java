package br.com.gerencia.portfolio.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Carlos Roberto
 * @created 07/05/2023
 * @since 1.0
 */
public enum ProjetoRiscoEnum {

    BAIXO_RISCO("BAIXO_RISCO"), MEDIO_RISCO("MEDIO_RISCO"), ALTO_RISCO("ALTO_RISCO");

    private String risco;

    ProjetoRiscoEnum(String risco){
        this.risco = risco;
    }

    public static ProjetoRiscoEnum getRiscoEnum(String risco){
        for (ProjetoRiscoEnum value : ProjetoRiscoEnum.values()) {
            if(value.risco.equalsIgnoreCase(risco)){
                return value;
            }
        }
        throw new IllegalArgumentException("Não existe o risco informado: " + risco);
    }

    @JsonValue
    public String getRisto() {
        return risco;
    }

    @JsonCreator
    public static ProjetoRiscoEnum fromRisco(String risco) {
        for (ProjetoRiscoEnum enumValue : ProjetoRiscoEnum.values()) {
            if (enumValue.risco.equals(risco)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("Risco inválido: " + risco);
    }
}
