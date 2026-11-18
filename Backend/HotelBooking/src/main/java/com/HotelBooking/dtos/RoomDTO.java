package com.HotelBooking.dtos;

import java.math.BigDecimal;

import com.HotelBooking.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class RoomDTO {


    private Long id;

    private Integer roomNumber;

    private RoomType type;

    private BigDecimal pricePerNight;

    private Integer capacity;

    private String description; //additional data for the room

    private String imageUrl; //this will hold the room picture

}
