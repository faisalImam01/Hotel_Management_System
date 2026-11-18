package com.HotelBooking.ServiceImpl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.HotelBooking.dtos.NotificationDTO;
import com.HotelBooking.entity.Notification;
import com.HotelBooking.enums.NotificationType;
import com.HotelBooking.repositries.NotificationRepository;
import com.HotelBooking.service.NotificationService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class NotificationServiceImpl  implements NotificationService{
    private  JavaMailSender javaMailSender;

    private  NotificationRepository notificationRepository;

    @Override
    @Async
    public void sendEmail(NotificationDTO notificationDTO) {
        log.info("Sending email ...");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(notificationDTO.getRecipient());
        simpleMailMessage.setSubject(notificationDTO.getSubject());
        simpleMailMessage.setText(notificationDTO.getBody());

        javaMailSender.send(simpleMailMessage);

        //SAVE TO DATABSE
        Notification notificationToSave = Notification.builder()
                .recipient(notificationDTO.getRecipient())
                .subject(notificationDTO.getSubject())
                .body(notificationDTO.getBody())
                .bookingRefrence(notificationDTO.getBookingReference())
                .type(NotificationType.EMAIL)
                .build();

        notificationRepository.save(notificationToSave);    }

	@Override
	public void sendSms() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendWhatsapp() {
		// TODO Auto-generated method stub
		
	}

}

	

