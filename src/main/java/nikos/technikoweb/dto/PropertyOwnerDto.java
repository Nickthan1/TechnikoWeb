/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import nikos.technikoweb.enums.UserRole;
import nikos.technikoweb.model.PropertyOwner;

/**
 *
 * @author legeo
 */
@Data
@NoArgsConstructor
public class PropertyOwnerDto {
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String vat;
    private UserRole role=UserRole.PROPERTY_OWNER;
    @JsonSerialize(using = nikos.technikoweb.serializer.LocalDateTimeSerializer.class)
    @JsonDeserialize(using = nikos.technikoweb.serializer.LocalDateTimeDeserializer.class)
    private LocalDateTime registrationDate;
    
    public PropertyOwnerDto (PropertyOwner propertyOwner) {
      if (propertyOwner != null) {
        vat = propertyOwner.getVat();
        name = propertyOwner.getName();
        email = propertyOwner.getEmail();
        surname = propertyOwner.getSurname();
        username = propertyOwner.getUsername();
        password = propertyOwner.getPassword();
        phoneNumber = propertyOwner.getPhoneNumber();
        address = propertyOwner.getAddress();
      }
    }
    
    public PropertyOwner asPropertyOwner() {
      PropertyOwner propertyOwner = new PropertyOwner();
      propertyOwner.setVat(vat);
      propertyOwner.setName(name);
      propertyOwner.setEmail(email);
      propertyOwner.setAddress(address);
      propertyOwner.setPhoneNumber(phoneNumber);
      propertyOwner.setSurname(surname);
      propertyOwner.setPassword(password);
      return propertyOwner;
    }
}
