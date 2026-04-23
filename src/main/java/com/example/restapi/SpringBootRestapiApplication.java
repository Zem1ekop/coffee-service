package com.example.restapi;

import com.example.restapi.repositories.CoffeeRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class SpringBootRestapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestapiApplication.class, args);
    }

    @Entity
    public static class Coffee {

        @Id
        private String id;

        private String name;

        public Coffee(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public Coffee(String name) {
            this(UUID.randomUUID().toString(), name);
        }

        public Coffee() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @RestController
    @RequestMapping("/coffees")
    class RestApiDemoController {
        private final CoffeeRepository coffeeRepository;

        public RestApiDemoController(CoffeeRepository coffeeRepository) {
            this.coffeeRepository = coffeeRepository;

            this.coffeeRepository.saveAll(List.of(
                    new Coffee("Café Cereza"),
                    new Coffee("Café Ganador"),
                    new Coffee("Café Lareño"),
                    new Coffee("Café Três Pontas")
            ));
        }

        @GetMapping
        Iterable<Coffee> getCoffees() {
            return coffeeRepository.findAll();
        }

        @GetMapping("/{id}")
        Optional<Coffee> getCoffeeById(@PathVariable String id) {
            return coffeeRepository.findById(id);
        }

        @PostMapping
        Coffee postCoffee(@RequestBody Coffee coffee) {
            return coffeeRepository.save(coffee);
        }

        @PutMapping("/{id}")
        ResponseEntity<Coffee> putCoffee(@PathVariable String id,
                                         @RequestBody Coffee coffee) {
            return (!coffeeRepository.existsById(id))
                    ? new ResponseEntity<>(coffeeRepository.save(coffee),
                    HttpStatus.CREATED)
                    : new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        void deleteCoffee(@PathVariable String id) {
           coffeeRepository.deleteById(id);
        }
    }
}