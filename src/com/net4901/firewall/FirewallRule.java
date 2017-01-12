package com.net4901.firewall;

public class FirewallRule {
	String flowName 				= "TEST";
	
	int priority					= 100;
	
	int flowId = 1;
	int tableId = 0;
	
	Boolean matchIcmpEchoRequest 	= false;
	Boolean matchIcmpReply 			= false;	
	
	int transportProtocol 			= -1; 		// -1 is any, 1 is TCP, 2 is UDP
	
	int sourcePort 					= -1; 		//-1 is any
	int destinationPort 			= -1;

	String sourceNetwork			= null;		// eg. 192.168.0.0
	String sourceMask				= null; 	// eg. 24

	String destinationNetwork		= null;		
	String destinationMask			= null;

	Boolean deny					= true;
}
