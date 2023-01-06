/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.model;

import jakarta.persistence.Entity;
import lombok.Data;

/**
 *
 * @author legeo
 */
@Data
@Entity
public class PropertyOwner extends Person{
    private String vat;
    private boolean isActive;
    private boolean isAdmin;
   }