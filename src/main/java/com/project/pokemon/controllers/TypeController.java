package com.project.pokemon.controllers;

import com.project.pokemon.entity.Type;
import com.project.pokemon.repository.TypeRepository;
import com.project.pokemon.utils.types.TypeTwo;
import com.project.pokemon.utils.types.TypesFromApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/type")
public class TypeController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TypeRepository repository;
    // get all types
    @GetMapping()
    public ResponseEntity<List> getTypes() {

        if(repository.count() <= 0) {
            String uri = "https://pokeapi.co/api/v2/type";
            TypesFromApi apiRes;

            apiRes = restTemplate.getForObject(uri, TypesFromApi.class);

            List<Type> res = new ArrayList<>();
            ArrayList<TypeTwo> results = apiRes.getResults();

            for(int i = 0; i < results.size(); i++) {
                Type type = new Type(results.get(i).getName());
                repository.save(type);
                res.add(type);
            }
            return ResponseEntity.ok(res);
        }

       return ResponseEntity.ok(repository.findAll());

    }

}
