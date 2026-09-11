package org.doit.watcha.megazine;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "MAGAZINECATEGORY")
public class MagazineCategory {

    @Id
    @Column(name = "MAGAZINECATEGORYID")
    private Integer magazineCategoryId;

    @Column(name = "MAGAZINECATEGORYNAME")
    private String magazineCategoryName;
}