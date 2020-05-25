package sas.data.models;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;


    @Column
    @Transient
    private boolean isAccountNonExpired;

    @Column
    @Transient
    private boolean isAccountNonLocked;

    @Column
    @Transient
    private boolean isCredentialsNonExpired;

    @Column
    @Transient
    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> authorities;

    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    public void addAuthority(Role authority) {
        this.authorities.add(authority);
    }


}
