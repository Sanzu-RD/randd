package texgraph.ui;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import imgui.extension.imnodes.ImNodes;
import imgui.extension.imnodes.ImNodesContext;

import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;

/**
 * 
 * 
 * @author Blank
 * @date 10 nov. 2021
 */
public class Graph extends Container {

    private static final ImNodesContext CONTEXT = new ImNodesContext();
    static {
        ImNodes.createContext();
    }
    
    private final GraphControls control = new GraphControls();
    public final Map<Integer, Node> nodes = new HashMap<>();
    public final Map<Integer, Link> links = new HashMap<>();
    
	public Graph() {
		super();
		this.title = "Node Graph";
		//this.windowCond = ImGuiCond.Always;
		//this.windowFlags = ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoMove; 
		//applyDefaults();
	}
	
	
	@Override
	public void renderContent(float delta) {

        // actual node editor
        ImNodes.editorContextSet(CONTEXT);
        ImNodes.beginNodeEditor();
        {

            // nodes
            for (Node node : nodes.values()) {
            	node.render(delta);
            }
            // links
            var it = links.entrySet().iterator();
            while(it.hasNext()) {
            	var entry = it.next();
            	if(entry.getValue().sourcePin == null || entry.getValue().targetPin == null) {
            		it.remove();
            	} else {
            		ImNodes.link(entry.getKey(), entry.getValue().sourcePin.id, entry.getValue().targetPin.id);
            	}
            }
            
        }
        final boolean isEditorHovered = ImNodes.isEditorHovered();
        ImNodes.endNodeEditor();

        control.controls(this, isEditorHovered);
        control.clickNodeContext(this);
        control.clickEditorContext(this);
	}

	
    public Pin findPin(final long pinId) {
    	Pin pin = null;
        for (Node node : nodes.values()) {
            if((pin = node.getPin(pinId)) != null)
            	return pin;
        }
        return null;
    }
    
	@Override
	protected void applyDefaults() {
		size[0] = 700;
		size[1] = 500;
		position[0] = (Gdx.graphics.getWidth() - size[0]) / 2;
		position[1] = (Gdx.graphics.getHeight() - size[1]) / 2;
	}

	@Override
	public void resizeScreen(int screenW, int screenH) {
		super.resizeScreen(screenW, screenH);
		for(var n : nodes.values())
			n.resizeScreen(screenW, screenH);
		//applyDefaults();
	}
	
}
