package com.project.pokemon.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pokemon")
public class Pokemon implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int height;
    private int weight;
    private ArrayList<Type> types;
    private String image;
    private int hp;
    private int attack;
    private int defense;
    private int speed;

    public Pokemon( String name, int height, int weight, ArrayList<Type> types, String image, int hp, int attack, int defense, int speed) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.types = types;
        this.image = image;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        System.out.println(hp + " " + attack + " " + defense + " " + speed + "from constructor");
    }
}
