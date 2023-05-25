package com.application.piunivesp.model;

import com.application.piunivesp.resources.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@SQLDelete(sql = "UPDATE usuarios SET deletado = CURRENT_TIMESTAMP WHERE usr_id = ?")
@Where(clause = "DELETADO IS NULL")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuarios")
public class User extends AbstractEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usr_seq")
    @SequenceGenerator(name = "usr_seq", allocationSize = 1, sequenceName = "usr_seq")
    @Column(name = "usr_id")
    private Long id;

    @Column(name = "usr_nome")
    private String name;

    @Column(name = "usr_email", unique = true)
    private String email;

    @Column(name = "usr_password")
    private String password;

    @Column(name = "usr_authorities")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority(this.role.name()));
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return this.name;
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
}
