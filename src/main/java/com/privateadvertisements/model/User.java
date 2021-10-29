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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    public User() {
    }

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Integer id;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String passwords;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "date_registered")
    @ToString.Exclude
    private LocalDateTime dateRegistered;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<CreditCard> creditCards;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "personal_info_id")
    @ToString.Exclude
    private PersonalUserInfo personalUserInfo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "users_chats",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    @ToString.Exclude
    private Set<Chat> chats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Messages> messages;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Advertisement> advertisements;

    public User(User user) {
        this.id = user.getId();
        this.roles = user.getRoles();
        this.login = user.getLogin();
        this.passwords = user.passwords;
        this.rating = user.rating;
        this.enabled = user.enabled;
        this.dateRegistered = user.getDateRegistered();
        this.address = user.getAddress();
        this.creditCards = user.creditCards;
        this.personalUserInfo = user.getPersonalUserInfo();
        this.chats = user.chats;
        this.messages = user.messages;
        this.comments = user.comments;
        this.advertisements = user.advertisements;
    }

    public User(Integer id, Set<Role> roles, String login, String passwords, Integer rating, Boolean enabled, LocalDateTime dateRegistered, Address address, List<CreditCard> creditCards, PersonalUserInfo personalUserInfo, Set<Chat> chats, List<Messages> messages, List<Advertisement> advertisements) {
        this.id = id;
        this.roles = roles;
        this.login = login;
        this.passwords = passwords;
        this.rating = rating;
        this.enabled = enabled;
        this.dateRegistered = dateRegistered;
        this.address = address;
        this.creditCards = creditCards;
        this.personalUserInfo = personalUserInfo;
        this.chats = chats;
        this.messages = messages;
        this.advertisements = advertisements;
    }

    public User(Integer id, Set<Role> roles, String login, String passwords, Integer rating, Boolean enabled, LocalDateTime dateRegistered, Address address, List<CreditCard> creditCards, PersonalUserInfo personalUserInfo, Set<Chat> chats, List<Messages> messages) {
        this.id = id;
        this.roles = roles;
        this.login = login;
        this.passwords = passwords;
        this.rating = rating;
        this.enabled = enabled;
        this.dateRegistered = dateRegistered;
        this.address = address;
        this.creditCards = creditCards;
        this.personalUserInfo = personalUserInfo;
        this.chats = chats;
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(roles, user.roles) && Objects.equals(login, user.login) && Objects.equals(passwords, user.passwords) && Objects.equals(rating, user.rating) && Objects.equals(enabled, user.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, passwords, rating, enabled, address, personalUserInfo);
    }
}
