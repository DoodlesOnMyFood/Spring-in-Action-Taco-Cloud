package com.example.tacocloud.web;

import com.example.tacocloud.Taco;
import com.example.tacocloud.data.TacoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/design", produces = "application/json")
@CrossOrigin(origins="*")
class DesignTacoController{
    private TacoRepository tacoRepository;

    public DesignTacoController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos(){
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable Long id){
        Optional<Taco> tacoOptional = tacoRepository.findById(id);
        return tacoOptional.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco){
        return tacoRepository.save(taco);
    }

    @PutMapping(consumes = "application/json", path="/{tacoId}")
    public Taco putTaco(@RequestBody Taco taco){
        return tacoRepository.save(taco);
    }

    @PatchMapping(consumes = "application/json", path="/{tacoId}")
    public Taco patchTaco(@RequestBody Taco patch, @PathVariable Long tacoId){
        Taco taco = tacoRepository.findById(tacoId).get();
        if(patch.getTacoName() != null)
            taco.setTacoName(patch.getTacoName());
        if(patch.getCreatedAt() != null)
            taco.setCreatedAt(patch.getCreatedAt());
        if(patch.getIngredients() != null)
            taco.setIngredients(patch.getIngredients());
        return tacoRepository.save(taco);
    }
}