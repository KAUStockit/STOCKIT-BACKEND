package Stockit.controller;

import Stockit.domain.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ItemController {

    @GetMapping("item-api")
    @ResponseBody
    public Item itemApi(@RequestParam("id") AtomicInteger id,
                         @RequestParam("code") String code,
                         @RequestParam("name") String name,
                         @RequestParam("is_active") AtomicBoolean is_active,
                         @RequestParam("issurance")AtomicLong issuance,
                         @RequestParam("url") String url){
        Item one_item = new Item(id, code, name, is_active, issuance, url);
        return one_item;
    }
}
