package org.doit.watcha.work;

import jakarta.persistence.*; // Id, ManyToOne 등을 한 번에 임포트
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "PERSON_WORK")
@IdClass(PersonWorkId.class) 
public class PersonWork {
    
    @Id 
    @Column(name = "ROLETYPE")
    private String roleType;
    
    @Id 
    @Column(name = "PERSONID")
    private Integer personId;
    
    @Id 
    @Column(name = "WORKID")
    private Integer workId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKID", insertable = false, updatable = false)
    private Work work;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSONID", insertable = false, updatable = false)
    private Person person;

    @Column(name = "CHARACTERNAME")
    private String characterName;
    
    
}