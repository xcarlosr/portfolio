package br.com.gerencia.portfolio.mappers;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Carlos Roberto
 * @created 07/05/2023
 * @since 1.0
 */
@Slf4j
@Component
public class MapperBaseImpl implements MapperBase {

    private static final String REGEX_CPF = "(\\d{3})(\\d{3})(\\d{3})(\\d{2})";

    @Override
    @Named(value = "localDateToString")
    public String localDateToString(LocalDate data) {
        return Objects.nonNull(data) ? data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }

    @Override
    @Named(value = "bigDecimalToString")
    public String bigDecimalToString(BigDecimal valor) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        format.setMaximumFractionDigits(2);
        return Objects.nonNull(valor) ? format.format(valor) : "0.00";
    }

    @Override
    @Named(value = "ofuscarCPF")
    public String ofuscarCPF(String cpf){
        return cpf.replaceAll("\\D+", "")
                .replaceAll(REGEX_CPF, "$1.***.***-$4");
    }

}
