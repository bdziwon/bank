package pl.kielce.tu.student.bank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findAccountByLoginAndPassword(String login, String password);
    public Account findByLoginAndPassword(String login, String password);
    public Account findByLogin(String login);
    public Account findByNumber(Long number);
}
