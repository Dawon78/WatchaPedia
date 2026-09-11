package org.doit.watcha.movie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;


@Getter
@Entity
@Table(name="MOVIEDETAIL")
public class MovieDetail {

    @Id
    @Column(name="WORKID")
    private Long workId;

    @Column(name="COUNTRY")
    private String country;

    @Column(name="RUNNINGTIME")
    private Integer runningTime;

    @Column(name="BOOKINGRATE")
    private Double bookingRate;

    @Column(name="AGERATING")
    private String ageRating;

    @Column(name="TOTALAUDIENCE")
    private Long totalAudience;

}