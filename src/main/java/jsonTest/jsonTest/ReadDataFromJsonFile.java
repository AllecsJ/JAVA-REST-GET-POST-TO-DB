package jsonTest.jsonTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadDataFromJsonFile {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    	
    	
    	

        // Parse JSON using the JSON PARSER
        JSONParser jsonParser = new JSONParser();

        try {
            URL url = new URL("https://randomuser.me/api/?format=json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                Object obj = jsonParser.parse(content.toString());

                JSONObject jsonObject = (JSONObject) obj;

                JSONArray resultsArray = (JSONArray) jsonObject.get("results");
                JSONObject firstResult = (JSONObject) resultsArray.get(0);

                JSONObject nameObj = (JSONObject) firstResult.get("name");
                String title = (String) nameObj.get("title");
                String first = (String) nameObj.get("first");
                String last = (String) nameObj.get("last");

                System.out.println("Title: " + title);
                System.out.println("First Name: " + first);
                System.out.println("Last Name: " + last);

            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
 