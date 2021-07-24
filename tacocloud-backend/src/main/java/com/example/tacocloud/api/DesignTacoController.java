package com.example.tacocloud.api;

import com.example.tacocloud.Taco;
import com.example.tacocloud.data.TacoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(path="/design", produces = "application/json")
@CrossOrigin(origins="*")
class DesignTacoController{
    private TacoRepository tacoRepository;

    public DesignTacoController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping("/recent")
    public Flux<Taco> recentTacos(){
        return tacoRepository.findAll().take(12);
    }

    @GetMapping("/{id}")
    public Mono<Taco> tacoById(@PathVariable Long id){
        return tacoRepository.findById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Taco taco){
        return tacoRepository.save(taco);
    }

    @PutMapping(consumes = "application/json", path="/{tacoId}")
    public Mono<Taco> putTaco(@RequestBody Taco taco){
        return tacoRepository.save(taco);
    }

    @PatchMapping(consumes = "application/json", path="/{tacoId}")
    public Mono<Taco> patchTaco(@RequestBody Taco patch, @PathVariable Long tacoId){
        Taco taco = tacoRepository.findById(tacoId).block();
        if(taco == null)
            return Mono.empty();
        if(patch.getTacoName() != null)
            taco.setTacoName(patch.getTacoName());
        if(patch.getCreatedAt() != null)
            taco.setCreatedAt(patch.getCreatedAt());
        if(patch.getIngredients() != null)
            taco.setIngredients(patch.getIngredients());
        return tacoRepository.save(taco);
    }
}