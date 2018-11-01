package com.souchy.randd.tools.mapeditor.ui.components;

import com.kotcrab.vis.ui.widget.VisTextField;

public class NumberEntryField extends VisTextField {
	
	public NumberEntryField() {
		this("");
	}
	
	public NumberEntryField(String string) {
		super(string);
		this.setTextFieldFilter(new DigitFilter());
	}
	
	public static class DigitFilter implements TextFieldFilter {
		private char[] accepted;
		
		public DigitFilter() {
			accepted = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' };
		}
		
		@Override
		public boolean acceptChar(VisTextField textField, char c) {
			for (char a : accepted)
				if (a == c)
					return true;
			return false;
		}
	}
}
