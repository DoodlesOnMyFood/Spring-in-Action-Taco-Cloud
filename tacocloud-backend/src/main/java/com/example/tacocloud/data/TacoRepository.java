package com.example.tacocloud.data;

import com.example.tacocloud.Taco;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {
    Taco save(Taco taco);
}
