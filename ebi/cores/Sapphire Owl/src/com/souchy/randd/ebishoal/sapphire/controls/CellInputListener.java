package com.souchy.randd.ebishoal.sapphire.controls;

import com.google.common.eventbus.Subscribe;


public class CellInputListener {

//	private Map<CellInputEventType, Consumer<CellInputEvent>> consumers = new HashMap<>();
	

	public CellInputListener(){
	//	Universe.bus.register(this);
		
//		Arrays.asList(CellInputEventType.values()).stream().forEach(t -> {
//			try {
//				var method = this.getClass().getDeclaredMethod(t.name(), this.getClass());
//				consumers.put(t, cell -> {
//					try {
//						method.invoke(cell);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				});
//			} catch (Exception e) { 
//				e.printStackTrace(); 
//			}
//		});
	}

	@Subscribe
	public void listen(CellInputEvent event){
//		consumers.get(event.getType()).accept(event);
	}
	
	
	public void mouseEnter(CellInputEvent event) {
		
	}
	
	
	
}
