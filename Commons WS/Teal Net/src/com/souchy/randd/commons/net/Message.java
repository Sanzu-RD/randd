package com.souchy.randd.commons.net;

import com.souchy.randd.commons.tealwaters.commons.AnnotatedIdentifiable;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.commons.tealwaters.commons.Factory;
import com.souchy.randd.commons.tealwaters.commons.Serializer;

/**
 * 
 * Un Message est à la fois un Message, un Serializer, un Deserializer et une
 * Factory.
 * <p>
 * On se sert de la Factory pour créer un Deserializer qui retourne le bon
 * Message.
 * <p>
 * Ce qui veut dire :
 * 
 * <pre>{@literal 
 * 	Factory<Deserializer<ByteBuf, BBMessage>> f = new DummyMessage();
 * 	Deserializer<ByteBuf, BBMessage> d = new DummyMessage() = f.create();
 * 	Message out = f.create().deserialize() = d.deserialize();
 * }
 * </pre>
 * 
 * Ainsi on peut créer une liste de Factories qui créent des Deserializer qui
 * retourne le bon Message, ce qui restreint l'accès aux autres methods.
 * <p>
 * Pour créer un Serializer, toujours passer par le constructeurr du Message pour
 * initialiser toutes ses variables avant de le serializer.
 * 
 * @author Souchy
 *
 * @param <F>
 *            - Format de Message à (dé)sérializer. Ex: StringBuffer, ByteBuf
 * @param <M>
 *            - Type de Message pour que le Deserializer renvoie le bon type de
 *            Message. Ex: BBMessage
 *            <p>
 * 			<s>@param <D> - Type de Deserializer pour que la Factory créé le
 *            bon type de Deserializer (en fait créé un Message, qui extends
 *            Deserializer). </s>
 *            <p>
 * 			Dans tous les cas, ce sera toujours Deserializer<F, M>, c'est pas
 *            comme si on avait d'autres sortes de deserializer comme
 *            HelloDeserializer&lt;F, M&gt;
 */
public interface Message<F, 
						 M extends Message<F, M>//, D>, 
						 //D extends Deserializer<F, M> 
						> 
				extends AnnotatedIdentifiable,
				Serializer<F, F>, Deserializer<F, M>, Factory<Deserializer<F, M>> { // Responsibility<T, Message<T>>,

	// public T serialize(T out);
	// public Message<T> deserialize(T in);
	// default Message<F, M> handle(F in){ return create().deserialize(in); }

	/**
	 * @return D - new this(); 
	 */
//	@Override
//	public Deserializer<F, M> create();

}
