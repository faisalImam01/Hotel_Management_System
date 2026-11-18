package com.HotelBooking.service;

import com.HotelBooking.dtos.LoginRequest;
import com.HotelBooking.dtos.RegistrationRequest;
import com.HotelBooking.dtos.Response;
import com.HotelBooking.dtos.UserDTO;
import com.HotelBooking.entity.User;

public interface UserService {
	 Response registerUser(RegistrationRequest registrationRequest);
	    Response loginUser(LoginRequest loginRequest);
	    Response getAllUsers();
	    Response getOwnAccountDetails();
	    User getCurrentLoggedInUser();
	    Response updateOwnAccount(UserDTO userDTO);
	    Response deleteOwnAccount();
	    Response getMyBookingHistory();
}
