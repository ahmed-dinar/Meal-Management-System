package Custom;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * A JTextField that accepts only integers.
 *
 * @author David Buzatto
 */
public class IntegerField extends JTextField {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IntegerField() {
        super();
    }

    public IntegerField( int cols ) {
        super( cols );
    }

    @Override
    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }

    static class UpperCaseDocument extends PlainDocument {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public void insertString( int offs, String str, AttributeSet a )
                throws BadLocationException {

            if ( str == null ) {
                return;
            }
            boolean ok = true;
            try {
                 Float.parseFloat( str);
            } catch ( NumberFormatException exc ) {
                 ok = false;  
            }
            if ( ok )
                super.insertString( offs, str, a );
        }
    }
}