package keys;

public class KeyBuilder {

	public KeyBuilder from(String path) {
		return this;
	}
	
	
	public class CreaturesKeyBuilder {
		public CreatureKeyBuilder get(int id) {
			
		}
	}
	public class CreatureKeyBuilder {
		public StatBuilder stats() {
			return statbuilder.from("");
		}
	}
	public class StatBuilder {
		
	}
	public class IntStatKeyBuilder {
		
	}
	
	
	
	
	public class IntKey {
		
	}
	public class StringKey {
		
	}
	
	// Universe.creatures.get(1).stats().affinities().fire().base();
	
	public class Universe {
		public CreaturesKeyBuilder creatures = new CreaturesKeyBuilder();
	}
	
	
	
	
}
