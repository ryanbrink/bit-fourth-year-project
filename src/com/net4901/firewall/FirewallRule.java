package com.net4901.firewall;

public class FirewallRule {
	int flowId = 1;
	int tableId = 0;
	
	int sourcePort; 			//-1 is any
	int destinationPort;

	String sourceNetwork;
	String sourceMask; // CIDR

	String destinationNetwork;
	String destinationMask;

	Boolean isDenied;
}
