package com.HotelBooking.service;

import com.HotelBooking.dtos.BookingDTO;
import com.HotelBooking.dtos.Response;

public interface BookingService {
    Response getAllBookings();
    Response createBooking(BookingDTO bookingDTO);
    Response findBookingByReferenceNo(String  bookingReference);
    Response updateBooking(BookingDTO bookingDTO);

}
