package com.souchy.randd.ebishoal.commons.lapis.lining;


import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


public class LineDrawing {

	public boolean flag = false;
	public Vector3 v;
	public Vector3 v2;
	public final Camera cam;
	
	public ShapeRenderer srender;
	public HashMap<Color, Array<Line>> lineMap;

	/*
	 * trucs de linerider
	public String tool = "line";
	public Color color = Color.BLUE;
	public Rectangle eraser;
	*/

	public LineDrawing(final Camera cam) {
		this.cam = cam;
		//eraser = new Rectangle();
		srender = new ShapeRenderer();
		lineMap = new HashMap<Color, Array<Line>>();
		lineMap.put(Color.BLUE, new Array<Line>());
		lineMap.put(Color.RED, new Array<Line>());
		lineMap.put(Color.GREEN, new Array<Line>());
	}
	

	public void addLine(Color color, Vector3 start, Vector3 end) {
		addLine(new Line(color, start, end));
	}
	public void addLine(Vector3 start, Vector3 end, Color color) {
		addLine(new Line(color, start, end));
	}
	public void addLine(Line line) {
		if(!lineMap.containsKey(line.color)) 
			lineMap.put(line.color, new Array<>());
		lineMap.get(line.color).add(line);
	}

	public void createCross(){
		createCross(3000);
	}
	public void createCross(int length){
		createCross(Color.RED, Color.GREEN, Color.BLUE, length);
	}
	public void createCross(Color crossXColor, Color crossYColor, Color crossZColor, int length){
		int fromX = 0, toX = length;
		int fromY = 0, toY = length;
		int fromZ = 0, toZ = length;
		// (0,0) crosss
		addLine(new Vector3(fromX, 0, 0), new Vector3(toX, 0, 0), crossXColor);
		addLine(new Vector3(0, fromY, 0), new Vector3(0, toY, 0), crossYColor);
		addLine(new Vector3(0, 0, fromZ), new Vector3(0, 0, toZ), crossZColor);
	}

	public void createGrid(){
		createGrid(1, 20, 20, true);
	}
	
	public void createGrid(int gap, int width, int height, boolean withCross) {
		createGrid(gap, width, height, Color.GRAY, withCross);
	}
	public void createGrid(int gap, int width, int height, Color color, boolean withCross) {
		// Draw a grid
		lineMap.put(color, new Array<>());
		//Color crossColor = Color.PURPLE;
		//lineMap.put(crossColor, new Array<>());
		
		int fromX = 0; // = -width;
		int toX = width;
		int fromY = 0; // = -height
		int toY = height;
		int zPlane = 1; // met la grid au même niveau que le dessus des blocs
		
		// vertical
		for (int x = withCross ? gap : fromX; x <= toX; x += gap) {
			addLine(color, new Vector3(x, fromY, zPlane), new Vector3(x, toY, zPlane));
		}
		// horizontal
		for (int y = withCross ? gap : fromY; y <= toY; y += gap) {
			addLine(color, new Vector3(fromX, y, zPlane), new Vector3(toX, y, zPlane));
		}
		if(withCross) createCross();
		// (0,0) cross
		//lineMap.get(crossColor).add(new Line(crossColor, new Vector3(fromX, 0, 0), new Vector3(toX, 0, 0), null));
		//lineMap.get(crossColor).add(new Line(crossColor, new Vector3(0, fromY, 0), new Vector3(0, toY, 0), null));
	}
	

	public void renderLines(){
		if(cam != null) srender.setProjectionMatrix(cam.combined);
		srender.begin(ShapeType.Line);
		for(Array<Line> lines : lineMap.values()){
			Iterator<Line> lini = lines.iterator();
			while (lini.hasNext()) {
				Line line = lini.next();
				//System.out.println("picked 1 line");
				if(cam == null || cam.frustum.boundsInFrustum(line.start, line.end)){
					srender.setColor(line.color);
					srender.line(line.start, line.end); // draw all the finished lines
					//System.out.println("rendered 1 line");
				}
			}
		}
		srender.end();
	}

	public void renderLinesExceptColor(Color colorIgnore){
		renderLinesExceptColor(colorIgnore, false);
	}

	public void renderLinesExceptColor(Color colorIgnore, boolean occlusion){
		if(cam != null) srender.setProjectionMatrix(cam.combined);
		srender.begin(ShapeType.Line);
		for(Array<Line> lines : lineMap.values()){
			if(lines.size == 0 || lines.get(0).color == colorIgnore) {
				continue;
			}
			for(Line line : lines) {
				//System.out.println("picked 1 line");
				if(cam == null || !occlusion || cam.frustum.boundsInFrustum(line.start, line.end)){
					srender.setColor(line.color);
					srender.line(line.start, line.end); // draw all the finished lines
					//System.out.println("rendered 1 line");
				}
			}
			/*Iterator<Line> lini = lines.iterator();
			while (lini.hasNext()) {
				Line line = lini.next();
			}*/
		}
		srender.end();
	}

	public void renderLinesOfColor(Color color){
		renderLinesOfColor(color, false);
	}

	
	public void renderLinesOfColor(Color color, boolean occlusion){
		if(cam != null) srender.setProjectionMatrix(cam.combined);
		srender.begin(ShapeType.Line);
		Array<Line> lines = lineMap.get(color);
		if(lines != null) {
			for(Line line : lines){
				if(cam == null || !occlusion || cam.frustum.boundsInFrustum(line.start, line.end)){
					srender.setColor(line.color);
					srender.line(line.start, line.end); // draw all the finished lines
				}
			}
		}
		srender.end();
	}


	/*public void draw() {
		if(tool == "line") line();
		if(tool == "pencil") pencil();
		if(tool == "fill") fill();
		if(tool == "eraser") erase();
	}
	
	private void line(){
		//	While pressed
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			if(!flag){
				flag = true;
				v = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			}
			v2 = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			srender.begin(ShapeType.Line);
			srender.line(v, v2); // draw temporary line
			srender.end();
		}
		//  On release
		if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			if(flag){
				flag = false;
				v2 = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
				lineMap.get(color).add(new Line(color, v, v2,  color == GREEN ? null : world));
			}
		}
	}
	
	private void pencil(){
		//	While pressed
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			if(!flag){
				flag = true;
				v = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			}
			v2 = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if(v.dst(v2) >= 1f){
				for(int i = 1; i > 0; i--) {
					lineMap.get(color).add(new Line(color, v, v2,  color == GREEN ? null : world));
				}
				v = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			}
			srender.begin(ShapeType.Line);
			srender.line(v, v2); // draw temporary line
			srender.end();
		}
		//  On release
		if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			if(flag) flag = false;
		}
	}
	
	private void erase(){
		// While pressed
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if (!flag) {
				flag = true;
				v = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			}
			v2 = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (v.dst(v2) >= 2) {
				v = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			}
			
			eraser.height = 5;
			eraser.width = 5;
			eraser.setLocation((int)v2.x - eraser.width/2, (int)v2.y - eraser.height/2);
			
			srender.begin(ShapeType.Filled);
			srender.setColor(Color.CORAL);
			srender.rect(v2.x - eraser.width/2, v2.y - eraser.height/2, 5, 5);
			srender.end();

			
			for(Array<Line> lines : lineMap.values()){
				Iterator<Line> iter = lines.iterator();
				while (iter.hasNext()) {
					Line line = iter.next();
					if(eraser.intersectsLine(line.start.x, line.start.y, line.end.x, line.end.y)){ 
						lines.removeValue(line, true);
						//if(color == Color.GREEN) glines.removeValue(line, true); if(color == Color.BLUE) blines.removeValue(line, true); if(color == Color.RED) rlines.removeValue(line, true);
						if(line.body != null) world.destroyBody(line.body);
						//System.out.println("removed line, count : " + lines.size);
					}
				}
			}
			
		}
		// On release
		if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if (flag) flag = false;
		}
	}

	private void fill(){
		
	}*/
	
	public Array<Line> get(Color color){
		return lineMap.get(color);
	}
}
