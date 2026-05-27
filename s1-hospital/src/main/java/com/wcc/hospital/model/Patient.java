package com.wcc.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String nationalId;       // unique identifier (e.g. NHS number)
    private LocalDate dateOfBirth;
    private String gender;           // MALE, FEMALE, OTHER
    private String bloodType;        // A+, B-, O+, AB+, etc.
    private String phoneNumber;
    private String email;
    private String address;
    private String ward;             // e.g. ICU, Cardiology, General
    private String admissionStatus;  // ADMITTED, DISCHARGED, OUTPATIENT
    private LocalDate admissionDate;
}
