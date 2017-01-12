package com.net4901.firewall;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;


public class CommandLine {

	public static void main(String[] args) throws IOException {
		for (int i = 0; i < args.length; i++) {
			// Parse command line arguments into FirewallRules
		}
		
		DaylightAPIManager manager = new DaylightAPIManager("", 1);		
		manager.addRule(new FirewallRule());
		manager.apply();	
		
		FirewallRule testRule = new FirewallRule();
		testRule.destinationNetwork = "192.168.2.3";
		testRule.destinationMask = "32";
		testRule.isDenied = true;
		System.out.println("Test");
		manager.addRule(testRule);
	}
}
