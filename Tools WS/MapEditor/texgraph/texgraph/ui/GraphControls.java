package texgraph.ui;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.ui.mapeditor.EditorScreen;

import imgui.extension.imnodes.ImNodes;
import imgui.flag.ImGuiMouseButton;
import imgui.internal.ImGui;
import imgui.type.ImInt;
import texgraph.Texgraph;
import texgraph.model.NodeModel;

public class GraphControls {

    private final ImInt LINK_A = new ImInt();
    private final ImInt LINK_B = new ImInt();
    
    
	void controls(Graph graph, boolean isEditorHovered) {
        if (ImNodes.isLinkCreated(LINK_A, LINK_B)) {
        	final Pin source = graph.findPin(LINK_A.get());
        	final Pin target = graph.findPin(LINK_B.get());
            if(source != null && target != null) {
                var link = new Link(source, target);
                graph.links.put(link.id, link);
                Log.info("GraphControl link");
            }
        }
        final int hoveredNode = ImNodes.getHoveredNode();
        if (ImGui.isMouseClicked(ImGuiMouseButton.Right)) {
        	//Log.info("right click");
            if (hoveredNode != -1) {
                ImGui.openPopup("node_context");
                ImGui.getStateStorage().setInt(ImGui.getID("delete_node_id"), hoveredNode);
            	MapEditorGame.screen.imgui.properties.setObj(hoveredNode);
            } else 
            if (isEditorHovered) {
                ImGui.openPopup("node_editor_context");
            }
        }
        if (ImGui.isMouseClicked(ImGuiMouseButton.Left)) {
            if (hoveredNode != -1) {
            	MapEditorGame.screen.imgui.properties.setObj(graph.nodes.get(hoveredNode));
            }
        }
	}
	

	void clickNodeContext(Graph graph) {
		if (ImGui.isPopupOpen("node_context")) {
            final int targetNode = ImGui.getStateStorage().getInt(ImGui.getID("delete_node_id"));
            if (ImGui.beginPopup("node_context")) {
                if (ImGui.button("Delete " + graph.nodes.get(targetNode).getName())) {
                    var node = graph.nodes.remove(targetNode);
                    if(node != null) node.dispose();
                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }
        }
	}
	
	void clickEditorContext(Graph graph) {
        if (ImGui.beginPopup("node_editor_context")) {
            int numLinks = ImNodes.numSelectedLinks();
            if(numLinks != 0) {
            	if(ImGui.button("Delete links")) {
		            int[] linkids = new int[numLinks];
		            ImNodes.getSelectedLinks(linkids);
		            for(int id : linkids) {
						Link link = graph.links.remove(id);
						if(link != null) link.dispose();
		            }
	                ImGui.closeCurrentPopup();
            	}
            }
            int numNodes = ImNodes.numSelectedNodes();
            if(numNodes != 0) {
            	if(ImGui.button("Delete nodes")) {
		            int[] nodeids = new int[numNodes];
		            ImNodes.getSelectedNodes(nodeids);
		            for(int id : nodeids) {
		            	Node node = graph.nodes.remove(id);
		            	if(node != null) node.dispose();
		            }
	                ImGui.closeCurrentPopup();
            	}
            }
            
            for(var e : Texgraph.models.entrySet()) {
            	if(ImGui.beginMenu(e.getKey())) {
                	for(var v : e.getValue()) {
                		if(ImGui.button(v.name)) {
                			var node = new Node();
                			try {
                				var model = v.getClass().getDeclaredConstructor().newInstance();
                				node.init(model);
                			} catch (Exception ex) {
                				ex.printStackTrace();
                			}
            		        graph.nodes.put(node.id, node);
            		        ImNodes.setNodeScreenSpacePos(node.id, ImGui.getMousePosX(), ImGui.getMousePosY());
            		        ImGui.closeCurrentPopup();
                		}
                	}
                	ImGui.endMenu();
            	}
            }

            ImGui.endPopup();
        }
	}
	
	
}
