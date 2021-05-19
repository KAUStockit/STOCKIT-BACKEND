package Stockit.controller;

import Stockit.domain.Item;
import Stockit.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ItemController {

    public final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("item-api")
    @ResponseBody
    public Item itemApi(@RequestParam("id") AtomicInteger id,
                         @RequestParam("code") String code,
                         @RequestParam("name") String name,
                         @RequestParam("is_active") AtomicBoolean is_active,
                         @RequestParam("issurance")AtomicLong issuance,
                         @RequestParam("url") String url){
        Item one_item = new Item(code, name, is_active, issuance, url);
        return one_item;
    }

    @GetMapping("/namesearch")
    public String searchItemName(@RequestParam("name") String name, Model model){
        Optional<Item> name_item = itemService.findOneItem(name, true);
        model.addAttribute("item", name_item);
        return "namesearch/item";
    }
    @GetMapping("/codesearch")
    public String searchItemCode(@RequestParam("code") String code, Model model){
        Optional<Item> code_item = itemService.findOneItem(code, false);
        model.addAttribute("item", code_item);
        return "namesearch/item";
    }
}
