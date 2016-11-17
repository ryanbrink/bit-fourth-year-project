package com.net4901.firewall;

public class DaylightAPIManager {
	
	int serverPort;
	String serverIP;
	
	public DaylightAPIManager(String serverIP, int serverPort) {
		this.serverPort = serverPort;
		this.serverIP = serverIP;
	}
	
	public void addRule(FirewallRule rule) {
		
	}
	
	public Boolean apply() {
		// Call the Daylight API to apply
		return false;
	}
}
