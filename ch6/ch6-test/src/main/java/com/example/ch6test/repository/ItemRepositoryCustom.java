package com.example.ch6test.repository;

import com.example.ch6test.dto.ItemSearchDto;
import com.example.ch6test.dto.MainItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.ch6test.entity.Item;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}