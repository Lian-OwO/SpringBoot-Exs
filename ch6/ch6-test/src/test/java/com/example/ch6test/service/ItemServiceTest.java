package com.example.ch6test.service;


import com.example.ch6test.constant.ItemSellStatus;
import com.example.ch6test.dto.ItemFormDto;
import com.example.ch6test.service.ItemService;
import com.example.ch6test.entity.Item;
import com.example.ch6test.entity.ItemImg;
import com.example.ch6test.repository.ItemImgRepository;
import com.example.ch6test.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    // 일반, 파일 데이터 전달시, 멀티파트로 전달하는 부분 체크.

    // 파일 데이터 5개를 담은 리스트를 반환 해주는 메서드.
    // 더미 데이터 생성.
    List<MultipartFile> createMultipartFiles() throws Exception{

        //멀티파트 파일만 담을 임시 리스트 생성.
        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<5;i++){
            // 임시 저장소에, 경로및, 파일명 확장자까지 지정.
            String path = "C:/shop/item/";
            String imageName = "image" + i + ".jpg";
            // 테스트를 위한 더미 데이터 전달용으로 사용된다.
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItem() throws Exception {
        // 1) 엔티티용 클래스 2) 전달용 DTO 클래스
        // 일반 데이터, 문자열, 숫자등.
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setItemNm("테스트상품");
        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDto.setItemDetail("테스트 상품 입니다.");
        itemFormDto.setPrice(1000);
        itemFormDto.setStockNumber(100);

// 정의된 메서드를 통해서, 더미 데이터 5개 생성,(멀티파트 타입을 요소로 가지는 리스트)
        List<MultipartFile> multipartFileList = createMultipartFiles();
        //itemFormDto : 일반데이터 (더미)
        // multipartFileList : 파일데이터 5개 (더미)
        // 영속성 컨텍스트에 영속화 ( 중간 테이블에 등록.)
        Long itemId = itemService.saveItem(itemFormDto, multipartFileList);
        // 상품의 아이디로 검색하고, 상품의 아이디의 오름차순, 등록순으로 정렬.
        // 해당 아이템의 이미지들을 담은 리스트에 담기.
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        // 등록된 상품의 아이디를 통해서, 검색하고,
        // 상품을 등록을 하기위한  입력폼(DTO) 사용중.
        // 2개를 비교함.
        // 등록시 사용했던 DTO의 내용과, 실제 중간테이블에 저장된 내용과 일관성 확인.
        // 이상이 없다면, 정상 단위테스트를 종료.
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(itemFormDto.getItemNm(), item.getItemNm());
        assertEquals(itemFormDto.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(itemFormDto.getItemDetail(), item.getItemDetail());
        assertEquals(itemFormDto.getPrice(), item.getPrice());
        assertEquals(itemFormDto.getStockNumber(), item.getStockNumber());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());
    }

}