package org.doit.watcha.series;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;


@Getter
@Entity
@Table(name="SERIESDETAIL")
public class SeriesDetail {

    @Id
    @Column(name="WORKID")
    private Long workId;

    @Column(name="COUNTRY")
    private String country;

    @Column(name="AGERATING")
    private String ageRating;


}