package com.example.ch2test.repository_Syy0926;


import com.shop.ch02_syytest_homework.entity_Syy0926.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


//public interface ItemRepository extends JpaRepository<Item, Long>,QuerydslPredicateExecutor<Member>  {
    public interface MemberRepository extends JpaRepository<Member, Long>{

    List<Member> findByMemberNm(String memberNm);
    List<Member> findByMemberEmail(String memberEmail);


    List<Member> findByMemberNmOrMemberDescription(String memberNm, String memberDescription);
//
    Optional<Member> findById(Long user_id);

//단축키는 i->m으로 변경
//JPQL 문법이용 표준 SQL
   // JPA : 자바를 영속성 콘텍스트
  //  ORM : object / RDBMS/ Mapping 라는 기술을 써서 자바만을 이용해서 .. 조회
//jpa
    @Query("select m from Member m where m.memberDescription like " +
            "%:memberDescription% order by m.regTime desc")
    List<Member> findByMemberDescription(@Param("memberDescription") String memberDescription);
//nativeQuery
    @Query(value="select * from member i where i.member_Description like " +
            "%:memberDescription% order by i.reg_Time desc", nativeQuery = true)
    List<Member> findByMemberDescriptionNative(@Param("memberDescription") String memberDescription);


//    구분
    @Query(value="select * from member i where i.member_Description like " +
            "%:memberEmail% order by i.reg_Time desc", nativeQuery = true)
    List<Member> findByMemberEmailByNative(@Param("memberEmail") String memberEmail);

}