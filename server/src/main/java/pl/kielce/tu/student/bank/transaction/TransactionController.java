package pl.kielce.tu.student.bank.transaction;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.student.bank.account.Account;
import pl.kielce.tu.student.bank.account.AccountRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private  AccountRepository accountRepository;

    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/transactions")
    public List<Transaction> transactions() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account loggedAccount = accountRepository.findByLogin(user.getUsername());

        List<Transaction> transactions = repository.findAllByFromAccountOrToAccount(loggedAccount, loggedAccount);
        transactions = transactions.stream().sorted(Comparator.comparing(Transaction::getRealizationDate).reversed())
                .collect(Collectors.toList());

        return transactions;
    }

    @Transactional
    @PostMapping("/transfer")
    public String transferMoney(@RequestBody String jsonStr) throws JSONException {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account loggedAccount = accountRepository.findByLogin(user.getUsername());

        if (loggedAccount.getAccountType().equals("Investement")) {
            return  "{\"status\":\"Unsupported\"}";
        }

        JSONObject jObject = new JSONObject(jsonStr);

        int value = jObject.getInt("amount");
        Long toAccountNumber = jObject.getLong("toAccount");
        String title = jObject.getString("title");


        if (value <= 0) {
            return  "{\"status\":\"Amount too small\"}";
        }

        if (loggedAccount.getBalance() - value < 0) {
            return  "{\"status\":\"Unsufficient funds\"}";
        }

        Account toAccount = accountRepository.findByNumber(toAccountNumber);

        if (toAccount == null) {
            return  "{\"status\":\"Account number does not exist\"}";
        }

        if (toAccount.getAccountType().equals("Investement")) {
            return  "{\"status\":\"Can't send money to investement account\"}";
        }

        Transaction transaction = new Transaction(title, loggedAccount, toAccount, value);
        if (realizeTransaction(transaction)) {
            return  "{\"status\":\"OK\"}";

        } else {
            return  "{\"status\":\"ERROR\"}";

        }

    }

    @Transactional
    public Boolean realizeTransaction(Transaction transaction) {
        repository.save(transaction);
        Account fromAccount = transaction.getFromAccount();
        fromAccount.setBalance(fromAccount.getBalance() - transaction.getValue());

        Account toAccount = transaction.getToAccount();
        toAccount.setBalance(toAccount.getBalance() + transaction.getValue());

        transaction.setRealizationStatus("realized");
        return true;

    }

}
