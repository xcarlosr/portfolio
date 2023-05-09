package br.com.gerencia.portfolio.mappers;


import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Carlos Roberto
 * @created 07/05/2023
 * @since 1.0
 */
public interface MapperBase {

    String localDateToString(LocalDate data);

    String bigDecimalToString(BigDecimal valor);

    String ofuscarCPF(String cpf);
}

