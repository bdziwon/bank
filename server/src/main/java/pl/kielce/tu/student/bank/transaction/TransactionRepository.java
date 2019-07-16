package pl.kielce.tu.student.bank.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.kielce.tu.student.bank.account.Account;

import java.util.List;


@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public List<Transaction> findAllByFromAccountOrToAccount(Account fromAccount, Account toAccount);

}
