/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.Instant;
import nikos.technikoweb.enums.RepairType;
import nikos.technikoweb.enums.StatusType;
import nikos.technikoweb.model.Property;
import nikos.technikoweb.model.PropertyRepair;

/**
 *
 * @author legeo
 */
public class PropertyRepairDto {
   
    private Property property;
    private RepairType repairType;
    private String description;
    private Instant dateOfSubmission;
    private String workToBeDone;
    private Instant proposedStartDate;
    private Instant proposedEndDate;
    private BigDecimal cost;
    private boolean userAcceptance;
    private StatusType status;
    private Instant actualStartDate;
    private Instant actualEndDate;
    private int id;
    @JsonSerialize(using = nikos.technikoweb.serializer.LocalDateTimeSerializer.class)
    @JsonDeserialize(using = nikos.technikoweb.serializer.LocalDateTimeDeserializer.class)
    private boolean isActive=true;
    
    public PropertyRepairDto (PropertyRepair propertyRepair) {
     if (propertyRepair != null) {
      repairType = propertyRepair.getRepairType();
      dateOfSubmission = propertyRepair.getDateOfSubmission();
      workToBeDone = propertyRepair.getWorkToBeDone();
      status = propertyRepair.getStatus();
      cost = propertyRepair.getCost();
     }
    }
    
    public PropertyRepair asPropertyRepair() {
     PropertyRepair propertyRepair = new PropertyRepair();
     propertyRepair.setRepairType(repairType);
     propertyRepair.setDateOfSubmission(dateOfSubmission);
     propertyRepair.setWorkToBeDone(workToBeDone);
     propertyRepair.setStatus(status);
     propertyRepair.setCost(cost);
     return propertyRepair;
    }
}
