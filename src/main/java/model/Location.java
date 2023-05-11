package model;

//This class represents the Location model with properties street_number, street_name, city, state, country, and postcode
public class Location {
 private String streetNumber;
 public String getStreetNumber() {
	return streetNumber;
}

public void setStreetNumber(String streetNumber) {
	this.streetNumber = streetNumber;
}

public String getStreetName() {
	return streetName;
}

public void setStreetName(String streetName) {
	this.streetName = streetName;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

public String getPostcode() {
	return postcode;
}

public void setPostcode(String postcode) {
	this.postcode = postcode;
}

private String streetName;
 private String city;
 private String state;
 private String country;
 private String postcode;

 // Constructor to initialize a Location object
 public Location(String streetNumber, String streetName, String city, String state, String country, String postcode) {
     this.streetNumber = streetNumber;
     this.streetName = streetName;
     this.city = city;
     this.state = state;
     this.country = country;
     this.postcode = postcode;
 }

 // Getters and setters for each field...
}
