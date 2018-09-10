package com.ericshim.versecopier.model;

public class CopiedInfo {
  private int bookIndex;
  private int chapter;
  private int verse;
  private int offset;
  private String fullText;

  public CopiedInfo(int bookIndex, int chapter, int verse, int offset) {
    this.bookIndex = bookIndex;
    this.chapter = chapter;
    this.verse = verse;
    this.offset = offset;
  }

  public int getBookIndex() {
    return bookIndex;
  }

  public int getChapter() {
    return chapter;
  }

  public int getVerse() {
    return verse;
  }

  public int getOffset() {
    return offset;
  }

  public String getFullText() {
    return fullText;
  }

  public void setFullText(String fullText) {
    this.fullText = fullText;
  }

  @Override
  public boolean equals(Object o) {
    // If the object is compared with itself then return true
    if (o == this) return true;
    if (!(o instanceof CopiedInfo)) return false;

    // typecast o to Complex so that we can compare data members
    CopiedInfo c = (CopiedInfo) o;

    return this.bookIndex == c.bookIndex &&
        this.chapter == c.chapter &&
        this.verse == c.verse &&
        this.offset == c.offset;
  }

  @Override
  public String toString() {
    if (offset > 0) {
      return String.format("%d %d:%d-%d", bookIndex, chapter, verse, verse + offset);
    } else {
      return String.format("%d %d:%d", bookIndex, chapter, verse);
    }
  }
}
