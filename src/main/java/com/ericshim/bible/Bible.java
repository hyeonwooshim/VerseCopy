package com.ericshim.bible;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This abstract class by default reads in a text file of a version of the bible,
 * and stores it as 3D String arrays with each dimension representing
 * the book, chapter, and verse, respectively.
 *
 * @author Eric Shim
 */
public abstract class Bible {
  private String[][][] indexedBible = null;

  /**
   * Constructor in the case for text files with each line being a verse.
   * The lines must have a very specific format.
   *
   * @param bibleReader BufferedReader that has the verses at each line
   */
  public Bible(BufferedReader bibleReader) throws IOException {
    if (bibleReader == null) return;
    indexedBible = readBible(bibleReader);
  }

  /**
   * @return English name of the language that this Bible is in
   */
  public abstract String getLanguage();

  /**
   * @return Array of full book names in this Bible
   */
  public abstract String[] getBookNames();

  /**
   * @return array of all the shorter versions the books' names
   */
  public String[] getShortenedBookNames() {
    return getBookNames();
  }

  /**
   * @param book index of the book
   * @return full name of the book
   */
  public String getBookName(int book) {
    return getBookNames()[book];
  }

  /**
   * @param book index of the book
   * @return shorter name of the book
   */
  public String getShortenedBookName(int book) {
    return getShortenedBookNames()[book];
  }

  public String[][][] getFullIndexedBible() {
    return indexedBible;
  }

  /**
   * Returns book number given a string of the name or search string for the name.
   * Returns a -1 if book is not found. Default behavior is to check for lower-case
   * substrings.
   *
   * @param book name of the book or part of it
   * @return the index of the book (0~65), -1 if not found
   */
  public int getBookIndex(String book) {
    if (book.isEmpty()) return -1;

    String[] bookNames = getBookNames();

    String lower = book.toLowerCase();
    for(int i = 0; i < bookNames.length; i++){
      String bookName = bookNames[i].toLowerCase();
      if (bookName.contains(lower)) return i;
    }
    return -1;
  }

  /**
   * Checks if a chapter is valid given the book index.
   *
   * @param book book index (beginning at 0)
   * @param chapter	chapter number
   * @return true if book has that chapter number
   */
  public boolean hasChapter(int book, int chapter) {
    return (book >= 0 && book < NUM_BOOKS) && (chapter > 0 && chapter <= NUM_CHAPTERS[book]);
  }

  /**
   * Checks if a verse is valid given the book index and chapter.
   *
   * @param book book(beginning at 0)
   * @param chapter	chapter number
   * @param verse	verse number
   * @return true if book has that verse
   */
  public boolean hasVerse(int book, int chapter, int verse) {
    return hasChapter(book, chapter) && verse > 0 && verse <= chapter(book, chapter).length;
  }

  public String[] chapter(int book, int chapter) {
    return indexedBible[book][chapter - 1];
  }

  /**
   * Returns the verse at book index, chapter #, and verse #
   *
   * @param book	book index (beginning at 0)
   * @param chapter	chapter number
   * @param verse	verse number
   * @return the corresponding line of text
   */
  public String verse(int book, int chapter, int verse) {
    return indexedBible[book][chapter - 1][verse - 1];
  }

  public String[] verses(int book, int chapter, int verse1, int verse2) {
    return versesFrom(book, chapter, verse1, verse2 - verse1);
  }

  public String[] versesFrom(int book, int chapter, int verse, int offset) {
    String[] verses = new String[offset + 1];
    for (int i = 0; i <= offset; i++) {
      verses[i] = verse(book, chapter, verse + i);
    }
    return verses;
  }

  /**
   * Returns 3D String array of Bible given a version
   *
   * @param bibleReader BufferedReader for the .txt file of the version
   * @return nested array of Strings of the Bible
   */
  private String[][][] readBible(BufferedReader bibleReader) throws IOException {
    // Create jagged 3D array of all the lines
    String[][][] book = new String[NUM_BOOKS][][]; // Number of books
    int[][] numVerses = new int[NUM_BOOKS][];
    for (int i = 0; i < NUM_BOOKS; i++) { // Assign correct number of chapters
      book[i] = new String[NUM_CHAPTERS[i]][];
      numVerses[i] = new int[NUM_CHAPTERS[i]];
      for (int j = 0; j < NUM_CHAPTERS[i]; j++) {
        book[i][j] = new String[200];
      }
    }
    
    String line;
    while ((line = bibleReader.readLine()) != null) {
      Verse v = new Verse(line, this::getBookIndex);
      book[v.getBook()][v.getChapter() - 1][v.getVerse() - 1] = v.getText();
      numVerses[v.getBook()][v.getChapter() - 1] = v.getVerse();
    }

    for (int i = 0; i < NUM_BOOKS; i++) {
      for (int j = 0; j < NUM_CHAPTERS[i]; j++) {
        book[i][j] = Arrays.copyOfRange(book[i][j], 0, numVerses[i][j]);;
      }
    }

    return book;
  }

  private static final int[] NUM_CHAPTERS = {
      50,
      40,
      27,
      36,
      34,
      24,
      21,
      4,
      31,
      24,
      22,
      25,
      29,
      36,
      10,
      13,
      10,
      42,
      150,
      31,
      12,
      8,
      66,
      52,
      5,
      48,
      12,
      14,
      3,
      9,
      1,
      4,
      7,
      3,
      3,
      3,
      2,
      14,
      4,
      28,
      16,
      24,
      21,
      28,
      16,
      16,
      13,
      6,
      6,
      4,
      4,
      5,
      3,
      6,
      4,
      3,
      1,
      13,
      5,
      5,
      3,
      5,
      1,
      1,
      1,
      22,
  };

  private static final int NUM_BOOKS = NUM_CHAPTERS.length;
}
