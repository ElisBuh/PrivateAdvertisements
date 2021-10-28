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
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "ad_seq", sequenceName = "ad_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", nullable = false)
    @ToString.Exclude
    private Advertisement advertisement;

    @Column(name = "content")
    private String content;

    @Column(name = "date_publication")
    private LocalDateTime dateCreate;

    public Comment() {

    }

    public Comment(Integer id, User user, String content, LocalDateTime dateCreate) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.dateCreate = dateCreate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(user, comment.user) && Objects.equals(advertisement, comment.advertisement) && Objects.equals(content, comment.content) && Objects.equals(dateCreate, comment.dateCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, advertisement, content, dateCreate);
    }
}
