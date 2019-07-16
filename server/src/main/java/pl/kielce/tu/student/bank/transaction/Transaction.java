package pl.kielce.tu.student.bank.transaction;


import lombok.*;
import pl.kielce.tu.student.bank.account.Account;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class Transaction {

    @Id @GeneratedValue
    private Long number;

    @ManyToOne
    @JoinColumn(name = "fromAccountId")
    @Getter @Setter
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "toAccountId")
    @Getter @Setter
    private Account toAccount;

    @Getter @Setter
    @Min(0)
    private double value = 0.0d;

    @Size(min = 1, max = 30)
    private @NonNull String title;

    //realized, in progress
    @Getter @Setter
    @Size(min = 1, max = 30)
    private @NonNull String realizationStatus = "in progress";


    private @NotNull LocalDateTime realizationDate = LocalDateTime.now();

    public Transaction(@NonNull String title, @NonNull Account fromAccount, @NonNull Account toAccount, double value) {
        this.title = title;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.value = value;
        this.realizationStatus = realizationStatus;
    }
}
