package com.elitbet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "BetType")
@Setter@Getter@NoArgsConstructor@ToString
public class BetType {
    public static final String first_win = "1";
    public static final String second_win = "2";
    public static final String draw = "X";

    @Id
    @Column(name = "BetTypeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long betTypeId;
    @Column(name = "Description")
    private String description;

    @Override
    public String toString() {
        return description;
    }
}
