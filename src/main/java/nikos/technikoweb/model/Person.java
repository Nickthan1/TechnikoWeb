/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Data;
import nikos.technikoweb.enums.UserRole;

/**
 *
 * @author legeo
 */
@Data
@MappedSuperclass
@Entity
public class Person extends BaseEntity {
   private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    @Column(unique=true, nullable = false)
    private String email;
    @Column(unique=true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique=true, nullable = false)
    private String vat;
    private UserRole role=UserRole.PROPERTY_OWNER;

    private LocalDateTime registrationDate;
}
