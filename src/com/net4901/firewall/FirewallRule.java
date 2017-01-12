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
	
	public FirewallRule(String ruleJSON) {
		JSONObject obj = new JSONObject(" .... ");
		String pageName = obj.getJSONObject("pageInfo").getString("pageName");

		JSONArray arr = obj.getJSONArray("posts");
		for (int i = 0; i < arr.length(); i++)
		{
		    String post_id = arr.getJSONObject(i).getString("post_id");
		    ......
		}
	}
}
