package pl.kielce.tu.student.bank.account;

import lombok.NonNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.student.bank.transaction.Transaction;
import pl.kielce.tu.student.bank.transaction.TransactionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private TransactionRepository transactionRepository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/accounts")
    public Collection<Account> accounts() {
        return repository.findAll();
    }

    @GetMapping("/")
    public Account home() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account loggedAccount = repository.findByLogin(user.getUsername());

        if (loggedAccount != null && !loggedAccount.getAccountType().equals("Normal")) {
            calculateInterests(loggedAccount);
        }



        return loggedAccount;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public int minusDays = 184;

    public void calculateInterests(Account loggedAccount) {

        LocalDateTime now = LocalDateTime.now();

        int investementLength = 183;

        Duration duration = Duration.between(loggedAccount.getCreationDate(), now.minusDays(minusDays));
        minusDays = 0;
        long diff = Math.abs(duration.toDays());
        double percentage = loggedAccount.getPercentage() / 100;

        while (diff >= 183) {

            double value = round(loggedAccount.getBalance() * ((investementLength * percentage * (1 - 0.19)) / 365), 2);

            loggedAccount.setBalance(
                    loggedAccount.getBalance() + (value));


            diff -= 183;

            Transaction transaction = new Transaction("Odsetki", repository.getOne(1L), loggedAccount, value);
            transactionRepository.save(transaction);

        }

        loggedAccount.setCreationDate(LocalDateTime.now());


    }

    @PostMapping("/register")
    public String register(@RequestBody String jsonStr) throws Exception {

        JSONObject jObject = new JSONObject(jsonStr);

        String login = jObject.getString("login");
        String password = jObject.getString("password");
        String repeatPassword = jObject.getString("repeatPassword");
        String name = jObject.getString("name");
        String surname = jObject.getString("surname");
        String accountType = jObject.getString("accountType");
        double percentage = 2;

        if (!password.equals(repeatPassword)) {
            return  "{\"status\":\"Passwords mismatched\"}";

        }

        if (password.length() < 5) {
            return  "{\"status\":\"Password too short\"}";
        }

        if (repository.findByLogin(login) != null) {
            return  "{\"status\":\"Username taken\"}";
        }

        Account acc = new Account(login, passwordEncoder.encode(password), name, surname, accountType, percentage, LocalDateTime.now());
        acc.addRole("ROLE_" + accountType);
        acc.addRole("ROLE_USER");

        try {
            repository.save(acc);
        } catch (TransactionSystemException e) {
            final String[] result = {""};
            if (e.getRootCause() instanceof ConstraintViolationException) {
                ConstraintViolationException ex = (ConstraintViolationException) e.getRootCause();
                ex.getConstraintViolations().forEach(constraintViolation ->
                        result[0] += " [" + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage() + "] ");
            }

            return  "{\"status\":\""+result[0]+"\"}";
        }

        return  "{\"status\":\"OK\"}";
    }
}
