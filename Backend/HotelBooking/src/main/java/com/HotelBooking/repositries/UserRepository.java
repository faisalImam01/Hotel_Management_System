package com.HotelBooking.repositries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HotelBooking.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	 Optional<User> findByEmail(String emmail);
}
