package com.example.iwwof.repository;

import java.util.List;
import java.util.Optional;

import com.example.iwwof.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	@Query(value = "select u from User u where u.isAllowByAdmin = false ")
	List<User> findByAccess();

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
