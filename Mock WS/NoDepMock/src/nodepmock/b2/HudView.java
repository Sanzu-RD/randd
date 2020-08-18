package nodepmock.b2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;

public class HudView extends AbstractLmlView {

	public HudView() {
		super(new Stage());
	}
	
	public HudView(Stage stage) {
		super(stage);
	}

	@Override
	public String getViewId() {
		return "main";
	}
	
	@Override
	public FileHandle getTemplateFile() {
		return Gdx.files.internal(getViewId() + ".lml");
	}
	
	public FileHandle getSkinFile() {
		return Gdx.files.internal(getViewId() + ".json");
	}
	
}
