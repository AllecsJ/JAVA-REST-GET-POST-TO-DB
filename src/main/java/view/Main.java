package view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.parser.ParseException;

import controller.DatabaseConnection;
import controller.JSONHandler;
import controller.QueryController;
import controller.UserDataController;
import model.User;

public class Main {
    public static void main(String[] args) throws IOException, java.text.ParseException, ParseException {
    	
    	
    	JSONHandler jsonHandler = new JSONHandler();
    	DatabaseConnection dbConnection = new DatabaseConnection();
        UserDataView userDataView = new UserDataView();
        UserDataController userDataController = new UserDataController(jsonHandler, userDataView);
        userDataController.fetchAndDisplayUserData();
        //User newUser = userDataController.fetchAndDisplayUserData();
        
        
        Connection connection = dbConnection.getConnection();    
        User newUser = jsonHandler.fetchUserData();
        
        
//        QueryController userController = new QueryController(jsonHandler, userDataView);
//        userController.insertUser(jsonHandler, dbConnection);
        
        
     // Create an instance of QueryController
        QueryController queryInsert = new QueryController(jsonHandler, userDataView);
        // Call the insertUser method and pass the User object and connection
        queryInsert.insertUser(newUser, connection);
        
        
        
        //retrive from database and query information
        QueryController queryRetrieve = new QueryController(jsonHandler, userDataView);
        List<model.User> dataList = queryRetrieve.retrieveData(connection);
        queryInsert.postData(dataList, "your_api_url");
       
    }
}

