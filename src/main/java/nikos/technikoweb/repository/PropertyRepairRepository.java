/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.repository;

import java.time.LocalDate;
import java.util.List;
import nikos.technikoweb.model.PropertyRepair;

/**
 *
 * @author legeo
 */
public interface PropertyRepairRepository extends Repository<PropertyRepair, Long>{
    
    /***
     * Search a date
     * @param date
     * @return list of repairs
     */
    List<PropertyRepair> searchDate(LocalDate date);
    /***
     * Search between a date range
     * @param start
     * @param end
     * @return list of repairs
     */
    List<PropertyRepair> searchDateRange(LocalDate start,LocalDate end);
    /***
     * search owner by id
     * @param ownerId
     * @return list of owners
     */
    List<PropertyRepair> searchOwnerId(int ownerId);
    /***
     * search owner by vat
     * @param vat
     * @return list of owners
     */
    List<PropertyRepair> searchOwnerVat(String vat);
    
    /**
     * Search repairs by the the property id
     * @param propertyId the id of the property
     * @return a list of repairs ordered for the specific property
     */
    List<PropertyRepair> searchByProperty(int propertyId);
    
    /**
     * Search repairs by the the property e9
     * @param e9 the e9 of the property
     * @return a list of repairs ordered for the specific property
     */
    List<PropertyRepair> searchByPropertyE9(String e9);
    
}