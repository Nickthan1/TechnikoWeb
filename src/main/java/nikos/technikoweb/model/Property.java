/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nikos.technikoweb.enums.PropertyType;

/**
 *
 * @author legeo
 */
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Property extends BaseEntity{
    @Column(unique=true)
    private String propertyIdentificationNumber;
    private String address;
    private Integer yearOfConstruction;
    private PropertyType propertyType;
    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;
    private boolean isActive;
}
