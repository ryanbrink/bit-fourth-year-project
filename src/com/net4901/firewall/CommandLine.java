package com.net4901.firewall;

public class CommandLine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello!");
		
		for (int i = 0; i < args.length; i++) {
			// Parse command line arguments into FirewallRules
		}
		
		DaylightAPIManager manager = new DaylightAPIManager("", 1);		
		manager.addRule(new FirewallRule());
		manager.apply();		
	}

}
