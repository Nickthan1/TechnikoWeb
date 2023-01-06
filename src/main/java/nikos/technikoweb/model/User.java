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
public class User extends BaseEntity {
    private String username;
    private String password;
    private String role;
    private boolean isActive=true;
    private String email;
    private String address;
}
