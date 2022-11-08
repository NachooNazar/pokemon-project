package com.project.pokemon.controllers;

import com.project.pokemon.entity.Pokemon;
import com.project.pokemon.entity.Type;
import com.project.pokemon.repository.PokemonRepository;
import com.project.pokemon.utils.pokemon.ArrayResultsPkmon;
import com.project.pokemon.utils.pokemon.CountNextPrevResPk;
import com.project.pokemon.utils.pokemon.PokemonWithAllStuff;
import com.project.pokemon.utils.types.TypesWithSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

@RestController
@RequestMapping(value = "/api/pokemon")
public class PokemonController {

    @Autowired
    private PokemonRepository repository;

    @Autowired
    private RestTemplate restTemplate;
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/hello")
    public ResponseEntity<List> hello() throws IOException {

        try {
            //Si hay pokemons no hace nada
            if(repository.count() > 0){
                return ResponseEntity.accepted().build();
            }

            String uri = "https://pokeapi.co/api/v2/pokemon";
            List<CountNextPrevResPk> FirstQueryWithCountNextPrevResult = new ArrayList<CountNextPrevResPk>();

            for(int i = 1 ; i <= 2 ; i++ ){
                if(i == 1){
                    FirstQueryWithCountNextPrevResult.add(restTemplate.getForObject(uri, CountNextPrevResPk.class));
                }else{
                    FirstQueryWithCountNextPrevResult.add(restTemplate.getForObject(uri+"?offset=20&limit=20", CountNextPrevResPk.class));
                }
            }


            ArrayList<ArrayResultsPkmon> namesAndUrls = new ArrayList<ArrayResultsPkmon>();
            for (CountNextPrevResPk countNextPrevResPk : FirstQueryWithCountNextPrevResult) {
                namesAndUrls.addAll(countNextPrevResPk.getResults());
            }

            ArrayList<PokemonWithAllStuff> pokemons = new ArrayList<>();

            for (ArrayResultsPkmon firsResults : namesAndUrls) {
                pokemons.add(restTemplate.getForObject(firsResults.getUrl(), PokemonWithAllStuff.class));
            }

            ArrayList<Pokemon> aux2 = new ArrayList<>();

            for (PokemonWithAllStuff value : pokemons) {
                ArrayList<Type> pkTypes = new ArrayList<>();
                for (TypesWithSlot el : value.getTypes()) {
                    pkTypes.add(new Type(el.getType().getName()));
                }
                Pokemon pokemon = new Pokemon(value.getName(), value.getHeight(), value.getWeight(),pkTypes,
                 value.getSprites().getOther().getDream_world().getFront_default(),value.getStats().get(0).getBase_stat()
                        ,value.getStats().get(4).getBase_stat(),value.getStats().get(1).getBase_stat(),
                        value.getStats().get(5).getBase_stat());

                repository.save(pokemon);
                aux2.add(pokemon);
            }
            return ResponseEntity.ok(aux2);
        }catch (Exception e) {
            return ResponseEntity.ok(new ArrayList(Collections.singleton(e.getMessage())));
        }
    }


    //get all pokemons
    @GetMapping("")
    public ResponseEntity<List> getAllPokemons(){
    try{
        if(repository.count() <= 0){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(repository.findAll());
    }catch(Exception e){
        return ResponseEntity.badRequest().build();
    }

    }

    // --- FILTERS ---
    //get pokemon by id - detail

    @GetMapping("/detail/{id}")
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

    @GetMapping("/search/{name}")
    public ResponseEntity<Pokemon> getPokemonsByName(@PathVariable String name){
        Optional<Pokemon> pokemon= repository.findByName(name);
        try {
         if(pokemon.isEmpty()) {
             return ResponseEntity.notFound().build();
         }
            System.out.println(pokemon);
         return ResponseEntity.ok(pokemon.get());
        }catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //get pokemons by type
    @GetMapping("/getByType/{name}")
    public ResponseEntity<List<Pokemon>> getPokemonsByType(@PathVariable String name){
        List<Pokemon> pokemon = repository.findAll();
        try {
            if(pokemon.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<Pokemon> res = new ArrayList<Pokemon>();
            for (Pokemon value : pokemon) {
                ArrayList<Type> types = value.getTypes();
                for (Type type : types) {
                    if (Objects.equals(type.getName(), name)) {
                        res.add(value);
                    }
                }
            }
            return ResponseEntity.ok(res);
        }catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //get pokemons by Attack
@GetMapping("/forStr/{lowOrHigh}")

    public ResponseEntity<List<Pokemon>> orderByAttack(@PathVariable String lowOrHigh){
    List<Pokemon> pokemon = repository.findAll();
    try {
        if(Objects.equals(lowOrHigh, "low")){
            pokemon.sort(new Comparator<Pokemon>() {
             @Override
             public int compare(Pokemon o1, Pokemon o2) {
             return new Integer(o1.getAttack()).compareTo(o2.getAttack());
              }});
            return ResponseEntity.ok(pokemon);
        }

        if(Objects.equals(lowOrHigh, "high")){
            pokemon.sort(new Comparator<Pokemon>() {
                @Override
                public int compare(Pokemon o1, Pokemon o2) {
                    return new Integer(o2.getAttack()).compareTo(o1.getAttack());
                }
            });
            return ResponseEntity.ok(pokemon);
        }
        return ResponseEntity.notFound().build();
    }catch(Exception e) {
        return ResponseEntity.badRequest().build();
    }
    }

    //get pokemons by speed
    @GetMapping("/forSpeed/{lowOrHigh}")

    public ResponseEntity<List<Pokemon>> orderBySpeed(@PathVariable String lowOrHigh){
        List<Pokemon> pokemon = repository.findAll();
        try {
            if(Objects.equals(lowOrHigh, "low")){
                pokemon.sort(new Comparator<Pokemon>() {
                    @Override
                    public int compare(Pokemon o1, Pokemon o2) {
                        return new Integer(o1.getSpeed()).compareTo(o2.getSpeed());
                    }});
                return ResponseEntity.ok(pokemon);
            }

            if(Objects.equals(lowOrHigh, "high")){
                pokemon.sort(new Comparator<Pokemon>() {
                    @Override
                    public int compare(Pokemon o1, Pokemon o2) {
                        return new Integer(o2.getSpeed()).compareTo(o1.getSpeed());
                    }
                });
                return ResponseEntity.ok(pokemon);
            }
            return ResponseEntity.notFound().build();
        }catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    //get pokemons by hp
    @GetMapping("/forHp/{lowOrHigh}")

    public ResponseEntity<List<Pokemon>> orderByHp(@PathVariable String lowOrHigh) {
        List<Pokemon> pokemon = repository.findAll();
        try {
            if (Objects.equals(lowOrHigh, "low")) {
                pokemon.sort(new Comparator<Pokemon>() {
                    @Override
                    public int compare(Pokemon o1, Pokemon o2) {
                        return new Integer(o1.getHp()).compareTo(o2.getHp());
                    }
                });
                return ResponseEntity.ok(pokemon);
            }

            if (Objects.equals(lowOrHigh, "high")) {
                pokemon.sort(new Comparator<Pokemon>() {
                    @Override
                    public int compare(Pokemon o1, Pokemon o2) {
                        return new Integer(o2.getHp()).compareTo(o1.getHp());
                    }
                });
                return ResponseEntity.ok(pokemon);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
        //get pokemons by weight

        @GetMapping("/forWeigth/{lowOrHigh}")

        public ResponseEntity<List<Pokemon>> orderByWeigth(@PathVariable String lowOrHigh) {
            List<Pokemon> pokemon = repository.findAll();
            try {
                if (Objects.equals(lowOrHigh, "low")) {
                    pokemon.sort(new Comparator<Pokemon>() {
                        @Override
                        public int compare(Pokemon o1, Pokemon o2) {
                            return new Integer(o1.getWeight()).compareTo(o2.getWeight());
                        }
                    });
                    return ResponseEntity.ok(pokemon);
                }

                if (Objects.equals(lowOrHigh, "high")) {
                    pokemon.sort(new Comparator<Pokemon>() {
                        @Override
                        public int compare(Pokemon o1, Pokemon o2) {
                            return new Integer(o2.getWeight()).compareTo(o1.getWeight());
                        }
                    });
                    return ResponseEntity.ok(pokemon);
                }
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }
            //get pokemons by height
            @GetMapping("/forHeigth/{lowOrHigh}")

            public ResponseEntity<List<Pokemon>> orderByHeigth(@PathVariable String lowOrHigh) {
                List<Pokemon> pokemon = repository.findAll();
                try {
                    if (Objects.equals(lowOrHigh, "low")) {
                        pokemon.sort(new Comparator<Pokemon>() {
                            @Override
                            public int compare(Pokemon o1, Pokemon o2) {
                                return new Integer(o1.getHeight()).compareTo(o2.getHeight());
                            }
                        });
                        return ResponseEntity.ok(pokemon);
                    }

                    if (Objects.equals(lowOrHigh, "high")) {
                        pokemon.sort(new Comparator<Pokemon>() {
                            @Override
                            public int compare(Pokemon o1, Pokemon o2) {
                                return new Integer(o2.getHeight()).compareTo(o1.getHeight());
                            }
                        });
                        return ResponseEntity.ok(pokemon);
                    }
                    return ResponseEntity.notFound().build();
                } catch (Exception e) {
                    return ResponseEntity.badRequest().build();
                }
            }
}