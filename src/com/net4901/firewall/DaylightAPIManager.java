package com.net4901.firewall;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

public class DaylightAPIManager {
	
	int serverPort;
	String serverIP;
	
	public DaylightAPIManager(String serverIP, int serverPort) {
		this.serverPort = serverPort;
		this.serverIP = serverIP;
	}
	
	public static String executeGet(String targetURL, String urlParameters) {
		  HttpURLConnection connection = null;

		  try {
			  Authenticator.setDefault (new Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication ("admin", "admin".toCharArray());
				    }
				});
			  
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setRequestProperty("Content-Type", 
		        "application/xml");

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return response.toString();
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
		}
	
	public static String executePut(String targetURL, String putData) {
		  HttpURLConnection connection = null;

		  try {
			  Authenticator.setDefault (new Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication ("admin", "admin".toCharArray());
				    }
				});
			  
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("PUT");
		    connection.setRequestProperty("Content-Type", "application/xml");

		    connection.setDoOutput(true);
		    
		    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		    out.write(putData);
		    out.close();
		    	
		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return response.toString();
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
		}
	
	public void addRule(FirewallRule rule) {
		
	}
	
	public Boolean apply() {
		// Call the Daylight API to apply
		return false;
	}
}
