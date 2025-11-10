package com.petlearning.restapi.repository;

import com.petlearning.restapi.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 飼主資料存取介面
 * 繼承 JpaRepository 即可擁有常用的 CRUD 方法
 */
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    
    /**
     * 依姓氏查詢飼主
     */
    List<Owner> findByLastName(String lastName);
    
    /**
     * 依城市查詢飼主
     */
    List<Owner> findByCity(String city);
    
    /**
     * 依姓氏和名字查詢飼主
     */
    List<Owner> findByFirstNameAndLastName(String firstName, String lastName);
}
