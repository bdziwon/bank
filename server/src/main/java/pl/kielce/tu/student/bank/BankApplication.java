package pl.kielce.tu.student.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import pl.kielce.tu.student.bank.account.Account;
import pl.kielce.tu.student.bank.account.AccountRepository;
import pl.kielce.tu.student.bank.transaction.Transaction;
import pl.kielce.tu.student.bank.transaction.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Stream;

        @RestController
        @SpringBootApplication
        public class BankApplication {

            @Autowired
            PasswordEncoder passwordEncoder;

            public static void main(String[] args) {
                SpringApplication.run(BankApplication.class, args);
            }


            @Bean
            ApplicationRunner init(AccountRepository repository, TransactionRepository transactionRepository) {
                return args -> {
                    Account account = new Account();
                    account.setName("Bartek");
                    account.setSurname("Dziwoń");
                    account.setLogin("admin");
                    account.setPassword(passwordEncoder.encode("12345"));
                    account.addRole("ROLE_ADMIN");
                    account.setCreationDate(LocalDateTime.now());

                    account.setAccountType("Normal");
                    account.addRole("ROLE_"+account.getAccountType());

                    Account account1 = new Account("user", passwordEncoder.encode("12345"),
                            "Patrycja", "Fatyga", "Saving", 2, LocalDateTime.now());

                    account1.addRole("ROLE_USER");
                    account1.addRole("ROLE_"+account1.getAccountType());

                    repository.save(account);
                    repository.save(account1);

                    Transaction transaction = new Transaction("Zapłata za xxx", account, account1, 50);
                    transactionRepository.save(transaction);

                    Transaction transaction2 = new Transaction("Zapłata za xxx2", account, account1, 20);
                    transactionRepository.save(transaction2);

                    Transaction transaction3 = new Transaction("Zapłata za xxx3", account1, account, 100);
                    transactionRepository.save(transaction3);

                    repository.findAll().forEach(System.out::println);
                };
            }





        }