package com.project.pokemon.repository;

import com.project.pokemon.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon,Long> {
    Optional<Pokemon> findByName(String name);
    //aca podemos crear mas metodos, getByStrength
}
