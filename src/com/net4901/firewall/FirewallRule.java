package com.net4901.firewall;

public class FirewallRule {
	int sourcePort; 			//-1 is any
	int destinationPort;

	String protocol = "ip"; 	//"tcp", "udp", or "ip"

	int sourceNetwork;
	int sourceMask; // 0.0.0.0

	int destinationNetwork;
	int destinationMask;

	Boolean isDenied;
}
