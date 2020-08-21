package com.souchy.randd.ebishoal.blueberyl;

import java.util.concurrent.TimeUnit;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;

import tigase.jaxmpp.core.client.BareJID;
import tigase.jaxmpp.core.client.JID;
import tigase.jaxmpp.core.client.SessionObject;
import tigase.jaxmpp.core.client.exceptions.JaxmppException;
import tigase.jaxmpp.core.client.xml.XMLException;
import tigase.jaxmpp.core.client.xmpp.modules.chat.Chat;
import tigase.jaxmpp.core.client.xmpp.modules.chat.MessageModule;
import tigase.jaxmpp.core.client.xmpp.stanzas.Message;
import tigase.jaxmpp.j2se.Jaxmpp;


public class BlueBeryl extends EbiShoalCore {

	public static BlueBeryl beryl;
	
	final Jaxmpp contact = new Jaxmpp();

	public static void main(String[] args) throws Exception {
		new BlueBeryl(args);
	}
	
	
	public BlueBeryl(String[] args) throws Exception {
		super(args);
		beryl = this;
		login();
	}
	
	public void login() throws Exception {
		try {
			tigase.jaxmpp.j2se.Presence.initialize(contact);
			
			contact.getModulesManager().register(new MessageModule());
			
			contact.getProperties().setUserProperty(SessionObject.USER_BARE_JID, BareJID.bareJIDInstance("admin@atlantiscity"));
			contact.getProperties().setUserProperty(SessionObject.PASSWORD, "admin");
			
			contact.getEventBus().addHandler(MessageModule.MessageReceivedHandler.MessageReceivedEvent.class, new MessageModule.MessageReceivedHandler() {
				@Override
				public void onMessageReceived(SessionObject sessionObject, Chat chat, Message stanza) {
					try {
						System.out.println("received message: " + stanza.getBody());
					} catch (XMLException e) {
						e.printStackTrace();
					}
				}
			});
			
			Log.info("Loging in...");
			
			contact.login(true);
			
			if(contact.isConnected()) {
				TimeUnit.MINUTES.sleep(1);
				contact.disconnect();
			}
		} catch (Exception e) {
			Log.info("", e);
		}
		
	}

	@Override
	protected String[] getRootPackages() {
		return new String[] { 
					"com.souchy.randd.commons.deathebi.msg", 
					"com.souchy.randd.deathshadows.nodes.pearl.messaging",
					"com.souchy.randd.deathshadows.nodes.pearl.messaging.moonstone",
					"com.souchy.randd.deathshadows.greenberyl"
				};
	}
	
}
