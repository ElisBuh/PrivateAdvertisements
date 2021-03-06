package com.privateadvertisements.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id", nullable = false)
    @ToString.Exclude
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", nullable = false)
    @ToString.Exclude
    private City city;

    @Column(name = "post_index")
    private Integer postIndex;

    @Column(name = "street")
    private String street;

    @Column(name = "number_house")
    private Integer numHouse;

    @Column(name = "number_flat")
    private Integer numFlat;

    public Address() {

    }

    public Address(Country country, City city, Integer postIndex, String street, Integer numHouse, Integer numFlat) {
        this.country = country;
        this.city = city;
        this.postIndex = postIndex;
        this.street = street;
        this.numHouse = numHouse;
        this.numFlat = numFlat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(postIndex, address.postIndex) && Objects.equals(street, address.street) && Objects.equals(numHouse, address.numHouse) && Objects.equals(numFlat, address.numFlat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, postIndex, street, numHouse, numFlat);
    }
}
