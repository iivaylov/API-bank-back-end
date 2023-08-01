package io.axe.bank.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "town_name")
    private String townName;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "country")
    private String country;

}
