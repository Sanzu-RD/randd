package com.souchy.randd.tools.mapeditor.io;

public class MapJson {
	
	/*
	 * actual runtiem class structure
	 * 
	 *  model : {
	 *  	
	 *  
	 *  
	 *  }
	 * 
	 */
	
	
	
	// also models (nodes/parts?) should have custom additional shaders
	// and materials too
	
	// so when you load a model .fbx in a certain folder, it also loads adjacent shaders with the same name
	// same with materials
	
	/*
	 * 
	 * {
	 * 
	 * 	materials : [
	 *  	{
	 *  		id: "",
	 *  		[
	 *  			diffuse (color attribute),
	 *  			specular (color attribute),
	 *  			diffuse (color attribute),
	 *  			blending attribute
	 *  			texture attribute,
	 *  		]
	 *  	},
	 *  ]
	 * 
	 * 
	 *  instances: {
	 * 		models: [
	 * 			{
	 * 				id: "",
	 * 				file: "",
	 * 				basetransform: []
	 * 				partsMaterials:[ matId, matId... ]
	 * 			}
	 *  	],
	 * 		instances: [
	 * 			{
	 * 				modelid: 0
	 * 				transform: [],
	 * 			}
	 * 		]
	 *  }
	 * 
	 * 	voxels: {
	 * 		models: [
	 * 			{
	 * 				materials: [] // for each face (6)
	 * 			}
	 * 		]
	 * 		voxels: [[], [], []...]
	 * 
	 * 	}
	 * 
	 * 	cellTypes: [[], []...]
	 * 
	 * 
	 * }
	 * 
	 * 
	 */
	
}
