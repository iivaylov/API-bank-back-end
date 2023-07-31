package io.axe.bank.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String phoneNumber;

    @OneToOne
    private Address address;

    private boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime closedAt;

    @OneToMany
    private Set<Account> accounts;

}
