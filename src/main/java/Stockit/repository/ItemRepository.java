package Stockit.repository;

import Stockit.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository{

    List<Item> findAll();
    Optional<Item> getOne(Long aLong);
    Optional<Item> findbyName(String name);
    Optional<Item> findbyCode(String code);
}
