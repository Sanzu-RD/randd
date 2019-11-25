package com.souchy.randd.deathshadows.nodes.moonstone.black;

//import com.souchy.randd.commons.core.CoreServer;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilderEncoder;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilderPort;
import com.souchy.randd.deathshadows.commons.core.CoreServer;
import com.google.common.eventbus.EventBus;

public class BlackMoonstone extends CoreServer {
	
	
	public static void main(String[] args) throws Exception {

		/*MultiMessageHandler handlers = new MultiMessageHandler();
		
		handlers.add(new FightActionHandler());
		
		handlers.handle(new FightAction());

		handlers.handle(new PassTurn());

		handlers.handle(new Update());*/

		
		launch(new BlackMoonstone());
	}

	@Override
	public void start() {
		System.out.println("Black Moonstone Server Start !");
	}
	
	/*public Object getServerSingleton() {
		return null;
	}*/
														// pas relié à cette ligne, mais oublie pas dajotuer une variable "source" ou session 
														// dans l'objet message pour savoir d'ou il provient 
														// ou sinon d'ajouter un argument source dans handler.handle(msg, source)
														// pour que quand on arrive à cet endroit, 
														// on sache c'est quel client qui veut faire l'action demand�e et � quel client on doit r�pondre
	/*
														/ pas mal sur qu'on devrait flagger les classes BBMessageHandlers et BBMessageFactories en tant que Disposable pour lib�rer toutes les instances
														/ il est alors possible de mettre les messages et handlers en plugin si on veut ou peu importe
														/ ce qui m'amène à penser qu'on devrait avoir plus de flexbilit� / contrôle sur ces managers de handlers et de factories
														
														/ Faut se rappeler qu'un core/node peut lui-même être constitué de plusieurs plugins
														/ Dans notre cas les classes Handler ont besoin de connaître les classes de Message (d�pendance direct pour obtenir le type g�n�ric et l'ID)
																
														/ Oublie donc pas d'ajouter un module/pluginmanager à l'interface Core et d'obliger son initialisation de quelconque mani�re dans BlackMoonstone
														/ Blackmoonstone peut implémenter une fonction retournant String[] pour identifier les directory ou aller chercher les plugins
														/ puisque tout comme les MessageDiscoverer et MessageHandlerDiscoverer, on utilise un PluginDiscoverer, donc m�me chose pour les chemins de recherche
														/ Pour les plugins on a déjà une interface EntryPoint, peut-être que Core devrait extend EntryPoint
														/ et on passerait alors la référence "this" (BlackMoonstone extends coreserver extends core extends entrypoint)
														/ aux plugins pour qu'ils puissent, entre autre, inscrire des Message, des MessageHandler, des genre dEventHandlers s�rement, etc
														
														/ todo D�fini plus de ressemblances entre ModuleManager et BBMessageHandlers et BBMessageFactories
														/ le module manager a son propre discoverer priv� et une fonction explore() pour l'utiliser, ce qui serait utile ici pour que les plugins disent aux managers dexplorer leurs packages
														/ il est aussi disposable, donc il a une fonction dispose(), ce qui est aussi utile dans le cas de plugins ou on veut enlever les objets venant des classes externes
														/ 
														
														/ Oublie pas aussi Modules.node qui dit que les cores (nodes) sont �galement des modules 
														/ Donc chaque core doit d�finir un fichier d'information
														
														/ Oublie pas de documenter toute cette architecture dans le document d'architecture
														/ donc expliquer en d�tail les syst�mes de modules/plugins/nodes/core (les projets, les packages, les classes, les inh�ritances, les fonctions, les variables...)
														/ Expliquer comment cr�er un nouveau core/node et l'utiliser (need entre autre un NodeInformation)
														/ Expliquer comment cr�er un nouveau plugin et l'utiliser    (need entre autre un BaseModuleInformation)
														/ Expliquer les EntryPoint et qu'est-ce qui est possible de faire � l'int�rieur d'un plugin
														/ Expliquer comment mettre en marche/shutdown un module, que ce soit un core ou un plugin
														/ Expliquer les Discoverer et diff�rents managers
														/ Expliquer la cr�ation de Messages et MessageHandlers et comment les register aupr�s d'un manager et les utiliser
														/ Expliquer les Session/BBSession -> �a d'ailleurs c'est seulement côté serveur.
														/ Expliquer comment envoyer/recevoir un message -> diff�rente mani�re c�t� serveur et c�t� client
														/ Expliquer les Identifiable/AnnotatedIdentifiable
														/ Expliquer les Serializer/Deserializer
														/ Expliquer les Factory
														/ Expliquer les Disposable
														/ Expliquer NettyServer, NettyHandler, BBMessageEncoder/Decoder
														/ Expliquer l'architecture client-serveur et le partage de classes entre les deux (Black vs White vs Commons Moonstone)
														
														/ Ne pas oublier de prendre son temps, faire le point, faire le m�nage, 
														/ et s'assurer qu'on suit un bon plan avec une bonne architecture et une bonne impl�mentation (ex certaines interfaces dans tealwaters.commons sont iffy)
										*/				
	/* -> Optionnel
	@Override
	protected NettyServerConfiguration initPipeline(NettyServerBuilderEncoder pipeBuilder) { 
		return pipeBuilder
				.encoder(() -> new BBMessageEncoder())
				.decoder(() -> new BBMessageDecoder(msgFactory))
				.handler(() -> new NettyHandler());
	}*/

	@Override
	protected NettyServerBuilderEncoder initServer(NettyServerBuilderPort conf) {  
		return conf.port(443).SSL(false);
	}

	@Override
	protected String[] getRootPackages() {
		return new String[]{ "com.souchy.randd.deathshadows.nodes.moonstone.black" };
	}

	@Override
	public EventBus getBus() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
