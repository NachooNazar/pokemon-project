package com.project.pokemon.utils.types;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class TypesFromApi {
    private int count;
    private String next;
    private String previous;
    private ArrayList<TypeTwo> results;
}
