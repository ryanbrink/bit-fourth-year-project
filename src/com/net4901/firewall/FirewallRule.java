package com.net4901.firewall;

import org.json.*;

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
	
	public FirewallRule() {
		
	}
	
	// TODO: complete this
	// Create a rule from the response JSON
	public FirewallRule(String ruleJSON) throws JSONException {
		JSONObject firewallJSONObj = new JSONObject(ruleJSON).getJSONArray("flow-node-inventory:flow").optJSONObject(0);
		
		if (firewallJSONObj.getJSONObject("match").getString("ipv4-source") != null) {
			sourceNetwork = firewallJSONObj.getJSONObject("match").getString("ipv4-source").split("/")[0];
			sourceMask = firewallJSONObj.getJSONObject("match").getString("ipv4-source").split("/")[1];
		}
		
		if (firewallJSONObj.getJSONObject("match").getString("ipv4-destination") != null) {
			destinationNetwork = firewallJSONObj.getJSONObject("match").getString("ipv4-destination").split("/")[0];
			destinationMask = firewallJSONObj.getJSONObject("match").getString("ipv4-destination").split("/")[1];
		}
	}
}
