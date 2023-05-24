package br.com.gerencia.portfolio.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Carlos Roberto
 * @created 21/05/2023
 * @since 1.0
 */
@Configuration
@EnableAspectJAutoProxy
@Transactional
@ComponentScan(basePackages = {"br.com.gerencia.portfolio"})
public class PortFolioApplicationTestContext {
}
