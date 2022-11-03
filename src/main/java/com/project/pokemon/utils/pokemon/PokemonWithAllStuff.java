package com.project.pokemon.utils.pokemon;

import com.project.pokemon.utils.types.TypesWithSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PokemonWithAllStuff {
    private int height;
    private int weight;
    private String name;
    private Sprites sprites;
    private ArrayList<TypesWithSlot> types;
    private ArrayList<Stats> stats;
}
