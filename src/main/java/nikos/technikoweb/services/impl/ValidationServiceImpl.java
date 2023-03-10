/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.services.impl;


import jakarta.inject.Inject;
import java.time.Instant;
import java.time.ZoneId;
import nikos.technikoweb.enums.UserRole;
import nikos.technikoweb.exceptions.PropertyException;
import nikos.technikoweb.exceptions.PropertyOwnerException;
import nikos.technikoweb.exceptions.PropertyRepairException;
import nikos.technikoweb.exceptions.codes.PropertyExceptionCodes;
import nikos.technikoweb.exceptions.codes.PropertyOwnerExceptionCodes;
import nikos.technikoweb.exceptions.codes.PropertyRepairExceptionCodes;
import nikos.technikoweb.model.Property;
import nikos.technikoweb.model.PropertyRepair;
import nikos.technikoweb.model.User;
import nikos.technikoweb.repository.PropertyOwnerRepository;
import nikos.technikoweb.repository.PropertyRepairRepository;
import nikos.technikoweb.repository.PropertyRepository;
import nikos.technikoweb.services.ValidationService;

/**
 *
 * @author legeo
 */
public class ValidationServiceImpl implements ValidationService {
    @Inject
    private PropertyRepository properties;
    @Inject
    private PropertyRepairRepository propertyRepairs;
    @Inject
    private PropertyOwnerRepository owners;

    public ValidationServiceImpl(PropertyOwnerRepository owners, PropertyRepository properties, PropertyRepairRepository repairs) {
        this.properties = properties;
        this.propertyRepairs = repairs;
        this.owners = owners;
    }

    public void validatePropertyData(Property property) throws PropertyException {
        checkPropertyNullValues(property);
        User owner = property.getOwner();
        validateOwner(owner);
        checkPropertyValidValues(property);
    }

    public void validateOwnerData(User owner) throws PropertyOwnerException {
        checkOwnerNullValues(owner);
        valideateEmail(owner);
        validateDataLength(owner);
        validateUsername(owner);
        validateVat(owner);

    }

    public void validatePropertyRepairData(PropertyRepair propertyRepair) throws PropertyRepairException {
        checkPropertyRepairNullValues(propertyRepair);
        org.hibernate.mapping.Property property = propertyRepair.getProperty();
        validateProperty(property);
        checkPropertyRepairValidValues(propertyRepair);
    }

    private void checkPropertyValidValues(Property property) throws PropertyException {

        if (!property.isActive()) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_NOT_ACTIVE);
        }
        Integer yearOfConstruction = property.getYearOfConstruction();
        if (yearOfConstruction > Instant.now().atZone(ZoneId.systemDefault()).getYear() && yearOfConstruction < 0) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_YEAR_ERROR);
        }
        String address = property.getAddress();
        if (address.length() <= 1) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_ADDRESS_ERROR);
        }
    }

    private void checkPropertyRepairValidValues(PropertyRepair propertyRepair) throws PropertyRepairException {
        if (!propertyRepair.isActive()) {
            throw new PropertyRepairException(PropertyRepairExceptionCodes.PROPERTY_REPAIR_PROPERTY_NOT_FOUND);
        }
        if (propertyRepair.getProposedStartDate() != null && propertyRepair.getProposedEndDate() != null) {
            Instant startDate = propertyRepair.getProposedStartDate();
            Instant endDate = propertyRepair.getProposedStartDate();
            if (startDate.compareTo(endDate) > 0) {
                throw new PropertyRepairException(PropertyRepairExceptionCodes.PROPERTY_REPAIR_PROPERTY_NOT_FOUND);
            }
            Instant nowDate = Instant.now();
            if (nowDate.compareTo(startDate) > 0) {
                throw new PropertyRepairException(PropertyRepairExceptionCodes.PROPERTY_REPAIR_PROPERTY_NOT_FOUND);
            }
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

    private void checkPropertyRepairNullValues(PropertyRepair propertyRepair) throws PropertyRepairException {
        if (propertyRepair == null) {
            throw new PropertyRepairException(PropertyRepairExceptionCodes.PROPERTY_REPAIR_IS_NULL);
        }

        if (propertyRepair.getProperty() == null || propertyRepair.getRepairType() == null
                || propertyRepair.getDescription() == null || propertyRepair.getDateOfSubmission() == null || propertyRepair.getWorkToBeDone() == null /*|| propertyRepair.getProposedStartDate() == null || propertyRepair.getProposedEndDate() == null || propertyRepair.getCost() == null
              || propertyRepair.getStatus() == null*/) {
            throw new PropertyRepairException(PropertyRepairExceptionCodes.PROPERTY_REPAIR_MISSING_DATA);
        }
    }

    private void validateOwner(User owner) throws PropertyException {
        if (owner.getRole().equals(UserRole.ADMIN)) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_ADMIN_OWNER);
        }
        if (!owner.isActive()) {
            throw new PropertyException(PropertyExceptionCodes.PROPERTY_INCATIVE_OWNER);
        }
    }

    private void validateProperty(Property property) throws PropertyRepairException {
        if (!property.isActive()) {
            throw new PropertyRepairException(PropertyRepairExceptionCodes.PROPERTY_REPAIR_PROPERTY_NOT_FOUND);
        }
    }

    private void validateVat(User owner) throws PropertyOwnerException {
        String vat = owner.getVat();
        if (vat.length() != 9) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_VAT_INCORRECT);
        }

        if (!owners.readOwnerByVat(vat).isEmpty()) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_VAT_ALREADY_EXISTS);
        }
    }

    private void validateUsername(User owner) throws PropertyOwnerException {
        String username = owner.getUsername();

        if (!owners.readOwnerByUsername(username).isEmpty()) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_USERNAME_ALREADY_EXISTS);
        }
    }

    private void validateDataLength(User owner) throws PropertyOwnerException {
        if (owner.getPassword().length() <= 3) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_PASSWORD_INCORRECT_INPUT);
        }
        if (owner.getName().length() < 3 || owner.getSurname().length() < 3 || owner.getAddress().length() < 3) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_WRONG_DATA);
        }
        if (owner.getPhoneNumber().length() != 10) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_PHONENUMBER_INCORRECT);
        }
    }

    private void valideateEmail(User owner) throws PropertyOwnerException {
        String email = owner.getEmail();
        if (!email.contains("@")) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_EMAIL_NOT_FOUND);
        }

        if (!owners.readOwnerByEmail(email).isEmpty()) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkOwnerNullValues(User owner) throws PropertyOwnerException {
        if (owner == null) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_NULL);
        }

        if (owner.getAddress() == null || owner.getEmail() == null || owner.getName() == null
                || owner.getPassword() == null || owner.getPhoneNumber() == null
                || owner.getRole() == null || owner.getSurname() == null || owner.getUsername() == null
                || owner.getVat() == null) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_NULL_VALUES);
        }
    }

}
