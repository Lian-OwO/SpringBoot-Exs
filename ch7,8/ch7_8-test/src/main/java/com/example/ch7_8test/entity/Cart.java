package com.example.ch7_8test.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter @Setter
@ToString
public class Cart extends BaseEntity {
    // Cart : 1 <-----> N CartItem M <----> 1 Item
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public static Cart createCart(Member member){
        // 1명의 회원당 장바구니는 1개로 설정
        Cart cart = new Cart();
        // 장바구니에 로그인한 유저를 등록
        cart.setMember(member);
        return cart;
    }

}