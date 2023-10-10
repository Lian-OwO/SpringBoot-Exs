package com.example.ch6test.controller;


import com.example.ch6test.dto.ItemSearchDto;
import com.example.ch6test.dto.MainItemDto;
import com.example.ch6test.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping(value = "/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

        //부트에서 페이징 처리를 쉽게 해주는 인터페이스 기능.
        // 첫 매개변수는 페이지 번호,
        // 두번 째 매개변수는 , 한페이지당 불러올 갯수
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        // 쿼리 메서드 , 메인에 상품을 나열할 때, 페이징을 처리해서, 출력하는 것.
        // 메인 페이지에, 페이징이 처리가 되어서, 나열함.
        // 검색의 키워드 또는 내용을 전달.
        // 추가로, 페이징을 처리해주는 pageable
        // 결론, 검색 조건 , 페이징을 같이 처리해서, 데이터를 가지고 온 목록.
        // 예) 상품의 검색 결과에 페이징을 추가 했다.
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        // 서버 -> 뷰 : 전달.
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "main";
    }

}