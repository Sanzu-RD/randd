package com.souchy.randd.deathshadows.nodes.pearl;

import java.io.File;
import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilderEncoder;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilderPort;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
//import com.souchy.randd.deathshadows.commons.core.CoreServer;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;

/**
 * 
 * Server for managin/monitoring and messaging between server nodes
 * 
 * Not date of creation, but modernisation.
 * 
 * @author Blank
 * @date 27 mai 2020
 */
public final class Pearl extends DeathShadowCore {
	
	public static Pearl core;
	public final DeathShadowTCP server;
	
	public static void main(String[] args) throws Exception {
		core = new Pearl(args);
	}
	
	public Pearl(String[] args) throws Exception {
		super(args);
		int port = 1000;
		if(args.length > 0) port = Integer.parseInt(args[0]);
		
		// start server
		server = new DeathShadowTCP(port, this); 
		server.block(); 
	}


	@Override
	protected String[] getRootPackages() {
		return new String[]{ "com.souchy.randd.deathshadows.nodes.pearl" };
	}

	
	
//	@Override
//	protected NettyServerBuilderEncoder initServer(NettyServerBuilderPort builder) {
//		return builder.port(10000).SSL(false);
//	}
//	
//	@Override
//	public void init() throws Exception {
//		super.init(); // init server 
//		var dir = new File("nodes/");
//		
//	}
//	
//	@Override
//	protected String[] getRootPackages() {
//		return new String[]{ "com.souchy.randd.deathshadows.nodes.pearl" };
//	}
		
	
	
	/** pas sur de vouloir créer ça pcq faut ajouter le nom du type manuellement, malgré que NodeRep.getType() t'oblige à renvoyer un bon type 
	 * par contre j'sais même pas à quoi ça pourrait servir, 
	 * sauf pour donner une étendue d'ID pour chaque type, ex les ID de nodes BlackMoonstone pourrait se trouver dans la range 30 000 - 39 999 */
	/*public static enum CoreType {
		Pearl, // node monitor
		GreenBeryl, // chat server
		BlackMoonstone, // fight server
		Opal, // api server
	}*/
	/** TODO Core doit extend ça et on doit (sûrement, pour pas avoir la liste des nodes serveurs dans le code client) 
	 * 	... avoir un CoreType différent pour côté client et côté serveur */
	public static interface NodeRepresentation extends Identifiable<Integer> {
		//public Integer getID(); // from Identifiable
		//public CoreType getType();
		public String getType();
	}
	
	List<NodeRepresentation> nodes; 
	
	
	/**
	 * Lorsque qu'un node est mis en marche, il envoie un message de création. <p>
	 * On lui renvoie alors un message pour lui assigner un ID.
	 * Puis on commence à monitorer le node.
	 */
	public void onNodeSelfIdentify() {
		BBMessage msg; // = NodeCreationMessage
		String type; //CoreType type; // msg.type;
		int id = 0; // next ID dans la range admise par le type
		// send new NodeIDMessage(id);
		// notifie certains autres nodes de la création de celui-ci, pour ceux qui sont int�ress�s et abonn�s au bon channel
		// commence à monitorer le node (send pingecho, recv heartbeat etc)
	}
	
	/**
	 * Lorsqu'un node demande une instance d'un node disponible
	 */
	public void onNodeAskAvailableNode() {
		BBMessage msg; // = 
		String type; //CoreType type; // msg.type;
		boolean force = false; // si on force la cr�ation d'un node du type demand� si aucun n'est dispo
		// va chercher un node qui a ce type et qui est disponible
		// renvoie la NodeRep du node en question // <strike>, ou une erreur si aucun n'est dispo.</strike>
		
	}
	
	/**
	 * Lorsqu'un node demande la création d'un autre node
	 */
	public void onNodeAskNodeCreate() {
		BBMessage msg; // = 
		String type; //CoreType type; // msg.type;
	}

	/**
	 * Lorsqu'un node demande le shutdown d'un node
	 */
	public void onNodeAskNodeShutdown() {
		BBMessage msg; // = 
		int id; // msg.id;
	}

	public void onNodeHeartBeat() {
		BBMessage msg; // = 
		String coreType;
		int nodeId;
	}
	
	
	
	
	
	public void getNextNodeID() {
		
	}

	
	
	
}
