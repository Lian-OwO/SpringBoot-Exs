package com.example.entity_Syy0926;

import com.example.syytest_homework.constant_Syy0926.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    //컬럼이름 수동변경
    @Column(name = "member_id")
    //자동증가번호 생성전략
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;       //멤버코드


    @Column(nullable = false, length = 50)
    private String memberNm; //멤버이름

    @Column(name = "memberEmail", nullable = false)
    private String memberEmail; //멤버이메일


    @Lob
    @Column(nullable = false)
    private String memberDescription; //멤버소개

    @Enumerated(EnumType.STRING)
    private UserRole userRole; //유저 권한

    private LocalDateTime regTime; //등록 시간

}