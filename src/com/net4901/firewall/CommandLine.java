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

import org.json.JSONException;


public class CommandLine {

	public static void main(String[] args) throws Exception {
		DaylightAPIManager manager = new DaylightAPIManager();		
		//FirewallRule rule = new FirewallRule(manager.readFlow(false, "openflow:3", 0, 1));
		
		//Initialize variables
		String protocol = null;
		int protocolNum = -1;
		String command = null;
		String nodeID = null;
		int tableID = 0;
		int flowID = 0;
		String source = null;
		String sourceIP = null;
		String sourceMask = null;
		String dest = null;
		String destIP = null;
		String destMask = null;
		//System.out.println(args.length);
		
		//Parse input from command line
		// Question: Should I add checks to make sure user is entering the right number of parameters for each command type?
		if(!(args.length == 4||args.length == 6 || args.length ==8)){
			System.err.println("Command syntax: Command [update|show|delete]  [nodeid] [tableID] [flowID] [source IP/mask] [dest IP/mask] [TCP|ICMP|nothing] [tcp port|icmp type]");
			System.exit(1);
		}
		command = args[0];
		if (command.equals("update")) {
			nodeID = args[1];
			tableID = Integer.parseInt(args[2]);
			flowID = Integer.parseInt(args[3]);
			source = args[4];
			String[] sourceParts = source.split("/");
			sourceIP = sourceParts[0];
			sourceMask = sourceParts[1];
			dest = args[5];
			String[] destParts = dest.split("/");
			destIP = destParts[0];
			destMask = destParts[1];
			if(args.length == 8){
				protocol = args[6];
				protocolNum = Integer.parseInt(args[7]);
			}
		}
		else if(command.equals("delete")||command.equals("show")){
			nodeID = args[1];
			tableID = Integer.parseInt(args[2]);
			flowID = Integer.parseInt(args[3]);
		}
		else{
			System.err.println("Command should be 'update','delete' or 'show'");
			System.exit(1);
		}
		
		//Test args
		System.out.println("Command:     "+command);
		System.out.println("nodeID:      "+nodeID);
		System.out.println("TableID:     "+tableID);
		System.out.println("flowID:      "+flowID);
		System.out.println("Source:      "+source);
		System.out.println("Source IP:   "+sourceIP);
		System.out.println("Source Mask: "+sourceMask);
		System.out.println("Dest:        "+dest);
		System.out.println("Dest IP:     "+destIP);
		System.out.println("Dest Mask:   "+destMask);
		System.out.println("Protocol:    "+protocol);
		System.out.println("ProtocolNum: "+protocolNum);
		
		
		//Create firewall rule and parameters
		//Question: Why are the tableID and flowID in the firewallRule class but then passed separately below?
		// TODO: set the testRule parameters based on the command line parameters
		FirewallRule testRule = new FirewallRule();
		if (command.equals("update")){
			testRule.destinationNetwork = destIP;
			testRule.destinationMask = "" + destMask;
			
			testRule.sourceNetwork = sourceIP;
			testRule.sourceMask = "" + sourceMask;
			
			if(args.length == 8){
				if (protocol.equals("ICMP")){
					// Not sure what to set for ICMP
					//testRule.transportProtocol=2;
				}
				else if(protocol.equals("TCP")){
					testRule.transportProtocol=1;
					testRule.destinationPort=protocolNum;
				}
			}
		}
		
		//Send command 
		if (command.equals("update")) {
			if (manager.updateFlow(testRule,nodeID,tableID,flowID)) {
				System.out.println("Update succeeded.");
			} else {
				System.out.println("Update failed.");
			}
		} else if (command.equals("delete")) {
			if (manager.deleteFlow(nodeID,tableID,flowID)) {
				System.out.println("Delete succeeded.");
			} else {
				System.out.println("Delete failed.");
			}
		} else {
			System.out.println(manager.readFlow(true, nodeID,tableID,flowID));
		}		
	}
}
