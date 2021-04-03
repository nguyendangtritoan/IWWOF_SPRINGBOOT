package com.example.iwwof.repository;

import com.example.iwwof.models.Business;
import com.example.iwwof.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    @Query("select b from Business b where b.category LIKE CONCAT('%',:category,'%')")
    List<Business> findBusinessesByCategory(@Param("category") String category);

    @Query("select business from Business business where business.location LIKE CONCAT('%',:location,'%')")
    List<Business> findBusinessesByLocation(@Param("location") String location);

    @Query("select business from Business business where business.user.id = :userId")
    List<Business> findBusinessesByUserId(@Param("userId") Long userId);
}
