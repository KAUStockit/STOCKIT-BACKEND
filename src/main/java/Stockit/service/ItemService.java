package Stockit.service;

import Stockit.domain.Item;
import Stockit.repository.ItemRepository;
import Stockit.repository.ItemRepositoryImpl;
import org.springframework.stereotype.Service;

import javax.lang.model.type.NullType;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository = new ItemRepositoryImpl();

    //전체 Item들 조회
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    //검색창을 통해서 입력함
    public Optional<Item> findOneItem(String NameOrCode, boolean flag){
        try {
            if(flag == true){
                itemRepository.findbyCode(NameOrCode);
            }
            else{
                itemRepository.findbyName(NameOrCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("해당 문자를 찾을 수 없습니다.");
            return null;
        }

    }
}
