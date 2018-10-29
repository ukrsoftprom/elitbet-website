package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "UserBank")
@Setter @Getter @NoArgsConstructor @ToString
public class UserBank {
    @Id
    @Column(name = "UserBankId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userBankId;
    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    private User user;
    @Column(name = "BankValue")
    private double bankValue;
}