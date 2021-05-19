package Stockit.repository;

import Stockit.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemRepositoryImpl implements ItemRepository{

    private static long sequence = 0L;
    private static Map<Long, Item> items = new HashMap<>();

    //모든 Item list 검색하기
    @Override
    public List<Item> findAll() {
        return new ArrayList<>(items.values());
    }

    //해당 id의 값으로 Item 하나만 검색
    @Override
    public Optional<Item> getOne(Long aLong) {
        return Optional.ofNullable(items.get(aLong));
    }

    //해당 이름을 가진 Item만 반환
   @Override
    public Optional<Item> findbyName(String name) {
        return items.values().stream()
                .filter(items -> items.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<Item> findbyCode(String code) {
        return items.values().stream()
                .filter(items -> items.getCode().equals(code))
                .findAny();
    }
    public void clearStore() {
        items.clear();
    }
}
