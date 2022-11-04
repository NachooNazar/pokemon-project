package com.project.pokemon.controllers;

import com.project.pokemon.entity.Pokemon;
import com.project.pokemon.entity.Type;
import com.project.pokemon.repository.PokemonRepository;
import com.project.pokemon.utils.pokemon.ArrayResultsPkmon;
import com.project.pokemon.utils.pokemon.CountNextPrevResPk;
import com.project.pokemon.utils.pokemon.PokemonWithAllStuff;
import com.project.pokemon.utils.types.TypesWithSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
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
    public ResponseEntity<List> hello() throws IOException {

        try {
            //Si hay pokemons no hace nada
            if(repository.count() > 0){
                return ResponseEntity.accepted().build();
            }

            String uri = "https://pokeapi.co/api/v2/pokemon";
            List<CountNextPrevResPk> FirstQueryWithCountNextPrevResult = new ArrayList<CountNextPrevResPk>();

            for(int i = 1 ; i <= 2 ; i++ ){
                FirstQueryWithCountNextPrevResult.add(restTemplate.getForObject(uri, CountNextPrevResPk.class));
                if(i != 1){
                    FirstQueryWithCountNextPrevResult.add(restTemplate.getForObject(uri+"?limit=2&offset=0", CountNextPrevResPk.class));
                }
            }
<<<<<<< HEAD
            Pokemon pokemon = new Pokemon(value.getName(), value.getHeight(), value.getWeight(), pkTypes,
                    value.getSprites().getFront_default(),value.getStats().get(0).getBase_stat()
                    ,value.getStats().get(4).getBase_stat(),value.getStats().get(1).getBase_stat(),
                    value.getStats().get(5).getBase_stat());
            FileOutputStream fileOut =new FileOutputStream("/employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(pokemon);
            repository.save(pokemon);
            out.close();
            fileOut.close();
            aux2.add(pokemon);
        }

        return ResponseEntity.ok(aux2);
=======
            //return ResponseEntity.ok(res)
            //count - next - prev - !!results!!

            //resDetail
            /*
             * Es un array con los dos results [[{pkmons}],[{pkmons}]]
             * */

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
                    System.out.println(el.getType().getName() + "soy los tipardos anashex");
                }
                Pokemon pokemon = new Pokemon(value.getName(), value.getHeight(), value.getWeight(),pkTypes,
                 value.getSprites().getFront_default(),value.getStats().get(0).getBase_stat()
                        ,value.getStats().get(4).getBase_stat(),value.getStats().get(1).getBase_stat(),
                        value.getStats().get(5).getBase_stat());
                System.out.println(pokemons);
                repository.save(pokemon);
                aux2.add(pokemon);
            }
            return ResponseEntity.ok(aux2);
        }catch (Exception e) {
            return ResponseEntity.ok(new ArrayList(Collections.singleton(e.getMessage())));
        }

>>>>>>> 938ca1e00387b06f94175e533fdfd9a1f39faed6
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