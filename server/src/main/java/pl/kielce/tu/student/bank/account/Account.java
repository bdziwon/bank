package pl.kielce.tu.student.bank.account;


import lombok.*;
import org.hibernate.Transaction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class Account {

    @Id @GeneratedValue
    private Long number;

    @Getter @Setter
    private double balance = 100.0d;

    @Getter @Setter
    private double percentage = 0.0d;

    @Size(min = 1, max = 20)
    private @NonNull String name;

    @Size(min = 1, max = 20)
    private @NonNull String surname;

    @Getter @Setter
    @Size(min = 4, max = 20)
    private @NonNull String login;

    @Getter @Setter
    private @NonNull String password;

    @Getter @Setter
    @Size(min = 4)
    private @NonNull String roles = "";

    //Normal, Investement, Saving
    @Getter @Setter
    private @NonNull String accountType;

    private @NotNull LocalDateTime creationDate;

    public Account(@NonNull String login, @NonNull String password, @NonNull String name, @NonNull String surname, @NonNull String accountType, double percentage, LocalDateTime creationDate) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.accountType = accountType;
        this.percentage = percentage;
        this.creationDate = creationDate;
    }

    public void addRole(String role) {
        this.roles += role + ",";
    }

    public List<String> getRoles() {

        List<String> roles = new ArrayList<>();
        for (String role : this.roles.split(",")
             ) {
            if (role.length() <= 1) {
                continue;
            }
            roles.add(role);
        }

        return roles;
    }
}
