package com.ericshim.bible;

public class Verse {
  private static final String INVALID_BN_PROCESSOR_MSG = "Processor can't be null!ss";
  private static final String INVALID_LINE_MSG = "Line doesn't fit format.";

  private String bookNameInLine;
  private int book;
  private int chapter;
  private int verse;
  private String text;

  public Verse(String line, BookNameProcessor processor) {
    if (processor == null) throw new IllegalArgumentException(INVALID_BN_PROCESSOR_MSG);
    processDefaultFormat(line, processor);
  }

  private void processDefaultFormat(String line, BookNameProcessor bnProcessor) {
    int firstSpace = line.indexOf(" ");
    String info = line.substring(0, firstSpace);
    text = line.substring(firstSpace + 1);

    String[] parts = info.replaceAll("[^0-9]+", " ").split(" ");
    String ch;
    String v;
    if (parts.length == 2) {
      ch = parts[0];
      v = parts[1];
    } else if (parts.length == 3) {
      ch = parts[1];
      v = parts[2];
    } else {
      throw new IllegalArgumentException(INVALID_LINE_MSG);
    }
    chapter = Integer.parseInt(ch);
    verse = Integer.parseInt(v);

    // What comes before the chapter number is the book name.
    bookNameInLine = info.substring(0, info.indexOf(ch, 1)).replaceAll("\\.", "");
    book = bnProcessor.getBookIndex(bookNameInLine);
  }

  public String getBookNameInLine() {
    return bookNameInLine;
  }

  public int getBook() {
    return book;
  }

  public int getChapter() {
    return chapter;
  }

  public int getVerse() {
    return verse;
  }

  public String getText() {
    return text;
  }

  public interface BookNameProcessor {
    int getBookIndex(String bookName);
  }
}
