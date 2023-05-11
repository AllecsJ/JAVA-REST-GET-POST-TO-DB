package controller;

import java.io.IOException;
import java.sql.CallableStatement;
import controller.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.simple.parser.ParseException;

import model.Location;
import model.Login;
import model.Picture;
import model.User;
import view.UserDataView;


import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// This class represents the QueryController that interacts with the model (User) and database
public class QueryController  {
	private JSONHandler jsonHandler;
    private UserDataView userDataView;
    private QueryController queryController;
    private DatabaseConnection dbConnection;
    
    
    
    public QueryController(JSONHandler jsonHandler, UserDataView userDataView) {
        this.jsonHandler = jsonHandler; // Assign the provided JSONHandler object to the instance variable jsonHandler
        this.userDataView = userDataView; // Assign the provided UserDataView object to the instance variable userDataView
    }
    
    
    // Method to insert a user into the database
    public void insertUser(User user, Connection connection) throws IOException, ParseException {
    	User newUser = jsonHandler.fetchUserData();
    	
    	String insertUserProcedure = "{CALL [UserInfo].[dbo].[insertUser](?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    	System.out.println("inserting into..."+insertUserProcedure);
    	try (CallableStatement statement = connection.prepareCall(insertUserProcedure)) {
    		// Setting the input parameters for the stored procedure
    		statement.setString(1, newUser.getTitle()); // VARCHAR
    		statement.setString(2, newUser.getFirstName()); // VARCHAR
    		statement.setString(3, newUser.getLastName()); // VARCHAR
    		statement.setString(4, newUser.getGender()); // VARCHAR
    		statement.setString(5, newUser.getEmail()); // VARCHAR
    		statement.setString(6, newUser.getDob()); // DATETIME
    		statement.setString(7, newUser.getRegistered()); // DATETIME
    		statement.setString(8, newUser.getPhone()); // VARCHAR
    		statement.setString(9, newUser.getCell()); // VARCHAR
    		statement.setString(10, newUser.getNat()); // VARCHAR
    		 
    		
    		
    		

    		 Location location = newUser.getLocation();
    		 if (location != null) {
    		     statement.setString(11, location.getStreetNumber()); // INT
    		     statement.setString(12, location.getStreetName()); // VARCHAR
    		     statement.setString(13, location.getCity()); // VARCHAR
    		     statement.setString(14, location.getState()); // VARCHAR
    		     statement.setString(15, location.getCountry()); // VARCHAR
    		     statement.setString(16, location.getPostcode()); // VARCHAR
    		 } else {
    		     // Handle the case where the Location object is null
    		     statement.setNull(11, java.sql.Types.INTEGER);
    		     statement.setNull(12, java.sql.Types.VARCHAR);
    		     statement.setNull(13, java.sql.Types.VARCHAR);
    		     statement.setNull(14, java.sql.Types.VARCHAR);
    		     statement.setNull(15, java.sql.Types.VARCHAR);
    		     statement.setNull(16, java.sql.Types.VARCHAR);
    		 }

    		Login login = newUser.getLogin();
    		if (login != null) {
    		    statement.setString(17, login.getUuid()); // VARCHAR
    		    statement.setString(18, login.getUsername()); // VARCHAR
    		    statement.setString(19, login.getPassword()); // VARCHAR
    		    statement.setString(20, login.getSalt()); // VARCHAR
    		    statement.setString(21, login.getMd5()); // VARCHAR
    		    statement.setString(22, login.getSha1()); // VARCHAR
    		    statement.setString(23, login.getSha256()); // VARCHAR
    		} else {
    		    // Handle the case where the Login object is null
    		    statement.setNull(17, java.sql.Types.VARCHAR);
    		    statement.setNull(18, java.sql.Types.VARCHAR);
    		    statement.setNull(19, java.sql.Types.VARCHAR);
    		    statement.setNull(20, java.sql.Types.VARCHAR);
    		    statement.setNull(21, java.sql.Types.VARCHAR);
    		    statement.setNull(22, java.sql.Types.VARCHAR);
    		    statement.setNull(23, java.sql.Types.VARCHAR);
    		}

    		Picture picture = newUser.getPicture();
    		if (picture != null) {
    		    statement.setString(24, picture.getLarge()); // VARCHAR
    		    statement.setString(25, picture.getMedium()); // VARCHAR
    		    statement.setString(26, picture.getThumbnail()); // VARCHAR
    		} else {
    		    // Handle the case where the Picture object is null
    		    statement.setNull(24, java.sql.Types.VARCHAR);
    		    statement.setNull(25, java.sql.Types.VARCHAR);
    		    statement.setNull(26, java.sql.Types.VARCHAR);
    		}
    		
    		


    	    // Executing the stored procedure
    	    statement.execute();
    	    
    	    

    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    	
  }
    
   //get data from sql and puts it in a list before posting
    public List<model.User> retrieveData(Connection connection) {
        List<model.User> dataList = new ArrayList<>();

        try (CallableStatement statement = connection.prepareCall("{CALL [getUser]}")) {
            // Execute the stored procedure and retrieve the result set
            ResultSet resultSet = statement.executeQuery();

            // Process the result set and create a list of objects
            while (resultSet.next()) {
                // Retrieve the desired data from the result set
                String data1 = resultSet.getString("first_name");
                String data2 = resultSet.getString("last_name");

                // Create an instance of User and populate it with the retrieved data
                model.User userInfo = new model.User();
                userInfo.setFirstName(data1);
                userInfo.setLastName(data2);

                // Add the data object to the list
                dataList.add(userInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    
    //convert list to json and post
    public void postData(List<model.User> dataList, String apiUrl) throws UnsupportedEncodingException {
        // Convert the list of objects to JSON using Gson library
        Gson gson = new Gson();
        String jsonData = gson.toJson(dataList);
        System.out.println("The data in JSON FORMAT TO BE POSTED: " + jsonData);

        // Perform the POST request to the API
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setEntity(new StringEntity(jsonData));

        // Set the necessary headers for the POST request
        httpPost.setHeader("Content-type", "application/json");

        try {
            // Execute the POST request
            HttpResponse response = httpClient.execute(httpPost);

            // Handle the response from the API
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
                String line;
                StringBuilder responseJson = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    responseJson.append(line);
                }

                // Process the response JSON if needed
                System.out.println(responseJson.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            
            System.out.println("URL ERROR: JSON COULD NOT BE PSOTED " + jsonData);
        }
    }
    
    
    //execure and post
    public void executeStoredProcedureAndPost(Connection connection, String apiUrl) throws UnsupportedEncodingException {
        List<model.User> dataList = retrieveData(connection);
        for (model.User userInfo : dataList) {
            System.out.println("First Name: " + userInfo.getFirstName());
            System.out.println("Last Name: " + userInfo.getLastName());
            // Output other properties if needed
            System.out.println();
        }
       
        postData(dataList, apiUrl);
    }
    
    
    
    
}
