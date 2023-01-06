/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.services.impl;

import jakarta.inject.Inject;
import nikos.technikoweb.enums.PropertyType;
import nikos.technikoweb.exceptions.PropertyException;
import nikos.technikoweb.exceptions.codes.PropertyExceptionCodes;
import nikos.technikoweb.model.Property;
import nikos.technikoweb.model.PropertyOwner;
import nikos.technikoweb.model.PropertyRepair;
import nikos.technikoweb.repository.PropertyRepairRepository;
import nikos.technikoweb.repository.PropertyRepository;
import nikos.technikoweb.services.RegisterService;

/**
 *
 * @author legeo
 */
public class RegisterServiceImpl implements RegisterService {
    @Inject
    private PropertyRepository properties;
    @Inject
    private PropertyRepairRepository propertyRepairs;

    public RegisterServiceImpl(/* PropertyOwnerRepository owners,*/PropertyRepository properties, PropertyRepairRepository repairs) {
        this.properties = properties;
        this.propertyRepairs=propertyRepairs;
    }

    @Override
    public void registerPropertyOwner(PropertyOwner owner) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void registerProperty(Property property) throws PropertyException {
        checkPropertyNullValues(property);
        PropertyOwner owner = property.getUser();
        validateOwner(owner);
        checkPropertyValidValues(property);
        properties.create(property);
    }

    @Override
    public void registerPropertyRepair(PropertyRepair repair) {
       propertyRepairs.create(repair);
    }

    private void checkPropertyValidValues(Property property) throws PropertyException {

        if (!property.isActive()) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_NOT_ACTIVE);
        }
        Integer yearOfConstruction = property.getYearOfConstruction();
        if (yearOfConstruction > 2022) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_YEAR_EROR);
        }
        String address = property.getAddress();
        if (address.length() <= 1) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_ADDRESS_ERROR);
        }
    }

    private void checkPropertyNullValues(Property property) throws PropertyException {
        if (property == null) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_NULL);
        }

        if (property.getAddress() == null || property.getOwner() == null || property.getPropertyIdentificationNumber() == null
                || property.getPropertyType() == null) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_NULL_VALUES);
        }
    }

    private void validateOwner(PropertyOwner owner) throws PropertyException {
        if (owner.isAdmin()) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_ADMIN_OWNER);
        }
        if (!owner.isActive()) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_INCATIVE_OWNER);
        }
    }

    private Property readProperty(String[] words) throws NumberFormatException {
        Property property = new Property();
        property.setPropertyIdentificationNumber(words[0]);
        PropertyOwner owner = new PropertyOwner();//TODO read property owner from owner table (vat = words[1])
        property.setOwner(owner);
        property.setAddress(words[2]);
        property.setPropertyType(PropertyType.valueOf(words[3]));
        property.setYearOfConstruction(Integer.valueOf(words[4]));
        property.setActive(Boolean.parseBoolean(words[5]));
        return property;
    }
}
