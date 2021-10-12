package com.privateadvertisements.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "advertisements")
public class Advertisement {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "ad_seq", sequenceName = "ad_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "advertisement", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Comment> comments;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "date_publication")
    private LocalDateTime datePublication;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusAd statusAd;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "advertisement", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Photograph> photographs;

    public Advertisement() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertisement that = (Advertisement) o;
        return id.equals(that.id) && Objects.equals(user, that.user) && Objects.equals(category, that.category) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(cost, that.cost) && Objects.equals(datePublication, that.datePublication) && statusAd == that.statusAd;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, category, title, content, cost, datePublication, statusAd);
    }
}
