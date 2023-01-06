/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nikos.technikoweb.enums.RepairType;
import nikos.technikoweb.enums.StatusType;
import org.hibernate.mapping.Property;

/**
 *
 * @author legeo
 */
@Setter
@Getter
@Entity
@ToString
public class PropertyRepair extends BaseEntity{

    @ManyToOne
    @JoinColumn(name="property_id")
    private Property property;
    private RepairType repairType;
    @Column(length = 128)
    private String description;
    private Instant dateOfSubmission;
    @Column(length = 512)
    private String workToBeDone;
    private Instant proposedStartDate;
    private Instant proposedEndDate;
    private BigDecimal cost;
    private boolean userAcceptance;
    private StatusType status;
    private Instant actualStartDate;
    private Instant actualEndDate;
    private int id;
    private boolean isActive=true;
}
