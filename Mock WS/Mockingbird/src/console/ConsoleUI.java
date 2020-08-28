package console;

import com.souchy.randd.commons.tealwaters.logging.Logging;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ConsoleUI {

	/*
	 * set the by typing "/prefix", example "/packet"
	 * reset with "//"
	 */
	public String channel;
	public ObservableList<String> messages = FXCollections.observableArrayList();
	
    @FXML 
    public ListView<String> history;
    @FXML 
    public TextField input;

    @FXML
    public void initialize() {
		history.setItems(messages);
		Logging.streams.add(l -> Platform.runLater(() -> messages.add(l.toString())));
    }
    
    @FXML
    void onKeyPress(KeyEvent e) {
    	if(e.getCode() == KeyCode.ENTER) {
    		String text = input.getText();
    		
    		var words = text.split(" ", 2);
    		
    		var prefix = words[0];
    		var data = "";
    		if(words.length > 1) data = words[1];
    		
			if(prefix.startsWith("/")) 
				channel = prefix.substring(1);
			if(channel != "") 
				prefix = channel;
    		
    		var consumer = Command.consumers.get(prefix);
    		if(consumer != null) {
        		var com = new Command();
        		com.prefix = prefix;
        		com.data = data;
        		messages.add(com.toString());
    			consumer.accept(com);
    		}
    		
    		input.clear();
    	}
    }

    
    
}
