package com.HotelBooking.payment.stripe;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.HotelBooking.dtos.NotificationDTO;
import com.HotelBooking.entity.Booking;
import com.HotelBooking.entity.PaymentEntity;
import com.HotelBooking.enums.NotificationType;
import com.HotelBooking.enums.PaymentGateway;
import com.HotelBooking.enums.PaymentStatus;
import com.HotelBooking.exceptions.NotFoundException;
import com.HotelBooking.repositries.BookingRepositry;
import com.HotelBooking.repositries.PaymentRepository;
import com.HotelBooking.service.NotificationService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;


@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final BookingRepositry bookingRepository;
    private final PaymentRepository paymentRepository;
    private final NotificationService notificationService;

    @Value("${stripe.api.secret.key}")
    private String secreteKey;


    public String createPaymentIntent(PaymentRequest paymentRequest){
        log.info("Inside createPaymentIntent()");
        Stripe.apiKey = secreteKey;
        String bookingReference = paymentRequest.getBookingRefrence();


        Booking booking = bookingRepository.findByBookingRefrence(bookingReference)
                .orElseThrow(() -> new NotFoundException("Booking Not Found"));

        if (booking.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new NotFoundException("Payment already made for this booking");

        }

        try{
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(paymentRequest.getAmount().multiply(BigDecimal.valueOf(100)).longValue()) //amount cents
                    .setCurrency("usd")
                    .putMetadata("bookingReference", bookingReference)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            return intent.getClientSecret();

        }catch (Exception e){
            throw new RuntimeException("Error creating payment intent");
        }

    }


    public void updatePaymentBooking(PaymentRequest paymentRequest){

        log.info("Inside updatePaymentBooking()");
        String bookingReference = paymentRequest.getBookingRefrence();

        Booking booking = bookingRepository.findByBookingRefrence(bookingReference)
                .orElseThrow(()-> new NotFoundException("Booing Not Found"));

        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentGateway(PaymentGateway.STRIPE);
        payment.setAmount(paymentRequest.getAmount());
        payment.setTransactionId(paymentRequest.getTransactionId());
        payment.setPaymentStatus(paymentRequest.isSuccess() ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setBookingRefrence(bookingReference);
       payment.setUser(booking.getUser());

        if (!paymentRequest.isSuccess()) {
            payment.setFailureReason(paymentRequest.getFailureReason());
        }

        paymentRepository.save(payment); //save payment to database

        //create and send notifiaction
        NotificationDTO notificationDTO = NotificationDTO.builder()
                .recipient(booking.getUser().getEmail())
                .type(NotificationType.EMAIL)
                .bookingReference(bookingReference)
                .build();

        log.info("About to send notification inside updatePaymentBooking  by sms");


        if (paymentRequest.isSuccess()){
            booking.setPaymentStatus(PaymentStatus.COMPLETED);
            bookingRepository.save(booking); //Update the booking

            notificationDTO.setSubject("Booking Payment Successful");
            notificationDTO.setBody("Congratulation!! Your payment for booking with reference: " + bookingReference + "is successful");
            notificationService.sendEmail(notificationDTO); //send email

        }else {

            booking.setPaymentStatus(PaymentStatus.FAILED);
            bookingRepository.save(booking); //Update the booking

            notificationDTO.setSubject("Booking Payment Failed");
            notificationDTO.setBody("Your payment for booking with reference: " + bookingReference + "failed with reason: " + paymentRequest.getFailureReason());
            notificationService.sendEmail(notificationDTO); //send email
        }





    }


}
