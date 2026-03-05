package com.nautik.api.dto.bookings;

import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.dto.mooring.MooringDto;
import com.nautik.api.dto.payment.PaymentInitRequestDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@NoArgsConstructor
public class BookingDto {
        private Long id;
        private String startDate;
        private String endDate;
        private Double totalCost;
        private Long boatId;
        private Long bookingStatusId;
        private String mooringNumber;
        private String boatName;
        private String boatRegistryNumber;
        private String clientName;
        private String clientEmail;
        private MooringDto mooring;
        private PaymentInitRequestDto payment;
        private Integer portId;
}
