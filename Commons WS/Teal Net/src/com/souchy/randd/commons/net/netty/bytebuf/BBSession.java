package com.souchy.randd.commons.net.netty.bytebuf;

import com.souchy.randd.commons.net.Session;

/**
 * Should be only on server-side libs, Cut this off from client libs, there are no sessions there, malgr� que peut-�tre (.write() pour �crire au serveur si ca se trouve, si on a un contextchannel)
 * @author Souchy
 *
 */
public class BBSession implements Session<BBMessage> {

	@Override
	public Integer getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(BBMessage msg) {
		// TODO Auto-generated method stub

	}

}
