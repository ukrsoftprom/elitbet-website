package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="CLIENT")
@Setter @Getter @NoArgsConstructor @ToString
public class Client {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name="NAME")
    private String name;
    @Column(name="EMAIL")
    private String email;
    @Column(name="PASSWORD")
    private String password;
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "client")
    private ClientBank clientBank;
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<Wager> wagerList;
}