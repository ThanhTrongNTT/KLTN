package hcmute.nhom.kltn.security.principal;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import hcmute.nhom.kltn.model.User;

/**
 * Class UserPrincipal.
 * Create by: IntelliJ IDEA
 * User     : trongnt
 * Date     : Thu, 9/22/2022
 * Time     : 07:27
 * Filename : UserPrinciple
 */
public class UserPrincipal implements UserDetails {
    private String id;
    private String email;
    private String password;

    private Boolean isActive;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UserPrincipal() {

    }

    /**
     * Constructor.
     *
     * @param id
     * @param email
     * @param password
     * @param authorities
     */
    public UserPrincipal(String id, String email, String password, Collection<? extends GrantedAuthority> authorities,
                         Boolean isActive) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    /**
     * Create UserPrincipal.
     *
     * @param user User
     * @return UserPrincipal
     */
    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());
        UserPrincipal userPrincipal = new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getIsActive()
        );
        return userPrincipal;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Boolean isActive() {
        return isActive;
    }
}
