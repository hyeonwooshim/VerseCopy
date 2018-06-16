/**
 * Formatter used for VerseCopier to format the
 * verses in the desired form for JBCH Wednesday sermons.
 * 2016/08/10
 * @author Eric Shim
 * @version 1.1
 */
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

  /**
   * Gets verses formatted as recited verses.
   *
   * @param book		book name
   * @param chap		chapter number
   * @param verse1	starting verse number
   * @param verse2	ending verse number
   * @param saidBefore	whether the verse location was mentioned before recitation
   * @return String with verses formatted as recited verses.
   */
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
   * Gets verses formatted as read verses.
   *
   * @param book		book name
   * @param chap		chapter number
   * @param verse1	starting verse number
   * @param verse2	ending verse number
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

    str += "<table style=\""
        + "border:1px solid black; border-collapse:collapse;"
        + "padding-left:0.08in; padding-right:0.08in;"
        + "padding-top:0in; padding-bottom:0in;"
        + "\">"
        + "<tr>"
        + "<td style=\"vertical-align:top; border:1px solid black;\">"
        + korLines
        + "</td>"
        + "<td style=\"vertical-align:top; border:1px solid black;\">"
        + engLines
        + "</td>"
        + "</tr>"
        + "</table>";

    return str;
  }
}
