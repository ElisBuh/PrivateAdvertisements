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
@Table(name = "photographs")
public class Photograph {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "ad_seq", sequenceName = "ad_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_seq")
    private Integer id;

    @Column(name = "path")
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", nullable = false)
    @ToString.Exclude
    private Advertisement advertisement;


    public Photograph() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photograph that = (Photograph) o;
        return Objects.equals(id, that.id) && Objects.equals(path, that.path) && Objects.equals(advertisement, that.advertisement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path, advertisement);
    }
}
