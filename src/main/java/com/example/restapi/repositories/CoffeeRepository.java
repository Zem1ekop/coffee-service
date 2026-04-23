package com.example.restapi.repositories;

import com.example.restapi.SpringBootRestapiApplication;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<SpringBootRestapiApplication.Coffee, String> {}
