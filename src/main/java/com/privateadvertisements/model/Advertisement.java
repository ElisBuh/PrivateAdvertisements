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
import java.time.LocalDate;
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
    @ToString.Exclude
    private LocalDate datePublication;

    @Column(name = "date_publication_off")
    private LocalDate datePublicationOff;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusAd statusAd;

    @Column(name = "top_rating")
    private Boolean topRating;

    @Column(name = "date_top_off")
    @ToString.Exclude
    private LocalDateTime dateTopOff;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "advertisement", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Photograph> photographs;

    public Advertisement() {

    }

    public Advertisement(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.user = advertisement.getUser();
        this.comments = advertisement.getComments();
        this.category = advertisement.getCategory();
        this.title = advertisement.getTitle();
        this.content = advertisement.getContent();
        this.cost = advertisement.getCost();
        this.datePublication = advertisement.getDatePublication();
        this.datePublicationOff = advertisement.getDatePublicationOff();
        this.statusAd = advertisement.getStatusAd();
        this.topRating = advertisement.getTopRating();
        this.dateTopOff = advertisement.getDateTopOff();
        this.photographs = advertisement.getPhotographs();
    }

    public Advertisement(Integer id, List<Comment> comments, Category category, String title, String content, BigDecimal cost, LocalDate datePublication, LocalDate datePublicationOff, StatusAd statusAd, Boolean topRating, LocalDateTime dateTopOff, List<Photograph> photographs) {
        this.id = id;
        this.comments = comments;
        this.category = category;
        this.title = title;
        this.content = content;
        this.cost = cost;
        this.datePublication = datePublication;
        this.datePublicationOff = datePublicationOff;
        this.statusAd = statusAd;
        this.topRating = topRating;
        this.dateTopOff = dateTopOff;
        this.photographs = photographs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertisement that = (Advertisement) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(cost, that.cost) && statusAd == that.statusAd && Objects.equals(topRating, that.topRating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, title, content, cost, datePublicationOff, statusAd, topRating, dateTopOff);
    }
}
