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

        //Si hay pokemons no hace nada
        if(repository.count() > 0){
            return ResponseEntity.accepted().build();
        }

        String uri = "https://pokeapi.co/api/v2/pokemon";
        List<CountNextPrevResPk> FirstQueryWithCountNextPrevResult = new ArrayList<CountNextPrevResPk>();

        for(int i = 1 ; i <= 2 ; i++ ){
            FirstQueryWithCountNextPrevResult.add(restTemplate.getForObject(uri, CountNextPrevResPk.class));
            if(i != 1){
                FirstQueryWithCountNextPrevResult.add(restTemplate.getForObject(uri+"?limit=100&offset=0", CountNextPrevResPk.class));
            }
        }
        System.out.println(FirstQueryWithCountNextPrevResult);
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
                pkTypes.add(new Type(el.getTypes().getName()));
            }
            System.out.println(value.getStats().get(0).getBase_stat());
            /*Pokemon pokemon = new Pokemon(value.getName(), value.getHeight(), value.getWeight(), pkTypes,
                    value.getSprites().getFront_default(),value.getStats().get(0).getBase_stat()
                    ,value.getStats().get(4).getBase_stat(),value.getStats().get(1).getBase_stat(),
                    value.getStats().get(5).getBase_stat());
            aux2.add(pokemon);*/
        }
        return ResponseEntity.ok(aux2);
        /*
        ArrayList<ArrayResultsPkmon> resDetail = new ArrayList<>();


        for (CountNextPrevResPk re : res) {
            resDetail.add(re.getResults());
        }

        System.out.println(resDetail);


        System.out.println("qwdqwdqwd");

        return ResponseEntity.ok(pokemons);*/
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
