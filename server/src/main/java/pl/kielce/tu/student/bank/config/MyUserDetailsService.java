package pl.kielce.tu.student.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.student.bank.account.Account;
import pl.kielce.tu.student.bank.account.AccountRepository;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    //
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Account account = accountRepository.findByLogin(username);
        if (account == null) {
            throw new UsernameNotFoundException(
                    "No user found with login: "+ username);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return  new org.springframework.security.core.userdetails.User
                (account.getLogin(),
                        account.getPassword(), enabled, accountNonExpired,
                        credentialsNonExpired, accountNonLocked,
                        getAuthorities(account.getRoles()));
    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}