package com.souchy.randd.commons.ebishoal;


public abstract class CoreClient extends EbiCore {
	
	

	/**
	 * Netty server
	 */
//	protected NettyClient serv;

	/**
	 * Initialize the server core
	 * @throws Exception
	 */
	@Override
	public void init() throws Exception {
		// Init a NettyServer Builder 
//		NettyClientBuilderEncoder pipeBuilder = initServer(NettyServer.create());
		// Init the server pipeline 
//		NettyClientConfiguration conf = initPipeline(pipeBuilder);
		// Build the server
//		serv = conf.adapt().wrap();
	}
	
	/**
	 * Must be implemented by the Core instance to configure its NettyServer via the NettyServerBuilder
	 * <br>
	 * Serves to set the IP, port and SSL settings
	 * 
	 * @param builder - A NettyServerBuilder at the port step
	 * 
	 * @return The same builder a couple steps forward
	 */
//	protected abstract NettyClientBuilderEncoder initClient(NettyClientBuilderPort builder);
	
	/**
	 * Self-explanatory
	 * @param pipeBuilder - NettyServerBuilder at the pipeline encoder step
	 * @return A completed NettyServerBuilder configuration
	 */
/*	protected NettyClientConfiguration initPipeline(NettyClientBuilderEncoder pipeBuilder) { //ChannelPipeline pipe);
		return pipeBuilder
				.encoder(() -> new BBMessageEncoder())
				.decoder(() -> new BBMessageDecoder(msgFactories))
				.handler(() -> new NettyHandler());
	}
*/
	

}
