package controller;

import model.Location;
import model.Login;
import model.Picture;
import model.User;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.jar.Attributes.Name;




public class JSONHandler {

	public User fetchUserData() throws IOException, ParseException {
	    JSONParser jsonParser = new JSONParser();
	    URL url = new URL("https://randomuser.me/api/?format=json");
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.connect();

	    int responseCode = connection.getResponseCode();
	    User user = null;

	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String inputLine;
	        StringBuilder content = new StringBuilder();
	        while ((inputLine = in.readLine()) != null) {
	            content.append(inputLine);
	        }
	        // close connections
	        in.close();
	        connection.disconnect();

	        // Parse the JSON content
	        JSONObject jsonObject = (JSONObject) jsonParser.parse(content.toString());

	        // The JSON from randomuser.me wraps the user data in a "results" array
	        JSONArray results = (JSONArray) jsonObject.get("results");

	        if (results.size() > 0) {
	            // Get the first result (user)
	            JSONObject jsonUser = (JSONObject) results.get(0);

	            // Now create and populate your User object from the JSON data
	            user = new User();
	            
	            JSONObject jsonName = (JSONObject) jsonUser.get("name");
	            user.setTitle((String) jsonName.get("title"));
	            user.setFirstName((String) jsonName.get("first"));
	            user.setLastName((String) jsonName.get("last"));
	            
	            
	            user.setGender((String) jsonUser.get("gender"));
	            user.setEmail((String) jsonUser.get("email"));
	            
//	         // Get nested JSONObjects and populate other model classes
//	            JSONObject dob = (JSONObject) jsonUser.get("dob");
//	            user.setDob((String) dob.get("date"));
//	            user.setAge((String) dob.get("age"));
//	            
//	         // Get nested JSONObjects and populate other model classes
//	            JSONObject Registered = (JSONObject) jsonUser.get("Registered");
//	            user.setRegistered_date((String) Registered.get("date"));
//	            user.setRegistered_date((String) Registered.get("age"));
	            
	         // Get nested JSONObjects and populate other model classes
	            user.setPhone((String) jsonUser.get("phone"));
	            user.setCell((String) jsonUser.get("cell"));
	            user.setNat((String) jsonUser.get("nat"));
	            
	            
		      

	            // Get nested JSONObjects and populate other model classes
	            JSONObject Location = (JSONObject) jsonUser.get("location");
	            JSONObject street = (JSONObject) Location.get("street");
		         String streetNumber = String.valueOf(street.get("number"));
		         String streetName = (String) street.get("name");
		         String city = (String) Location.get("city");
		         String state = (String) Location.get("state");
		         String country = (String) Location.get("country");
		         String postcode = String.valueOf(Location.get("postcode"));
		         //Initialize location object
		         Location location = new Location(streetNumber, streetName, city, state, country, postcode);
		         // Set the location object on the User object
		         user.setLocation(location);
	

	            JSONObject jsonLogin = (JSONObject) jsonUser.get("login");
	            String uuid = (String) jsonLogin.get("uuid");
	            String username = (String) jsonLogin.get("username");
	            String password = (String) jsonLogin.get("password");
	            String salt = (String) jsonLogin.get("salt");
	            String md5 = (String) jsonLogin.get("md5");
	            String sha1 = (String) jsonLogin.get("sha1");
	            String sha256 = (String) jsonLogin.get("sha256");
	            //Initialize login object
	            Login login = new Login(uuid, username, password, salt, md5, sha1, sha256);
	            // Set the Login object on the User object
	            user.setLogin(login);
	            

	            JSONObject jsonPicture = (JSONObject) jsonUser.get("picture");
	            String large = (String)jsonPicture.get("large");
	            String medium = (String)jsonPicture.get("large") ;
	            String thumbnail = (String)jsonPicture.get("large"); 
	            //Initialize login object
	            Picture picture = new Picture( large,  medium,  thumbnail);
	            // Set the Login object on the User object
	            user.setPicture(picture);

	        }
	    }

	    return user;
	}
}







//
//public class JSONHandler {
//
//	public User fetchUserData() throws IOException, ParseException {
//	    JSONParser jsonParser = new JSONParser();
//	    URL url = new URL("https://randomuser.me/api/?format=json");
//	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//	    connection.setRequestMethod("GET");
//	    connection.connect();
//
//	    int responseCode = connection.getResponseCode();
//	    User user = null;
//
//	    if (responseCode == HttpURLConnection.HTTP_OK) {
//	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//	        String inputLine;
//	        StringBuilder content = new StringBuilder();
//	        while ((inputLine = in.readLine()) != null) {
//	            content.append(inputLine);
//	        }
//	        in.close();
//	        
////	        if (location.get("street") != null) {
////	            streetName = location.get("street").toString();
////	        }
////	       
//	        
//	        JSONObject json = (JSONObject) jsonParser.parse(content.toString());
//	        final JSONArray results = (JSONArray) json.get("results");
//	        final JSONObject userData = (JSONObject) results.get(0);
//
//	        JSONObject name = (JSONObject) userData.get("name");
//	        final String firstName = (String) name.get("first");
//	        final String lastName = (String) name.get("last");
//
//	        JSONObject location = (JSONObject) userData.get("location");
//	        JSONObject street = (JSONObject) location.get("street"); //street is 
//	        final String streetNumber = (String) street.get("number").toString();
//	        final String streetName = (String) street.get("name");
//	        final String city = (String) location.get("city");
//	        final String state = (String) location.get("state");
//	        final String country = (String) location.get("country");
//	        final String postCode = location.get("postcode").toString();
//
//	         JSONObject coordinates = (JSONObject) location.get("coordinates");
//	        final String latitude = (String) coordinates.get("latitude");
//	        final String longitude = (String) coordinates.get("longitude");
//
//	        final String email = (String) userData.get("email");
//	        JSONObject login = (JSONObject) userData.get("login");
//	        final String username = (String) login.get("username");
//	        final String password = (String) login.get("password");
//
//	         JSONObject dob = (JSONObject) userData.get("dob");
//	        final String dateOfBirth = (String) dob.get("date");
//	        final int age = ((Long) dob.get("age")).intValue();
//
//	        final JSONObject pictureObject = (JSONObject) userData.get("picture");
//	        final String picture = (String) pictureObject.get("medium");
//
//
//	        user = new User() {
//	            {
//	                setFirstName(firstName);
//	                setLastName(lastName);
//	                setStreetNumber(streetNumber);
//	                setStreetName(streetName);
//	                setCity(city);
//	                setState(state);
//	                setCountry(country);
//	                setPostCode(postCode);
//	                setLatitude(latitude);
//	                setLongitude(longitude);
//	                setEmail(email);
//	                setUsername(username);
//	                setPassword(password);
//	                setDateOfBirth(dateOfBirth);
//	                setAge(age);
//	                setPicture(picture);
//	                //setRole();
//	            }
//	        };
//	    }
//	        return user;
//	   }
//	}

	  