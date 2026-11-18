package com.HotelBooking.payment.stripe;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class PaymentRequest {

	
	@NotBlank(message="Booking is required")
	private String bookingRefrence;
	private BigDecimal amount;
	
	private String transactionId;
	private boolean success;
	private String failureReason;
}
