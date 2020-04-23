package com.bet.matches.api.core.league;

import com.bet.matches.api.core.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "leagues")
@NoArgsConstructor
@AllArgsConstructor
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "users_leagues",
            joinColumns = @JoinColumn(name = "id_league"),
            inverseJoinColumns = @JoinColumn(name = "id_user"))
    private List<User> users;

    public void addUser(final User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

}
