package com.net4901.firewall;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

public class DaylightAPIManager {
	
	String serverPort = "8181";
	String serverIP = "134.117.89.170";
	
	String username = "admin";
	String password = "admin";
	
	public DaylightAPIManager(){
	
	}
	
	public DaylightAPIManager(String serverIP, String serverPort) {
		this.serverPort = serverPort;
		this.serverIP = serverIP;
	}
	
	private String flowURL(Boolean isOperational, String nodeId, int tableId, int flowId) {
		return "restconf/" + (isOperational ? "operational" : "config") + "/opendaylight-inventory:nodes/node/" + nodeId + 
				"/table/"+tableId + 
				"/flow/" + flowId;
	}
	
	public Boolean deleteFlow(String nodeId, int tableId, int flowId) {
		return executeDelete(flowURL(false, nodeId, tableId, flowId));
	}
	
	public Boolean updateFlow(FirewallRule rule, String nodeId) throws Exception {		
		return executePut(flowURL(false, nodeId, rule.tableId, rule.flowId), getRuleXML(rule, nodeId, rule.tableId, rule.flowId));
	}
	
	public String readFlow(Boolean readOperational, String nodeId, int tableId, int flowId) {
		return executeGet(flowURL(readOperational, nodeId, tableId, flowId));
	}
	
	private Boolean executeDelete(String targetPath) {
		HttpURLConnection connection = null;
		  String targetURL = "http://" + serverIP + ":"+ serverPort +"/" + targetPath;
		  try {
			  Authenticator.setDefault (new Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication (username, password.toCharArray());
				    }
				});
			  
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("DELETE");
		    connection.setRequestProperty("Content-Type", 
		        "application/xml");

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
		  } catch (Exception e) {
		    e.printStackTrace();
		    return false;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
	}
	
	private String executeGet(String targetPath) {
		  HttpURLConnection connection = null;
		  String targetURL = "http://" + serverIP + ":"+ serverPort +"/" + targetPath;
		  try {
			  Authenticator.setDefault (new Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication (username, password.toCharArray());
				    }
				});
			  
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setRequestProperty("Content-Type", 
		        "application/xml");

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return response.toString();
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
		}
	
	private Boolean executePut(String targetPath, String putData) {
		  HttpURLConnection connection = null;		 
		  String targetURL = "http://" + serverIP + ":"+ serverPort +"/" + targetPath;
		  StringBuilder response = new StringBuilder();
		  try {
			  Authenticator.setDefault (new Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication (username, password.toCharArray());
				    }
				});
			  
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("PUT");
		    connection.setRequestProperty("Content-Type", "application/xml");

		    connection.setDoOutput(true);
		    
		    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		    out.write(putData);
		    out.close();
		    	
		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();		
		    return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
		  } catch (Exception e) {
		    e.printStackTrace();
		    System.err.println("Request XML was:");
		    System.err.println(putData);
		    System.err.println("Response was:");
		    System.err.println(response);
		    return false;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }		    
		  }
		  
		  
		}
	
	private String getRuleXML(FirewallRule rule, String targetNode, int targetTableId, int targetFlowId) throws Exception {
		String firewallRuleXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><flow xmlns=\"urn:opendaylight:flow:inventory\">"+
						"<priority>" + rule.priority + "</priority>" +
						 "<flow-name>" + rule.flowName + "</flow-name>";
		
		// Always match IP packets
		firewallRuleXML += "<match><ethernet-match><ethernet-type><type>2048</type></ethernet-type></ethernet-match>";
		
		// Destination network matching
		if (rule.destinationNetwork != null) {
			firewallRuleXML += "<ipv4-destination>" + rule.destinationNetwork + "/" + rule.destinationMask + "</ipv4-destination>";
		}

		// Source network matching
		if (rule.sourceNetwork != null) {
			firewallRuleXML += "<ipv4-source>" + rule.sourceNetwork + "/" + rule.sourceMask + "</ipv4-source>";
		}
		
		if (rule.transportProtocol != -1 && (rule.matchIcmpEchoRequest || rule.matchIcmpReply)) {
			throw new Exception("You can't match ICMP AND UDP or TCP!");
		}
		
		// Transport and ICMP matching
		if (rule.transportProtocol != -1 || rule.matchIcmpEchoRequest || rule.matchIcmpReply) {
			firewallRuleXML += "<ip-match>";
			
			// Transport protocol matching
			if (rule.transportProtocol != -1) {
				if (rule.transportProtocol == 1) {
					firewallRuleXML += "<ip-protocol>6</ip-protocol>";
				} else {
					firewallRuleXML += "<ip-protocol>17</ip-protocol>";
				}	            
			}
			
			// ICMP matching
			if (rule.matchIcmpEchoRequest || rule.matchIcmpReply) {
				firewallRuleXML+= "<ip-protocol>1</ip-protocol>";								
			}
			
			firewallRuleXML += "</ip-match>";
						
			// Transport port matching
			if (rule.transportProtocol == 1) {
				if (rule.destinationPort != -1) {
					firewallRuleXML += "<tcp-destination-port>"+ rule.destinationPort + "</tcp-destination-port>";
				}
				
				if (rule.sourcePort != -1) {
					firewallRuleXML += "<tcp-source-port>"+ rule.destinationPort + "</tcp-source-port>";
				}
			} else if (rule.transportProtocol == 2) {
				// UDP!
			}
			
			if (rule.matchIcmpEchoRequest || rule.matchIcmpReply) {
				firewallRuleXML += "<icmpv4-match>";
				if (rule.matchIcmpEchoRequest) {
					firewallRuleXML += "<icmpv4-type>8</icmpv4-type><icmpv4-code>0</icmpv4-code>";
				}
				
				if (rule.matchIcmpReply) {
					firewallRuleXML += "<icmpv4-type>0</icmpv4-type><icmpv4-code>0</icmpv4-code>";
				}
				firewallRuleXML += "</icmpv4-match>";
			}
		}				
		
		firewallRuleXML += "</match>";
		
		firewallRuleXML += "<id>" + rule.flowId + "</id><table_id>" + rule.tableId + "</table_id>";
		
		if (rule.deny) {
			firewallRuleXML += "<instructions>";
			firewallRuleXML += "<instruction><order>0</order><apply-actions><action><order>0</order><drop-action/></action></apply-actions></instruction>";
			firewallRuleXML += "</instructions>";
		}
		firewallRuleXML += "</flow>";		
		
		return firewallRuleXML;
	}
}
