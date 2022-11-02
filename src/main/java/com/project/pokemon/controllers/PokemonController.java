package com.project.pokemon.controllers;

import com.project.pokemon.entity.Pokemon;
import com.project.pokemon.repository.PokemonRepository;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/pokemon")
public class PokemonController {

    @Autowired
    private PokemonRepository repository;

    //get all pokemons
    @GetMapping("")
    public List<Pokemon> getAllPokemons(){

        if(repository.count() == 0){
            Request request = new Request.Builder("https://pokeapi.co/api/v2/pokemon")
                    .get().build();
        }
        return repository.findAll();
    }

    // --- FILTERS ---
    //get pokemon by id - detail

    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> getPokemonById(@PathVariable Long id ){
        Optional<Pokemon> pokemon = repository.findById(id);
        try{
            if(pokemon.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pokemon.get());
        }catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //get pokemons by name

    //get pokemons by type

    //get pokemons by str

    //get pokemons by speed

    //get pokemons by hp

    //get pokemons by weight

    //get pokemons by height
}
