package br.com.gerencia.portfolio.enums;

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
}
