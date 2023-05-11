package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import model.User;
import view.UserDataView;

public class UserDataController   {
    private JSONHandler jsonHandler;
    private UserDataView userDataView;
    private QueryController queryController;
    private DatabaseConnection dbConnection;

    public UserDataController(JSONHandler jsonHandler, UserDataView userDataView) {
        this.jsonHandler = jsonHandler; // Assign the provided JSONHandler object to the instance variable jsonHandler
        this.userDataView = userDataView; // Assign the provided UserDataView object to the instance variable userDataView
    }
    
    //gets data from json handler and outputs itn
    public void fetchAndDisplayUserData() throws IOException, ParseException, org.json.simple.parser.ParseException {
        try {
            User user = jsonHandler.fetchUserData(); // Fetch user data using the jsonHandler object and assign it to the user variable
            userDataView.displayUserData(user); // Display the fetched user data using the userDataView object
        } catch (IOException e) {
            e.printStackTrace(); // If an IOException occurs during the fetching process, print the stack trace
        }
    }
    
    //get data from jason handler and display it
    public void insertData() throws IOException, org.json.simple.parser.ParseException, SQLException {
    	User user = jsonHandler.fetchUserData();
    	Connection connection = DatabaseConnection.getConnection();
    	queryController.insertUser(user, connection);
    	connection.close();
    }

}

