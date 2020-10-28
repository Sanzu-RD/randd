package keys;

public class Keys {

	
	public CreaturesKeys creatures;
	public StatsKeys stats;
	public AffinitiesKeys affinities;
	
	
	public class CreaturesKeys {
		
	}

	public class StatsKeys {
		public String get(String c, String type, String ele) {
			return "creatures:" + c + ":stats:" + type + ":" + ele;
		}
		public String ressources(String c, String res) {
			return "creatures:" + c + ":stats:ressources:" + res;
		}
		public String affinities(String c, String ele) {
			return "creatures:" + c + ":stats:affinities:" + ele;
		}
		public String resistances(String c, String ele) {
			return "creatures:" + c + ":stats:resistances:" + ele;
		}
	}

	public class AffinitiesKeys {
		public String get(String c, String ele) {
			return "creatures:" + c + ":stats:affinities:" + ele;
		}
//		public String fire(String c) {
//			return "creatures:" + c + ":stats:affinities:fire";
//		}
//		public String water(String c) {
//			return "creatures:" + c + ":stats:affinities:fire";
//		}
//		public String ice(String c) {
//			return "creatures:" + c + ":stats:affinities:fire";
//		}
//		public String earth(String c) {
//			return "creatures:" + c + ":stats:affinities:fire";
//		}
	}
	public class RessourcesKeys {
		public String get(String c, String res) {
			return "creatures:" + c + ":stats:ressources:" + res;
		}
	}
	
	
	
	public class Key {
		public String key;
	}
	
	
	
	
	public class CKey {
		private SKey stats;
		public SKey get(int id) {
			
		}
	}
	public class SKey {
		
	}
	
	
	
	
	
	
}
