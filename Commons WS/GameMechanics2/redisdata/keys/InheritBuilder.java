package keys;


public class InheritBuilder {
	
	
	public static void main() {
		Universe.creatures().creature(1).stats().affinities().affinity(1).base();
	}
	
	
	public static class Universe {
		public static CreaturesKey creatures() {
			return new MegaKey();
		}
	}
	
	
	public static class Key {
		protected String key;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public void append(String key) {
			this.key += ":" + key;
		}
		
	}
	
	public interface IntStatKey {
		public String base();
	}

	public interface CreaturesKey {
		public Creaturekey creature(int id);
	}

	public interface Creaturekey {
		public StatsKey stats();
		public SpellsKey spells();
		public StatusesKey statuses();
	}

	public static interface StatsKey {
		public AffinitiesKey affinities();
	}

	public static interface AffinitiesKey {
		public IntStatKey affinity(int id);
	}

	public static interface RessourcesKey {
		public IntStatKey ressource(int id);
	}

	public static interface ResistanceKey {
		public IntStatKey resistance(int id);
	}

	public interface SpellsKey extends SpellKey {
		public SpellKey spell(int id);
	}
	
	public interface StatusesKey {
		
	}
	
	public interface SpellKey {
		
	}
	
	
	public static class MegaKey extends Key implements CreaturesKey, Creaturekey, StatsKey, SpellsKey, StatusesKey, AffinitiesKey, RessourcesKey, ResistanceKey, IntStatKey  {
		@Override
		public Creaturekey creature(int id) {
			append(Integer.toString(id));
			return this;
		}
		@Override
		public StatsKey stats() {
			append("stats");
			return this;
		}
		@Override
		public SpellsKey spells() {
			append("spells");
			return this;
		}
		@Override
		public StatusesKey statuses() {
			append("statuses");
			return this;
		}
		@Override
		public AffinitiesKey affinities() {
			append("affinities");
			return this;
		}
		@Override
		public IntStatKey affinity(int id) {
			append(Integer.toString(id));
			return this;
		}
		@Override
		public IntStatKey ressource(int id) {
			append(Integer.toString(id));
			return this;
		}
		@Override
		public SpellKey spell(int id) {
			append(Integer.toString(id));
			return this;
		}
		@Override
		public IntStatKey resistance(int id) {
			append(Integer.toString(id));
			return this;
		}
		@Override
		public String base() {
			append("base");
			return key;
		}
		
	}
	
	
}
