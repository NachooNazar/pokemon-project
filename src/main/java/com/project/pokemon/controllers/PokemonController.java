package com.project.pokemon.controllers;

import com.project.pokemon.entity.Pokemon;
import com.project.pokemon.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.io.*;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/pokemon")
public class PokemonController {

    @Autowired
    private PokemonRepository repository;

    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/hello")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List> hello() {

        if(repository.count() > 0){
            return ResponseEntity.accepted().build();
        }
        String uri = "https://pokeapi.co/api/v2/pokemon";
        List<Result> res = new ArrayList<Result>();

        for(int i = 1 ; i <= 2 ; i++ ){
            res.add(restTemplate.getForObject(uri,Result.class));
            if(i != 1){
                res.add(restTemplate.getForObject(uri+"?limit=100000&offset=0",Result.class));
            }
        }

        ArrayList<ArrayList<FirsResult>> resDetail = new ArrayList<>();

        for (Result re : res) {
            resDetail.add(re.getResults());
        }

       ArrayList<Pokemon> pokemons = new ArrayList<>();

        for (int i = 0; i < resDetail.get(0).size(); i++){
            pokemons.add(restTemplate.getForObject(resDetail.get(0).get(i).getUrl(),Pokemon.class));
        }
        for (int l =0; l < pokemons.size(); l++){
            Pokemon pokemon = new Pokemon(pokemons.get(l).getName(), pokemons.get(l).getHeight(), pokemons.get(l).getWeight(), pokemons.get(l).getTypes(), pokemons.get(l).getImage());

        }

        return ResponseEntity.ok(pokemons);
    }


    //get all pokemons
    @GetMapping("")
    public List<Pokemon> getAllPokemons(){
/*
        if(repository.count() == 0){
            Request request = new Request.Builder("https://pokeapi.co/api/v2/pokemon")
                    .get().build();
        }0*/
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
