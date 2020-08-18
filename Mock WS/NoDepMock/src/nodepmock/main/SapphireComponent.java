package nodepmock.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.czyzby.lml.parser.action.ActionContainer;

public abstract class SapphireComponent extends Table implements ActionContainer {

	public abstract String getTemplateId();
	protected abstract void init();
	
	public SapphireComponent() {
//		super(SapphireHud.skin);
	}
	
	public FileHandle getTemplateFile() {
		return Gdx.files.internal("res/ux/sapphire/components/" + getTemplateId() + ".lml");
	}
	
	public FileHandle getStyleFile() {
		return Gdx.files.internal("res/ux/sapphire/components/" + getTemplateId() + ".json");
	}
	
	
	
	
}
