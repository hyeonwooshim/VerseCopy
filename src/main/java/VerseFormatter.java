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
	public String getHtmlNumberedEngLines(int bookNum, int chap,
        int verse1, int verse2) {
        String engStr = "";
        for (int i = verse1 - 1; i < verse2; i++) {
            engStr += (i + 1) + ". "
				+ bible.getEngLineAt(bookNum, chap - 1, i) + "<br><br>";
        }
        return engStr;
    }

    public String getHtmlNumberedKorLines(int bookNum, int chap,
        int verse1, int verse2) {
        String korStr = "";
        for (int i = verse1 - 1; i < verse2; i++) {
            korStr += (i + 1)
				+ ". " + bible.getKorLineAt(bookNum, chap - 1, i) + "<br><br>";
        }
        return korStr;
    }

	/**
	 *
	 * @return HTML formatted 2-column table of the Korean verses in first
	 * 			column and the English verses in the second column.
	 */
    public String getHtmlNumberedVerses(String book, int chap, int verse1, int verse2) {
        int bookNum = bible.findBook(book);
        String korBookName = bible.LIST_KOR[bookNum];
        String engBookName = bible.LIST_ENG[bookNum];
		String str = "";
		
        String korLines = getHtmlNumberedKorLines(bookNum, chap, verse1, verse2);
        String engLines = getHtmlNumberedEngLines(bookNum, chap, verse1, verse2);
		
		if (verse1 == verse2) {
            str += "<pre><b>\"" + korBookName + " " + chap + ":" + verse1 + "\"";
            str += "&#9;\"" + engBookName + " " + chap + ":" + verse1 + "\"</b></pre><br>";
        } else {
            str += "<pre><b>\"" + korBookName + " " + chap + ":" + verse1 + "-" + verse2 + "\"";
            str += "&#9;\"" + engBookName + " " + chap + ":" + verse1 + "-" + verse2 + "\"</b></pre>";
        }
		
		str += "<table style=\"border:1px solid black; border-collapse:collapse;\">";
		str += "<tr>";
		str += "<td style=\"vertical-align:top; border:1px solid black;\">";
		str += korLines;
		str += "</td>";
		str += "<td style=\"vertical-align:top; border:1px solid black;\">";
		str += engLines;
		str += "</td>";
		str += "</tr>";
		str += "</table>";
		
        return str;
    }
}
