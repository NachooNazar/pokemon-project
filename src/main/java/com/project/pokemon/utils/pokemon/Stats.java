package com.project.pokemon.utils.pokemon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Stats {
    private int base_stat;
    private int effort;
    private Stat stat;
}

