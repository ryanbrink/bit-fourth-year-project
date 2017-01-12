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
		String protocol = null;
		int protocolNum = 0;
		String command = null;
		String nodeID = null;
		int tableID = 0;
		int flowID = 0;
		String source = null;
		String sourceIP = null;
		int sourceMask = 0;
		String dest = null;
		String destMask = null;
		int destPort = 0;
		System.out.println(args.length);
		if(!(args.length == 6 || args.length ==8)){
			System.err.println("Command syntax: Command [Set|show|Delete]  [nodeid] [tableID] [flowID] [source IP/mask] [dest IP/mask] [TCP|ICMP|nothing] [tcp port|icmp type]");
			System.exit(1);
		}
		command = args[0];
		//add if statement for what the command type is
		nodeID = args[1];
		tableID = Integer.parseInt(args[2]);
		flowID = Integer.parseInt(args[3]);
		source = args[4];
		String[] sourceParts = source.split("/");
		sourceIP = sourceParts[0];
		sourceMask = Integer.parseInt(sourceParts[1]);
		dest = args[5];
		String[] destParts = dest.split("/");
		destMask = destParts[0];
		destPort = Integer.parseInt(destParts[1]);
		if(args.length == 8){
			protocol = args[6];
			protocolNum = Integer.parseInt(args[7]);
		}
		
		DaylightAPIManager manager = new DaylightAPIManager("", 1);		
		
		//Test args
		System.out.println("Command:     "+command);
		System.out.println("nodeID:      "+nodeID);
		System.out.println("TableID:     "+tableID);
		System.out.println("flowID:      "+flowID);
		System.out.println("Source:      "+source);
		System.out.println("Source IP:   "+sourceIP);
		System.out.println("Source Mask: "+sourceMask);
		System.out.println("Dest:        "+dest);
		System.out.println("Dest IP:     "+destMask);
		System.out.println("Dest Mask:   "+destPort);
		System.out.println("Protocol:    "+protocol);
		System.out.println("ProtocolNum: "+protocolNum);
		
		
		
		FirewallRule testRule = new FirewallRule();
		testRule.destinationNetwork = "192.168.2.3";
		testRule.destinationMask = "32";
		testRule.deny = true;
		//manager.updateFlow(testRule,"openflow:3",0,1);//object, node, table, flowID
		System.out.println("Ran Rule");
	}
}
