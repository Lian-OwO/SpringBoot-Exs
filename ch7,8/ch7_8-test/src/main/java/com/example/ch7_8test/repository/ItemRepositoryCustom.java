package com.example.ch7_8test.repository;

import com.example.ch7_8test.dto.ItemSearchDto;
import com.example.ch7_8test.dto.MainItemDto;
import com.example.ch7_8test.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}