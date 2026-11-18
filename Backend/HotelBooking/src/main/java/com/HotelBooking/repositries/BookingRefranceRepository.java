package com.HotelBooking.repositries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HotelBooking.entity.BookingRefrence;

public interface BookingRefranceRepository extends JpaRepository<BookingRefrence, Long>{

	Optional<BookingRefrence>findByRefrenceNo(String refrenceNo);
}
