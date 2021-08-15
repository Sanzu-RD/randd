module CreatureCommons {
	exports com.souchy.randd.data.s1.main;
	exports com.souchy.randd.data.s1.creatures;
	exports com.souchy.randd.data.s1.creaturetypes;
	exports com.souchy.randd.data.s1.status;
	exports com.souchy.randd.data.s1.spells;

	exports com.souchy.randd.data.s1.spells.normal;
	exports com.souchy.randd.data.s1.spells.fire;
	exports com.souchy.randd.data.s1.spells.water;
	exports com.souchy.randd.data.s1.spells.earth;
	exports com.souchy.randd.data.s1.spells.air;
	
	exports com.souchy.randd.data.s1.spells.secondary.dark;
	exports com.souchy.randd.data.s1.spells.secondary.electric;
	exports com.souchy.randd.data.s1.spells.secondary.ghost;
	exports com.souchy.randd.data.s1.spells.secondary.ice;
	exports com.souchy.randd.data.s1.spells.secondary.light;
//	exports com.souchy.randd.data.s1.spells.nature;
//	exports com.souchy.randd.data.s1.spells.physical;
//	exports com.souchy.randd.data.s1.spells.poly;
//	exports com.souchy.randd.data.s1.spells.psychic;
//	exports com.souchy.randd.data.s1.spells.steel;
//	exports com.souchy.randd.data.s1.spells.toxin;
	
	exports com.souchy.randd.data.s1.creatures.flora;
	exports com.souchy.randd.data.s1.creatures.flora.spells;
//	exports com.souchy.randd.data.s1.creatures.flora.status;
	
	
	
	requires transitive GameMechanics2;
//	requires GameMechanics3;
	requires com.google.common;
	requires com.souchy.randd.AnnotationProcessor;
	requires netty.all;
	requires com.souchy.randd.commons.TealNet;
}