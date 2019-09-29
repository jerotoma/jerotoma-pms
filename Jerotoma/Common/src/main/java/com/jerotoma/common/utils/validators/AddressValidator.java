package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.AddressConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.addresses.Address;
import com.jerotoma.common.utils.CalendarUtil;

public class AddressValidator {
	
public static Address validateAddress(Map<String, Object> params, List<String> requiredFields) {
		
		Address address = new Address();
		String street  = null;		
		String unit = null;
		String state = null;	
		String postalCode = null;
		String country = null;		
		String city = null;
		Integer id = null;
		
		params = getAddressParams(params);	
		requiredFields.add(AddressConstant.CITY);
		requiredFields.add(AddressConstant.COUNTRY);
		requiredFields.add(AddressConstant.STREET);
		requiredFields.add(AddressConstant.POSTAL_CODE);
		requiredFields.add(AddressConstant.STATE);
		
		if(params.containsKey(AddressConstant.ID)) {
			id  = params.get(AddressConstant.ID) != null ? (Integer) params.get(AddressConstant.ID) : null;
		}
		
		if(params.containsKey(AddressConstant.STREET)) {
			street  = (String) params.get(AddressConstant.STREET);
		}
		
		if(params.containsKey(AddressConstant.UNIT)) {
			unit  = (String) params.get(AddressConstant.UNIT);
		}
		
		if(params.containsKey(AddressConstant.CITY)) {
			city  = (String) params.get(AddressConstant.CITY);
		}
		
		
		if(params.containsKey(AddressConstant.STATE)) {
			state  = (String) params.get(AddressConstant.STATE);
		}
		
		if(params.containsKey(AddressConstant.POSTAL_CODE)) {
			postalCode  = (String) params.get(AddressConstant.POSTAL_CODE);
		}
		
		if(params.containsKey(AddressConstant.COUNTRY)) {
			country = (String) params.get(AddressConstant.COUNTRY);
		}
				
		if (street == null && requiredFields.contains(AddressConstant.STREET)) {
			throw new FieldIsRequiredException("Street is required to continue");
		}
		address.setStreet(street);
		
		if (unit == null && requiredFields.contains(AddressConstant.UNIT)) {
			throw new FieldIsRequiredException("Unit is required to continue");
		}
		address.setUnit(unit);
		
		if (state == null && requiredFields.contains(AddressConstant.STATE)) {
			throw new FieldIsRequiredException("Unit is required to continue");
		}
		address.setState(state);
		
		if (postalCode == null && requiredFields.contains(AddressConstant.POSTAL_CODE)) {
			throw new FieldIsRequiredException("Post Code is required to continue");
		}
		address.setPostalCode(postalCode);
		
		if (country == null && requiredFields.contains(AddressConstant.COUNTRY)) {
			throw new FieldIsRequiredException("Country is required to continue");
		}
		address.setCountry(country);
				
		if (city == null && requiredFields.contains(AddressConstant.CITY)) {
			throw new FieldIsRequiredException("City is required to continue");
		}
		address.setCity(city);
		
		if (id == null && requiredFields.contains(AddressConstant.ID)) {
			throw new FieldIsRequiredException("Address ID is required to continue");
		}
		address.setId(id);
				
		Date today = CalendarUtil.getTodaysDate();
		
		address.setCreatedOn(today);
		address.setUpdatedOn(today);
		
		return address;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> getAddressParams(Map<String, Object> params){
		if(params.containsKey(AddressConstant.ADDRESS)) {
			return (Map<String, Object>) params.get(AddressConstant.ADDRESS);
		}
		return params;
		
	}
}
