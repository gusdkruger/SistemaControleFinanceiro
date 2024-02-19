package filtros;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public final class LimitaSomenteNumeros extends PlainDocument {
	
	public void insertString(int offs, String s, AttributeSet a) throws BadLocationException {
		if(s == null || s.matches("[^0-9,.+-]+")) {
			return;
		}
		super.insertString(offs, s, a);
	}
}