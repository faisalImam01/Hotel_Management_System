package com.HotelBooking.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.HotelBooking.entity.BookingRefrence;
import com.HotelBooking.repositries.BookingRefranceRepository;


@Service
public class BookingCodeGenerator {

	  private BookingRefranceRepository bookingReferenceRepository;

	   public String generateBookingReference() {
	        String bookingReference;

	        // Keep generating until a unique code is found
	        do {
	            bookingReference = generateRandomAlphaNumericCode(10);
	        } while (isBookingReferenceExist(bookingReference));

	        saveBookingReferenceToDatabase(bookingReference);
	        return bookingReference;
	    }

	    private String generateRandomAlphaNumericCode(int length) {
	        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
	        Random random = new Random();
	        StringBuilder sb = new StringBuilder(length);

	        for (int i = 0; i < length; i++) {
	            int index = random.nextInt(characters.length());
	            sb.append(characters.charAt(index));
	        }
	        return sb.toString();
	    }

	    private boolean isBookingReferenceExist(String bookingReference) {
	        return bookingReferenceRepository.findByRefrenceNo(bookingReference).isPresent();
	    }

	    private void saveBookingReferenceToDatabase(String bookingReference) {
	        BookingRefrence newBookingReference = BookingRefrence.builder()
	                .refrenceNo(bookingReference)
	                .build();
	        bookingReferenceRepository.save(newBookingReference);
	    }
	}
	

