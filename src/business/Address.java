package business;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/* Immutable */
final public class Address implements Serializable {
	
	private static final long serialVersionUID = -891229800414574888L;
	private String street;
	private String city;
	private String state;
	private IntegerProperty postalCode;
	private String zip;

	public Address() {
		
		
	}
	
	public Address(String street, String city, String state,String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
	this.zip = zip;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPostalCode() {
		return postalCode.get();
	}

	public void setPostalCode(int postalCode) {
		this.postalCode.set(postalCode);
	}

	public IntegerProperty postalCodeProperty() {
		return postalCode;
	}
	

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	
	@Override
	public String toString() {
		return "(" + street + ", " + city + ", " + zip + ")";
		
	}
}
