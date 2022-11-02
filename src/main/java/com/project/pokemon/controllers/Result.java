package com.project.pokemon.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String next;
    private String previous;
    private ArrayList results;
}
