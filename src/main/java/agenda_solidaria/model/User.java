package agenda_solidaria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username",unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;
    
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "rol")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "login_intentos")
    private Integer loginIntentos;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhoneNumber> phones;


    public void incrementLoginIntentos() {
        if (this.loginIntentos == null)
            this.loginIntentos = 1;
        else
            this.loginIntentos = this.loginIntentos.intValue() + 1;
    }

    public void clearLogin() {
        this.setLoginIntentos(0);
        this.setEnabled(true);
    }
} 