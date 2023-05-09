package br.com.gerencia.portfolio.mappers;


import br.com.gerencia.portfolio.enums.ProjetoRiscoEnum;
import br.com.gerencia.portfolio.enums.ProjetoStatusEnum;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Carlos Roberto
 * @created 07/05/2023
 * @since 1.0
 */
public interface MapperBase {

    String localDateToString(LocalDate data);

    LocalDate dataStringToLocalDate(String data);

    String bigDecimalToString(BigDecimal valor);

    BigDecimal decimalStringToBigDecimal(String valor);
    String ofuscarCPF(String cpf);
}

