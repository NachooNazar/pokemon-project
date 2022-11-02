package com.project.pokemon.repository;

import com.project.pokemon.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon,Long> {
    //aca podemos crear mas metodos, getByStrength
}
