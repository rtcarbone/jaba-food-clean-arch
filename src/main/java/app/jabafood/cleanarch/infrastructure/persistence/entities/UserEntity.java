package app.jabafood.cleanarch.infrastructure.persistence.entities;

import app.jabafood.cleanarch.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String email;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = false, unique = true)
    private AddressEntity address;
}