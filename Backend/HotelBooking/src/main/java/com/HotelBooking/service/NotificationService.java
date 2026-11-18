package com.HotelBooking.service;

import com.HotelBooking.dtos.NotificationDTO;

public interface NotificationService {
	void sendEmail(NotificationDTO notificationDTO);

    void sendSms();

    void sendWhatsapp();

}
