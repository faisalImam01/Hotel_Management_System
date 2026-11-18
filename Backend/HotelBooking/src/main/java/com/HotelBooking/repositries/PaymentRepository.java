package com.HotelBooking.repositries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HotelBooking.entity.PaymentEntity;

public interface PaymentRepository  extends JpaRepository<PaymentEntity, Long>{

}
