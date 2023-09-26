package com.shop.ch2homework.repository_scb0925;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.ch2homework.constant_scb0925.UserRole;
import com.shop.ch2homework.entity_scb0925.Member;
import com.shop.ch2homework.entity_scb0925.QMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// 테스트를 하기위한 , 설정 파일을 분리했고, 로드.
// 설정 파일이 주석이 되면, 기본 설정 파일이 로드가 된다.
// 기본 설정 파일은 mysql 로 등록이 되어 있다.
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    // 영속성 컨텍스트 기능 이용하기위한, 엔티티 매니저 인스턴스
    @PersistenceContext
    EntityManager em;


    @Test
    @DisplayName("멤버 저장 테스트")
    public void createMemberTest() {
        Member member = new Member();
        member.setUserNm("서청빈");
        member.setUserDescription("실습 풀이중");
        member.setUserEmail("aaa@naver.com");
        member.setUserRole(UserRole.ADMIN);
        member.setRegTime(LocalDateTime.now());

        //itemRepository 이용해서, 자바 -> 디비, 샘플디비 하나 생성
        // 영속성 컨텍스트 = 중간 저장소, 1차 캐시 테이블
        Member savedMember = memberRepository.save(member);
        // H2, 실제 파일기반으로 내용을 저장이 아니라.
        // 메모리에 임시로 작업을 해서, 저장이 안됨.
        System.out.print(savedMember.toString());
    }

    public void createMemberList(){
        for(int i=1;i<=10;i++){
            Member member = new Member();
            member.setUserNm("서청빈"+i);
            member.setUserDescription("실습 풀이중" +i);
            member.setUserEmail("aaa"+i+"@naver.com");
            member.setUserRole(UserRole.ADMIN);
            member.setRegTime(LocalDateTime.now());
            memberRepository.save(member);

        }
    }

    @Test
    void findByUserNm() {
        this.createMemberList();
        List<Member> memberList = memberRepository.findByUserNm("서청빈1");
        for(Member member : memberList){
            System.out.println(member.toString());
            System.out.println("이름 확인 해보기 : "+member.getUserNm());
        }
    }

//    @Test
//    void findByUserDescription() {
//
//        this.createMemberList();
//        List<Member> memberList = memberRepository.findByUserDescription("실습 풀이중1");
//        for(Member member : memberList){
//            System.out.println(member.toString());
//            System.out.println("이름 확인 해보기 : "+member.getUserNm());
//        }
//    }

    @Test
    @DisplayName("유저명, 유저소개 or 테스트")
    // H2 데이터베이스를 이용해서, 메모리 상에서, 단위 테스트 기능을 확인중.
    // 메모리 상에 작업중이니, 당연히 MySQL 디비와는 관계없음.
    public void findByUserNmOrUserDescription(){
        // 매번 테스트 할 때 마다, 더미 데이터 10 개 생성
        this.createMemberList();
        // 확인.  조회 조건 확인 중. or
        List<Member> memberList = memberRepository.findByUserNmOrUserDescription("서청빈1", "실습 풀이중3");
        for(Member member : memberList){
            System.out.println(member.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 유저 조회 테스트")
    public void findByUserDescription(){
        this.createMemberList();
        List<Member> memberList =
                memberRepository.findByUserDescription("실습 풀이중7");
        for(Member member : memberList){
            System.out.println(member.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상유저 조회 테스트 native 속성 사용")
    public void findByUserDescriptionTest(){
        //this.createMemberList();
        List<Member> memberList =
                memberRepository.findByUserDescriptionByNative("실습 풀이중7");
        for(Member member : memberList){
            System.out.println(member.toString());
        }
    }


    /*Querydsl 테스트 추가 부분*/
    /*사용시 추가 해야하는 사항 2가지 있고,
    1) 의존성 1개
    2) 플러그인 1개*/

    /*pom.xml 추가 확인
    <!--쿼리 dsl 추가1 ~ 2-->*/

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest(){
        this.createMemberList2();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember qMember = QMember.member;
        JPAQuery<Member> query  = queryFactory.selectFrom(qMember)
                .where(qMember.userRole.eq(UserRole.USER))
                .where(qMember.userDescription.like("%" + "실습 풀이중" + "%"))
                .orderBy(qMember.regTime.desc());

        List<Member> memberList = query.fetch();

        for(Member member : memberList){
            System.out.println(member.toString());
        }
    }

    public void createMemberList2(){
        for(int i=1;i<=5;i++){
            Member member = new Member();
            member.setUserNm("서청빈"+i);
            member.setUserDescription("실습 풀이중" +i);
            member.setUserEmail("aaa"+i+"@naver.com");
            member.setUserRole(UserRole.ADMIN);
            member.setRegTime(LocalDateTime.now());
            memberRepository.save(member);
        }

        for(int i=6;i<=10;i++){
            Member member = new Member();
            member.setUserNm("서청빈"+i);
            member.setUserDescription("실습 풀이중" +i);
            member.setUserEmail("aaa"+i+"@naver.com");
            member.setUserRole(UserRole.USER);
            member.setRegTime(LocalDateTime.now());
            memberRepository.save(member);
        }
    }

}