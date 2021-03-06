package com.souchy.randd.commons.diamond.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import io.netty.buffer.ByteBuf;

public class AoeBuilders {
	
	@FunctionalInterface
	public static interface AoeBuilder extends BBSerializer {
		public Aoe build(int a);

		@Override
		default ByteBuf serialize(ByteBuf out) {
			try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream)) {
				
				outputStream.writeObject(this);
				var bytes = byteArrayOutputStream.toByteArray();
				
				out.writeBoolean(true);
				out.writeBytes(bytes);
			} catch (Exception e) {
				out.writeBoolean(false);
			}
			return out;
		}
		
		
		public static AoeBuilder deserialize(ByteBuf in) {
			var success = in.readBoolean();
			if(!success) return null;
			byte[] bytes = new byte[in.readableBytes()];
			in.readBytes(bytes);
			
//			byte[] value = p.getBinaryValue();
	        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
	             ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream)) {
	        	
	        	
//	            return (SerializableRunnable) inputStream.readObject();
	            return (AoeBuilder) inputStream.readObject();
	        } catch (Exception e) {
	        	
	        }
	        return null;
		}
	}
	@FunctionalInterface
	public static interface AoeBuilder2 {
		public Aoe build(int a, int b);
	}
	
	
//	private static Aoe singleaoe = new Aoe(1, 1).fill(); // can't do this because we're able to modify Aoes directly in SpellStats
	public static Supplier<Aoe> single = () -> new Aoe(1, 1).fill();
	public static Function<Integer, Aoe> lineH = (length) -> new Aoe(length, 1).fill();
	public static Function<Integer, Aoe> lineV = (length) -> new Aoe(1, length).fill();
	
	public static Function<Integer, Aoe> cross = (radius) -> {
		var aoe = new Aoe(radius * 2 + 1, radius * 2 + 1);
		aoe.table.setIf((i, j) -> Pathfinding.checkAlign(radius, radius, i, j));
		return aoe;
	};
	public static Function<Integer, Aoe> crossDiag = (radius) -> {
		var aoe = new Aoe(radius * 2 + 1, radius * 2 + 1);
		aoe.table.setIf((i, j) -> Pathfinding.checkDiagonal(radius, radius, i, j));
		return aoe;
	};
	
	public static BiFunction<Integer, Integer, Aoe> rectangle = (width, height) -> new Aoe(width, height).fill();
	
	// je le laisse pour l'instant, mais pas besoin de ??a. Il vaut mieux faire aoe =
	// rect(3,3).sub(rect(2,2);
	public static BiFunction<Integer, Integer, Aoe> rectEmpty = (width, height) -> {
		var aoe = new Aoe(width, height);
		for (int i = 0; i < width; i++) {
			aoe.table.put(i, 0, true);
			aoe.table.put(i, height - 1, true);
		}
		for (int j = 0; j < height; j++) {
			aoe.table.put(0, j, true);
			aoe.table.put(width - 1, j, true);
		}
		return aoe;
	};
	/** circle = diamond */
	public static Function<Integer, Aoe> circle = (radius) -> {
		radius++;
		int w = radius * 2; // - 1;
		var aoe = new Aoe(w - 1, w - 1);
		for (int i = -radius; i < radius; i++) {
			for (int j = -radius; j < radius; j++) {
				if(Math.abs(i) + Math.abs(j) <= radius - 1) {
					aoe.table.put(i + radius - 1, j + radius - 1, true);
				}
			}
		}
		return aoe;
	};
	

	/** cone = half a circle = half a diamond */
	public static Function<Integer, Aoe> cone = (radius) -> {
		radius++;
		int w = radius * 2; // - 1;
		var aoe = new Aoe(w - 1, w - 1);
		for (int i = -radius; i < 1; i++) {
			for (int j = -radius; j < radius; j++) {
				if(Math.abs(i) + Math.abs(j) <= radius - 1) {
					aoe.table.put(i + radius - 1, j + radius - 1, true);
				}
			}
		}
		return aoe;
	};
	
	
	/** une moiti?? de cercle */
	// public static Function<Integer, Aoe> cone = (radius) -> {
	//
	// };
	
	// je le laisse pour l'instant, mais pas besoin de ??a. Il vaut mieux faire aoe =
	// circle(3,3).sub(circle(2,2);
//	public static Function<Integer, Aoe> circleEmpty = (radius) -> {
//		radius++;
//		int w = radius * 2; // - 1;
//		var aoe = new Aoe(w, w);
//		for (int i = -radius; i < radius; i++) {
//			for (int j = -radius; j < radius; j++) {
//				if(Math.abs(i) + Math.abs(j) == radius - 1) {
//					aoe.table.put(i + radius - 1, j + radius - 1, true);
//				}
//			}
//		}
//		return aoe;
//	};
	
	public static Function<Integer, Aoe> diag1 = (length) -> {
		var aoe = new Aoe(length, length);
		for (int i = 0; i < length; i++) {
			aoe.table.put(i, length - i - 1, true);
		}
		return aoe;
	};
	
	public static Function<Integer, Aoe> diag2 = (length) -> {
		var aoe = new Aoe(length, length);
		for (int i = 0; i < length; i++) {
			aoe.table.put(i, i, true);
		}
		return aoe;
	};
	
	
}