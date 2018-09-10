package com.ericshim.versecopier;

import com.ericshim.bible.Bible;

/**
 * Formatter used for VerseCopier to format the
 * verses in the desired form for JBCH Wednesday sermons.
 * 2016/08/10
 * @author Eric Shim
 * @version 1.1
 */
public class VerseFormatter {

  private Bible bible1;
  private Bible bible2;

  public VerseFormatter(Bible bible1, Bible bible2) {
    this.bible1 = bible1;
    this.bible2 = bible2;
  }

  public String getVerses(Bible bible, int book, int chapter, int verse1, int verse2) {
    return String.join(" ", bible.verses(book, chapter, verse1, verse2));
  }

  /**
   * Gets verses formatted as recited verses.
   *
   * @param bookIndex book index
   * @param chap chapter number
   * @param verse1 starting verse number
   * @param verse2 ending verse number
   * @param saidBefore whether the verse location was mentioned before recitation
   * @return String with verses formatted as recited verses.
   */
  public String formatVerses(int bookIndex, int chap, int verse1, int verse2, boolean saidBefore) {
    String bookName1 = bible1.getShortenedBookName(bookIndex);
    String bookName2 = bible2.getShortenedBookName(bookIndex);
    String korStr;
    String engStr;
    String verses1 = getVerses(bible1, bookIndex, chap, verse1, verse2);
    String verses2 = getVerses(bible2, bookIndex, chap, verse1, verse2);

    String korLocator = "(" + bookName1 + " ";
    String engLocator = "(" + bookName2 + " ";

    if (verse1 == verse2) {
      korLocator += chap + ":" + verse1 + ")";
      engLocator += chap + ":" + verse1 + ")";
    } else {
      korLocator += chap + ":" + verse1 + "-" + verse2 + ")";
      engLocator += chap + ":" + verse1 + "-" + verse2 + ")";
    }

    if (saidBefore) {
      korStr = korLocator + " " + verses1;
      engStr = engLocator + " " + verses2;
    } else {
      korStr = verses1 + " " + korLocator;
      engStr = verses2 + " " + engLocator;
    }

    return korStr + "\n" + engStr;
  }

  public String formatVerses(String book, int chap, int verse1, int verse2, boolean saidBefore) {
    int bookIndex = getBookIndex(book);
    return formatVerses(bookIndex, chap, verse1, verse2, saidBefore);
  }

  /**
   * Methods for getting numbered lines (made for Microsoft Word)
   **/

  public String getHtmlNumberedVerses(Bible bible,
      int bookIndex, int chapter, int verse1, int verse2) {
    int numVerses = verse2 - verse1 + 1;
    String[] verses = bible.verses(bookIndex, chapter, verse1, verse2);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < numVerses; i++) {
      sb.append(i + verse1).append(". ");
      sb.append(verses[i]).append("<br><br>");
    }
    return sb.toString();
  }

  /**
   * Gets verses formatted as read verses.
   *
   * @param bookIndex book index
   * @param chapter chapter number
   * @param verse1 starting verse number
   * @param verse2 ending verse number
   * @return HTML formatted 2-column table of the Korean verses in first column and the English
   * verses in the second column.
   */
  public String getHtmlNumberedVerses(int bookIndex, int chapter, int verse1, int verse2) {
    String bookName1 = bible1.getShortenedBookName(bookIndex);
    String bookName2 = bible2.getShortenedBookName(bookIndex);
    String str = "";

    String verses1 = getHtmlNumberedVerses(bible1, bookIndex, chapter, verse1, verse2);
    String verses2 = getHtmlNumberedVerses(bible2, bookIndex, chapter, verse1, verse2);

    if (verse1 == verse2) {
      str += "<pre><b>\"" + bookName1 + " " + chapter + ":" + verse1 + "\"";
      str += "&#9;\"" + bookName2 + " " + chapter + ":" + verse1 + "\"</b></pre><br>";
    } else {
      str += "<pre><b>\"" + bookName1 + " " + chapter + ":" + verse1 + "-" + verse2 + "\"";
      str += "&#9;\"" + bookName2 + " " + chapter + ":" + verse1 + "-" + verse2 + "\"</b></pre>";
    }

    str += "<table style=\""
        + "border:1px solid black; border-collapse:collapse;"
        + "padding-left:0.08in; padding-right:0.08in;"
        + "padding-top:0in; padding-bottom:0in;"
        + "\">"
        + "<tr>"
        + "<td style=\"vertical-align:top; border:1px solid black;\">"
        + verses1
        + "</td>"
        + "<td style=\"vertical-align:top; border:1px solid black;\">"
        + verses2
        + "</td>"
        + "</tr>"
        + "</table>";

    return str;
  }

  public String getHtmlNumberedVerses(String book, int chapter, int verse1, int verse2) {
    int bookIndex = getBookIndex(book);
    return getHtmlNumberedVerses(bookIndex, chapter, verse1, verse2);
  }

  public int getBookIndex(String book) {
    int bookIndex = bible1.getBookIndex(book);
    if (bookIndex < 0) bookIndex = bible2.getBookIndex(book);
    return bookIndex;
  }

  public boolean validChapter(int bookIndex, int chapter) {
    return bible1.hasChapter(bookIndex, chapter);
  }

  public boolean validVerse(int bookIndex, int chapter, int verse) {
    return bible1.hasVerse(bookIndex, chapter, verse);
  }
}
