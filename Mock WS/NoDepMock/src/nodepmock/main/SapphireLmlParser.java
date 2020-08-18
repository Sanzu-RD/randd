package nodepmock.main;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.github.czyzby.lml.parser.LmlData;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.DefaultLmlParser;
import com.github.czyzby.lml.parser.impl.tag.actor.TableLmlTag;
import com.github.czyzby.lml.parser.impl.tag.actor.provider.TableLmlTagProvider;
import com.github.czyzby.lml.parser.tag.LmlActorBuilder;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.github.czyzby.lml.parser.tag.LmlTagProvider;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class SapphireLmlParser extends DefaultLmlParser2 {
	
	public static SapphireComponent currentView = null;

	public SapphireLmlParser(LmlData data) {
		super(data);
	}
	
	@Override
	public <View> Array<Actor> createView(View view, FileHandle lmlTemplateFile) {
		if(view instanceof Actor) currentView = (SapphireComponent) view;
		var result = super.createView(view, lmlTemplateFile);
		currentView = null;
		return result;
	}
	

    /** @param rawTagData unprocessed data of the currently closed tag.
     * @param tagNameEndIndex index in rawTagData at which tag name ends. */
/*	@Override
    protected void processClosedTag(final StringBuilder rawTagData, final int tagNameEndIndex) {
        // Starting with 1 char to strip '/' marker:
        final String closedTagName = rawTagData.substring(1, tagNameEndIndex).trim();
        if (currentParentTag == null) {
            throwErrorIfStrict("There were no open tags, and yet: \"" + closedTagName + "\" is a closed parental tag.");
            return;
        } else if (!currentParentTag.getTagName().equals(closedTagName)) {
            if (strict || !strict && !currentParentTag.getTagName().equalsIgnoreCase(closedTagName)) {
                throwError("Tag: \"" + closedTagName + "\" was closed, but: \"" + currentParentTag.getTagName()
                        + "\" was expected.");
            }
        }
        currentParentTag.closeTag();
        final LmlTag grandParent = currentParentTag.getParent();
        if (grandParent == null) { // Tag was a root.
            if (currentParentTag.getActor() != null) {
            	Log.info("grand parent " + rawTagData);
//            	if(currentView != null)
//            		actors.add(currentView);
//            	else
            		actors.add(currentParentTag.getActor());
            }
        } else { // Tag had a parent.
            grandParent.handleChild(currentParentTag);
        }
        mapActorById(currentParentTag.getActor());
        currentParentTag = grandParent;

//		Log.info("parser tag " + tag.getAttribute("id"));
    }
*/

    /** @param tagName name of the tag to be parsed.
     * @param rawTagData raw data of a regular widget tag. */
/* 	@Override
   protected void processRegularTag(final String tagName, final StringBuilder rawTagData) {
        final LmlTagProvider tagProvider = syntax.getTagProvider(tagName);
        if (tagProvider == null) {
            throwError("No tag parser found for name: " + tagName);
        }
        final LmlTag tag = tagProvider.create(this, currentParentTag, rawTagData);
        if (tag.isParent()) {
//			Log.info("parser tag parent " + tag.getAttribute("id"));
            currentParentTag = tag;
        } else {
            // The tag is a child, so we're closing it immediately.
            tag.closeTag();
//			Log.info("parser tag close " + tag.getAttribute("id"));
            if (currentParentTag != null) {
                // Tag is child - adding to current parent:
                currentParentTag.handleChild(tag);
            } else {
                // Tag is a root - adding to the result:
                if (tag.getActor() != null) {
					Log.info("parser root " + currentView);
					if(currentView != null) {
						actors.add(currentView);
					} else {
						actors.add(tag.getActor());
                	}
                }
            }
            mapActorById(tag.getActor());
        }
    }
*/
	
	
	
	
}

