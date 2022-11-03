package com.project.pokemon.utils.pokemon;

import com.project.pokemon.utils.pokemon.ArrayResultsPkmon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CountNextPrevResPk {
    private String next;
    private String previous;
    private ArrayList<ArrayResultsPkmon> results;
}