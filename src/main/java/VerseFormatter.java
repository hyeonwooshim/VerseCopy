import java.io.IOException;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class VerseFormatter {

    private Bible bible;

    public VerseFormatter(Bible bible) {
        this.bible = bible;
    }

    public String getEngLines(int bookNum, int chap,
        int verse1, int verse2) {
        String engStr = "";
        for (int i = verse1 - 1; i < verse2; i++) {
            engStr += bible.getEngLineAt(bookNum, chap - 1, i) + " ";
        }
        return engStr.substring(0, engStr.length() - 1);
    }

    public String getKorLines(int bookNum, int chap,
        int verse1, int verse2) {
        String korStr = "";
        for (int i = verse1 - 1; i < verse2; i++) {
            korStr += bible.getKorLineAt(bookNum, chap - 1, i) + " ";
        }
        return korStr.substring(0, korStr.length() - 1);
    }

    public String getVerses(String book, int chap, int verse1, int verse2,
        boolean saidBefore) {
        int bookNum = bible.findBook(book);
        String korBookName = bible.LIST_KOR[bookNum];
        String engBookName = bible.LIST_ENG[bookNum];
        String korStr;
        String engStr;
        String korLines = getKorLines(bookNum, chap, verse1, verse2);
        String engLines = getEngLines(bookNum, chap, verse1, verse2);

        String korLocator = "(" + korBookName + " ";
        String engLocator = "(" + engBookName + " ";

        if (verse1 == verse2) {
            korLocator += chap + ":" + verse1 + ")";
            engLocator += chap + ":" + verse1 + ")";
        } else {
            korLocator += chap + ":" + verse1 + "-" + verse2 + ")";
            engLocator += chap + ":" + verse1 + "-" + verse2 + ")";
        }

        if (saidBefore) {
            korStr = korLocator + " " + korLines;
            engStr = engLocator + " " + engLines;
        } else {
            korStr = korLines + " " + korLocator;
            engStr = engLines + " " + engLocator;
        }

        return korStr + "\n" + engStr;
    }
	
	/** Methods for getting numbered lines (made for Microsoft Word) **/
	public String getNumberedEngLines(int bookNum, int chap,
        int verse1, int verse2) {
        String engStr = "";
        for (int i = verse1 - 1; i < verse2; i++) {
            engStr += bible.getEngLineAt(bookNum, chap - 1, i) + " ";
        }
        return engStr.substring(0, engStr.length() - 1);
    }

    public String getNumberedKorLines(int bookNum, int chap,
        int verse1, int verse2) {
        String korStr = "";
        for (int i = verse1 - 1; i < verse2; i++) {
            korStr += bible.getKorLineAt(bookNum, chap - 1, i) + " ";
        }
        return korStr.substring(0, korStr.length() - 1);
    }

    public String getNumberedVerses(String book, int chap, int verse1, int verse2) {
        int bookNum = bible.findBook(book);
        String korBookName = bible.LIST_KOR[bookNum];
        String engBookName = bible.LIST_ENG[bookNum];
		String str = "";
		
        String korLines = getKorLines(bookNum, chap, verse1, verse2);
        String engLines = getEngLines(bookNum, chap, verse1, verse2);


        if (verse1 == verse2) {
            str += "\"" + korBookName + " " + chap + ":" + verse1 + "\"";
            str += "\t\"" + engBookName + " " + chap + ":" + verse1 + "\"\n";
        } else {
            str += "\"" + korBookName + " " + chap + ":" + verse1 + "-" + verse2 + "\"";
            str += "\t\"" + engBookName + " " + chap + ":" + verse1 + "-" + verse2 + "\"\n";
        }
		
		
		
		
        return str;
    }
}
