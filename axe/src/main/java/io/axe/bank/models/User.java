package io.axe.bank.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

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

    private String phoneNumber;

    private boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime closedAt;

    @OneToMany
    private Set<Account> accounts;

}
