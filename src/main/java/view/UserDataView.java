package view;

import model.User;
import controller.JSONHandler;
import controller.UserDataController;

import model.Location;
import model.Login;
import model.Picture;

import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.net.MalformedURLException;


public class UserDataView {
	
	public void displayUserData(User user) {
        System.out.println("Title: " + user.getTitle());
        System.out.println("First Name: " + user.getFirstName());
        System.out.println("Last Name: " + user.getLastName());
        System.out.println("Gender: " + user.getGender());
        System.out.println("Email: " + user.getEmail());

        Location location = user.getLocation();
        System.out.println("Street Number: " + location.getStreetNumber());
        System.out.println("Street Name: " + location.getStreetName());
        System.out.println("City: " + location.getCity());
        System.out.println("State: " + location.getState());
        System.out.println("Country: " + location.getCountry());
        System.out.println("Postcode: " + location.getPostcode());

        Login login = user.getLogin();
        System.out.println("UUID: " + login.getUuid());
        System.out.println("Username: " + login.getUsername());
        System.out.println("Password: " + login.getPassword());
        System.out.println("Salt: " + login.getSalt());
        System.out.println("MD5: " + login.getMd5());
        System.out.println("SHA1: " + login.getSha1());
        System.out.println("SHA256: " + login.getSha256());

        Picture picture = user.getPicture();
        System.out.println("Large Picture: " + picture.getLarge());
        System.out.println("Medium Picture: " + picture.getMedium());
        System.out.println("Thumbnail: " + picture.getThumbnail());
    }
}
