package com.elitbet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "CLIENT_BANK")
@Setter @Getter @NoArgsConstructor @ToString
public class ClientBank {
    @Id
    @Column(name = "CLIENT_BANK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientBankId;
    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;
    @Column(name = "BANK_VALUE")
    private double bankValue;
}