package com.net4901.firewall;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;


public class CommandLine {

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			// Parse command line arguments into FirewallRules
		}
		
		DaylightAPIManager manager = new DaylightAPIManager("", 1);		
		manager.addRule(new FirewallRule());
		manager.apply();	

//		System.out.println(manager.executeGet("http://134.117.89.170:8181/restconf/config/opendaylight-inventory:nodes/node/openflow:3", ""));
		
		String putData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><flow xmlns=\"urn:opendaylight:flow:inventory\">    <priority>104</priority>    <flow-name>TEST</flow-name>    <match>        <ethernet-match>            <ethernet-type>                <type>2048</type>            </ethernet-type>        </ethernet-match>        <ipv4-source>192.168.1.0/24</ipv4-source>    </match>    <id>1</id>    <table_id>0</table_id>    <instructions>        <instruction>            <order>0</order>            <apply-actions>                <action>                    <order>0</order>                    <drop-action/>                </action>            </apply-actions>        </instruction>    </instructions></flow>";
		System.out.println(manager.executePut("http://134.117.89.170:8181/restconf/config/opendaylight-inventory:nodes/node/openflow:3/table/0/flow/1", putData));
	}
}
