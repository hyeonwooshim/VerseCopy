import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import java.io.*;

public final class ClipboardTransfer implements ClipboardOwner {
	//Creates the RTF string 
	private static final String RTF_STRING = "{\\rtf1\\ansi\\deff0\r\n{\\colortbl;\\red0\\green0\\blue0;\\red255\\green0\\blue0;}\r\nThis line is the default color\\line\r\n\\cf2\r\nThis line is red\\line\r\n\\cf1\r\nThis line is the default color\r\n}\r\n}";
	//Creates the plain text string
	private static final String PLAIN_STRING = "This line is the default color \n This line is red \n This line is the default color";
	//Array of data for specific flavor
	private static final Object data[] = {new ByteArrayInputStream(RTF_STRING.getBytes()),new ByteArrayInputStream(PLAIN_STRING.getBytes())};
	//Plain favor
	private static final DataFlavor PLAIN_FLAVOR = new DataFlavor("text/plain", "Plain Flavor");
	//RTF flavor
	private static final DataFlavor RTF_FLAVOR = new DataFlavor("text/rtf", "Rich Formatted Text");
	//Array of data flavors
	private static final DataFlavor flavors[] = {RTF_FLAVOR, PLAIN_FLAVOR};
	
   /**
   * Empty implementation of ClipboardOwner interface.
   */
    @Override
    public void lostOwnership(Clipboard aClipboard, Transferable aContents) {
        
    }

    /**
    * Make this class the owner of the Clipboard's contents and copy String.
    * @param str string that is to be placed
    */
    public void setClipboardContents(String str){
        StringSelection stringSelection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, this);
    }

    /**
    * Get the String residing on the clipboard.
    *
    * @return any text found on the Clipboard; if none found, return an
    * empty String.
    */
    public String getClipboardContents() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText = (contents != null)
            && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
          try {
            result = (String)contents.getTransferData(DataFlavor.stringFlavor);
          }
          catch (UnsupportedFlavorException | IOException ex){
            System.out.println(ex);
            ex.printStackTrace();
          }
        }
        return result;
    }
}
