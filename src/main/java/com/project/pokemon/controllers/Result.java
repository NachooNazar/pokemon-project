package com.project.pokemon.controllers;

import com.project.pokemon.entity.Pokemon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Result {
    private String next;
    private String previous;
    private ArrayList<FirsResult> results;
}