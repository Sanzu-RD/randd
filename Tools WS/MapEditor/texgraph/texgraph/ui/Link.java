package texgraph.ui;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author Blank
 * @date 10 nov. 2021
 */
public class Link {
	
    public static AtomicInteger nextLinkId = new AtomicInteger(1);
	public final int id = nextLinkId.getAndIncrement();
	
	public Pin sourcePin;
	public Pin targetPin;

	public Link(Pin out, Pin in) {
		this.sourcePin = out;
		this.targetPin = in;
		
		out.link = this;
		in.link = this;
	}
	
	public void dispose() {
		sourcePin.link = null;
		targetPin.link = null;
		sourcePin = null;
		targetPin = null;
	}
	
}
