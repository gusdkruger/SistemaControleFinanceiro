package filtros;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public final class LimitaLength extends PlainDocument {
	 
	private final int limite;
	
	public LimitaLength(int limite) {
		this.limite = limite;
	}
	
	public void insertString(int offs, String s, AttributeSet a) throws BadLocationException {
		if(s == null) {
			return;
		}
		if((getLength() + s.length()) <= limite) {
			super.insertString(offs, s, a);
		}
	}
}