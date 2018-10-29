package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="User")
@Setter @Getter @NoArgsConstructor @ToString
public class User {
    @Id
    @Column(name = "UserId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name="Username")
    private String username;
    @Column(name="Email")
    private String email;
    @Column(name="Password")
    private String password;
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private UserBank userBank;
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Bet> bets;

    @Override
    public String toString() {
        return username;
    }
}
