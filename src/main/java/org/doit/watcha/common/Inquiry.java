package org.doit.watcha.common;

import java.time.LocalDate;

import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "INQUIRY")
@Data
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INQUIRYID")
    private Integer inquiryId;

    @Column(name = "MEMBERID", nullable = false)
    private String memberId;

    @Column(name = "INQUIRYCONTENT", nullable = false, columnDefinition = "TEXT")
    private String inquiryContent;

    @Column(name = "INQUIRYEMAIL", nullable = false)
    private String inquiryEmail;

    @Column(name = "INQUIRYDATE")
    private LocalDate inquiryDate = LocalDate.now();
}