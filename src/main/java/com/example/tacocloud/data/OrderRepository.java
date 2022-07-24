package com.example.tacocloud.data;

import com.example.tacocloud.tacos.TacoOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<TacoOrder, String> {

}
