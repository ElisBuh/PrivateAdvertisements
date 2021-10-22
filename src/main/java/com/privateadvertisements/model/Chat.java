package com.privateadvertisements.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "chat_seq", sequenceName = "chat_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Messages> messages;

    @ManyToMany(mappedBy = "chats", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<User> users;


    public Chat() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id) && Objects.equals(name, chat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
