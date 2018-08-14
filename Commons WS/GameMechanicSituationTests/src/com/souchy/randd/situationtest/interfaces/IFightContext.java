package com.souchy.randd.situationtest.interfaces;

public interface IFightContext {

	// (context props à la fin du texte)
	
	/**
	
	spellcontext / turncontext / ..
	
	
	spellcontext our turncontext pour garder en mémoire des valeurs qu'on a besoin d'utiliser depuis plusieurs effets
	
	ex un sort roule un dé de 1 à 6 et on applique un effet différent selon le jet de dé
	
	il faut donc garder le jet de dé qqpart en mémoire pour que tous les effets puissent savoir sa valeur
	
	on peut utiliser qqc comme des properties,
	ex le 1er effet du sort roule le dé puis set("spell:1:diceroll:1", value);
	puis après les autres effets peuvent get("spell:1:diceroll:1")
	puis t'as une fonction ou un effet qui clean() les vielles properties quand la actionpipeline fini 
	
	
	on poourrait ptete mettre ce contexte / ces properties dans la pipeline vu que sa durée de vie est la même 
		(cast un sort -> créé une pipline -> créé des props -> l'action fini -> delete la pipeline -> delete les props)
				
				
				
				
	OnStartTurn : send event :
		if there is a listener : create a pipeline, put the effect
		if there is 2 listener : 1st creates a pipeline, 1st puts the effect, 2nd puts the effect, then start pipeline
		
		
	*/
	
	
	/*
	 Explication ActionBuffer vs EffectPipeline 
	 
	 ActionBuffer c'est pour quand le client enchaîne plusieurs actions, ex cast un spell puis bouge puis cast un autre spell rapidement.
	 Le buffer prend toutes les actions et les process une par une, mais ça permet au client de les pré-envoyer (donc buffer les actions) 
	 	au lieu d'avoir à attendre que l'action précédente se termine pour pouvoir en faire une autre
	 
	 L'EffectPipeline ou ActionPipeline, il y en a une pour chaque action , j'sais pas pour le nom
	 
	 C'est pas *nécessaire* d'avoir un actionbuffer par contre
	 
	 */
				
	// Context prop s : 
	// Players (clients)
	// Teams (list d'entities)
	// ActionBuffer -> EffectPipelines/ActionPipeline 
	// TurnQueue
	// CellMap + riders
	// then you have references to all entities in the Teams, in TurnQueue and in CellMap riders
	// startTime of the fight pour savoir la durée du match en temps (currentmillis - starttime)
	// 
	
}
