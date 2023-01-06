package nikos.technikoweb.services;

import java.util.List;
import nikos.technikoweb.dto.PropertyOwnerDto;
import nikos.technikoweb.dto.PropertyRepairDto;

public interface RegisterService {

    PropertyOwnerDto create(PropertyOwnerDto propertyOwnerDto);
    PropertyOwnerDto read(long id);
    List<PropertyOwnerDto> read();
    
    PropertyRepairDto createPropertyRepair(long propertyOwnerId);

}