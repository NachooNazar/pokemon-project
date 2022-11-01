package com.project.nba.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "player")
public class Player {
    private Long id;
    private String firstname;
    private String lastname;
    private Birth birthdate;
    private String college;
    private String affiliation;
}
