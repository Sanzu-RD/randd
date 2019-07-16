package com.souchy.randd.jade.oldcombat;

public interface FightContext {

	// (context props � la fin du texte)
	
	/**
	
	spellcontext / turncontext / ..
	
	
	spellcontext our turncontext pour garder en m�moire des valeurs qu'on a besoin d'utiliser depuis plusieurs effets
	
	ex un sort roule un d� de 1 � 6 et on applique un effet diff�rent selon le jet de d�
	
	il faut donc garder le jet de d� qqpart en m�moire pour que tous les effets puissent savoir sa valeur
	
	on peut utiliser qqc comme des properties,
	ex le 1er effet du sort roule le d� puis set("spell:1:diceroll:1", value);
	puis apr�s les autres effets peuvent get("spell:1:diceroll:1")
	puis t'as une fonction ou un effet qui clean() les vielles properties quand la actionpipeline fini 
	
	
	on poourrait ptete mettre ce contexte / ces properties dans la pipeline vu que sa dur�e de vie est la m�me 
		(cast un sort -> cr�� une pipline -> cr�� des props -> l'action fini -> delete la pipeline -> delete les props)
				
				
				
				
	OnStartTurn : send event :
		if there is a listener : create a pipeline, put the effect
		if there is 2 listener : 1st creates a pipeline, 1st puts the effect, 2nd puts the effect, then start pipeline
		
		
	*/
	
	
	/*
	 Explication ActionBuffer vs EffectPipeline 
	 
	 ActionBuffer c'est pour quand le client encha�ne plusieurs actions, ex cast un spell puis bouge puis cast un autre spell rapidement.
	 Le buffer prend toutes les actions et les process une par une, mais �a permet au client de les pr�-envoyer (donc buffer les actions) 
	 	au lieu d'avoir � attendre que l'action pr�c�dente se termine pour pouvoir en faire une autre
	 
	 L'EffectPipeline ou ActionPipeline, il y en a une pour chaque action , j'sais pas pour le nom
	 
	 C'est pas *n�cessaire* d'avoir un actionbuffer par contre
	 
	 */
				
	// Context prop s : 
	// Players (clients)
	// Teams (list d'entities)
	// ActionBuffer -> EffectPipelines/ActionPipeline 
	// TurnQueue
	// CellMap + riders
	// then you have references to all entities in the Teams, in TurnQueue and in CellMap riders
	// startTime of the fight pour savoir la dur�e du match en temps (currentmillis - starttime)
	// 
	
}
