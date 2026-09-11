package org.doit.watcha.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "INQUIRYFILE")
@Data
public class InquiryFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INQUIRYFILE")
    private Integer inquiryFileId;

    @Column(name = "INQUIRYID", nullable = false)
    private Integer inquiryId;

    @Column(name = "INQUIRYFILENAME", nullable = false)
    private String inquiryFileName;

    @Column(name = "INQUIRYFILEPATH", nullable = false)
    private String inquiryFilePath;
}




