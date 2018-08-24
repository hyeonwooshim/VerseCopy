package com.ericshim.bible;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * This abstract class by default reads in a text file of a version of the bible,
 * and stores it as 3D String arrays with each dimension representing
 * the book, chapter, and verse, respectively.
 *
 * @author Eric Shim
 */
public abstract class Bible {
  private String[][][] indexedBook = null;

  /**
   * Constructor in the case for text files with each line being a verse.
   * The lines must have a very specific format.
   *
   * @param bibleReader BufferedReader that has the verses at each line
   */
  public Bible(BufferedReader bibleReader) throws IOException {
    if (bibleReader == null) return;
    indexedBook = readBible(bibleReader);
  }

  /**
   * @return English name of the language that this Bible is in
   */
  public abstract String getLanguage();

  /**
   * @return Array of full book names in this Bible
   */
  public abstract String[] getBookNames();

  public String[] getShortenedBookNames() {
    return getBookNames();
  }

  public String getBookName(int book) {
    return getBookNames()[book];
  }

  public String getShortenedBookName(int book) {
    return getShortenedBookNames()[book];
  }

  /**
   * Returns book number given a string of the name or search string for the name.
   * Returns a -1 if book is not found. Default behavior is to check for lower-case
   * substrings.
   *
   * @param book    name of the book
   * @return the index of the book
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
    return indexedBook[book][chapter - 1];
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
    return indexedBook[book][chapter - 1][verse - 1];
  }

  public String[] verses(int book, int chapter, int verse1, int verse2) {
    String[] verses = new String[verse2 - verse1 + 1];
    for (int i = verse1; i <= verse2; i++) {
      verses[i - verse1] = verse(book, chapter, i);
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
    for (int i = 0; i < NUM_BOOKS; i++) { // Assign correct number of chapters
      book[i] = new String[NUM_CHAPTERS[i]][];
    }

    int previousVerse = 0;
    int currentVerse = 1;

    // Process first line of the file
    String line = bibleReader.readLine();
    String oneLine = line.substring(1);
    oneLine = oneLine.replaceAll("[^0-9]+", " "); // Returns all integers in it
    String[] d = oneLine.trim().split(" ");
    currentVerse = Integer.parseInt(d[1]);

    int num = 1; // Keep track of how many lines we've read
    for (int i = 0; i < 66; i++) { // for each book
      for (int j = 0; j < NUM_CHAPTERS[i]; j++) { // for each chapter
        String[] chapLineCollection = new String[180];

        int index = 0; // Keep track of line number starting at 0 read for EACH CHAPTER
        while (previousVerse < currentVerse && num <= TOTAL_NUM_VERSES){
          previousVerse = currentVerse;

          line = line.substring(line.indexOf(" ")+1); // Keep just the content of the line
          chapLineCollection[index] = line;
          index++; // Increment index in chapLineCollection String[]

          // Read in new line and check chapter
          line = bibleReader.readLine();
          if (line != null) {
            oneLine = line.substring(1);
            oneLine = oneLine.replaceAll("[^0-9]+", " ");
            d = oneLine.trim().split(" ");
            currentVerse = Integer.parseInt(d[1]);
          }
          num++; // Increment total number of lines we've read
        }

        book[i][j] = Arrays.copyOfRange(chapLineCollection, 0, index);

        previousVerse = 0;
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

  private static final int[] NUM_VERSES = {
      1533, // Genesis
      1213, // Exodus
      859 , // Leviticus
      1288, // etc.
      95,
      65,
      61,
      8,
      81,
      69,
      81,
      71,
      94,
      82,
      28,
      40,
      16,
      107,
      246,
      91,
      22,
      11,
      129,
      136,
      15,
      127,
      35,
      19,
      7,
      14,
      2,
      4,
      10,
      4,
      5,
      5,
      3,
      21,
      5,
      1071, // Matthew
      678,  // Mark
      1151, // Luke
      879,  // John
      1007, // Acts
      433,  // Romans
      437,  // 1 Corinthians
      257,  // 2 Corinthians
      149,  // Galatians
      155,  // Ephesians
      104,  // Philippians
      95,   // Colossians
      89,   // 1 Thessalonians
      47,   // 2 Thessalonians
      113,  // 1 Timothy
      83,   // 2 Timothy
      46,   // Titus
      25,   // Philemon
      303,  // Hebrews
      108,  // James
      105,  // 1 Peter
      61,   // 2 Peter
      105,  // 1 John
      13,   // 2 John
      14,   // 3 John
      25,   // Jude
      404,  // Revelation
  };

  private static final int TOTAL_NUM_VERSES = IntStream.of(NUM_VERSES).sum();
  private static final int NUM_BOOKS = NUM_CHAPTERS.length;
}
