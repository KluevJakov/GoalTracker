package com.aptech.goaltracker.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TEAM")
@Getter @Setter @EqualsAndHashCode @ToString
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //private User initiator;
    private String title;
    @ManyToMany
    private List<User> members;
    @ManyToMany
    private List<Goal> goals;
}
