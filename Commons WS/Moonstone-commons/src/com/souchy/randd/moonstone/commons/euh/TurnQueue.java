package com.souchy.randd.moonstone.commons.euh;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TurnQueue {

	
	private List<?> queue = new ArrayList<>();
	
	private AtomicInteger index = new AtomicInteger(0);
	private AtomicInteger turn = new AtomicInteger(0);
	
	private ScheduledExecutorService e = Executors.newSingleThreadScheduledExecutor();
	private ScheduledFuture<?> timer;
	
	
	public void add() {
		
	}
	
	public void insert() {
		
	}
	
	public Object get() {
		return queue.get(index.get());
	}
	
	public int getCurrentIndex() {
		return index.get();
	}
	
	public int getCurrentTurn() {
		return turn.get();
	}
	
	public boolean tryPass(int index, int turn) {
		if(this.index.get() == index && this.turn.get() == turn) {
			pass();
			return true;
		}
		return false;
	}
	
	/**
	 * may be private
	 */
	private void pass() {
		final int previousIndex = index.getAndIncrement();
		//final int previousTurn = turn.getAndIncrement();

		
		timer.cancel(false);
		// send end turn packet pour stop le timer ? ou arr�te avec le timer qui est c�t� client
		
		// trigger event onEndTurn(previous/index/.get());   ->  trigger des effets ex. glyphe, buff/debuff
		
		if(index.get() == queue.size()){
			index.set(0);
			turn.getAndIncrement();
			// trigger event onFullTurn();   ->  trigger des effets ex. glyphe, buff/debuff
		}


		final int storedIndex = index.get();
		final int storedTurn = turn.get();
		
		timer = e.schedule(() -> {
			tryPass(storedIndex, storedTurn);
		}, 15, TimeUnit.SECONDS);
		
		// trigger event onStartTurn(previousindex/.get());   -> t rigger des effets ex. glyphe, buff/debuff
		
	}
	
	public TurnQueue() throws Exception {
		/*ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare("TurnQueue", true, false, false, null);
		*/
	}
	
	
	public void eventlistener_OnEndTurn() {
		
	}
	
	public void eventListener_OnStartTurn(){
		
	}
	
	
	
}
