package com.privateadvertisements.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "personal_info")
public class PersonalUserInfo {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Integer id;

    @Column(name = "first_name")
    private String fistName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_birthday")
    private LocalDate birthday;

    @Column(name = "number_phone")
    private Integer numberPhone;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    public PersonalUserInfo() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalUserInfo that = (PersonalUserInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(fistName, that.fistName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthday, that.birthday) && Objects.equals(numberPhone, that.numberPhone) && sex == that.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fistName, lastName, birthday, numberPhone, sex);
    }
}
