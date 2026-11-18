package com.HotelBooking.repositries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HotelBooking.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	
}
