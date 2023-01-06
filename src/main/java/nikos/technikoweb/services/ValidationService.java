/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.services;

import nikos.technikoweb.exceptions.PropertyException;
import nikos.technikoweb.exceptions.PropertyOwnerException;
import nikos.technikoweb.exceptions.PropertyRepairException;
import nikos.technikoweb.model.Property;
import nikos.technikoweb.model.PropertyRepair;
import nikos.technikoweb.model.User;



public interface ValidationService {

    /**
     * Validate the property date
     * @param property
     * @throws PropertyException
     */
    void validatePropertyData(Property property) throws PropertyException;

    /**
     * validate the owner data
     * @param owner
     * @throws PropertyOwnerException
     */
    void validateOwnerData(User owner) throws PropertyOwnerException;

    /**
     * validate the repair data
     * @param propertyRepair
     * @throws PropertyRepairException
     */
    void validatePropertyRepairData(PropertyRepair propertyRepair) throws PropertyRepairException;
    
}
