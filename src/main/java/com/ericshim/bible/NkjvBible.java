package com.ericshim.bible;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This Object reads in the NKJV text of the Bible,
 * and stores it as 3D String arrays with each dimension representing
 * the book, chapter, and verse, respectively.
 * The names of the files are hard-coded at the moment, but
 * can be easily changed to allow the names to be parameters
 * in the constructor.
 *
 * @author Eric Shim
 */
public class NkjvBible extends Bible {
  private static final String LANGUAGE = "English";
  private static final String FILE_NAME = "NKJVer.txt";

  public NkjvBible() throws IOException {
    super(new BufferedReader(
        new InputStreamReader(
            NkjvBible.class.getResourceAsStream('/' + FILE_NAME),
            "UTF8"
        )
    ));
  }

  @Override
  public String getLanguage() {
    return LANGUAGE;
  }

  @Override
  public String[] getBookNames() {
    return LIST_NKJV;
  }

  /**
   * Returns String of shortest possible input names for each book.
   * Used for initialization for the most part.
   *
   * @return string of shortest possible names
   */
  public String[] getShortestPossibleEnglishNames() {
    String[] bests = new String[LIST_NKJV.length];
    for (int i = 0; i < LIST_NKJV.length; i++) {
      String best = "";
      String name = LIST_NKJV[i];
      for (int j = 1; j <= name.length(); j++) {
        String sub = name.substring(0, j);
        if (getBookIndex(sub) == i) {
          bests[i] = sub;
          break;
        }
      }
    }

    return bests;
  }

  private static final String[] LIST_NKJV = {
      "Genesis",
      "Exodus",
      "Leviticus",
      "Numbers",
      "Deuteronomy",
      "Joshua",
      "Judges",
      "Ruth",
      "1 Samuel",
      "2 Samuel",
      "1 Kings",
      "2 Kings",
      "1 Chronicles",
      "2 Chronicles",
      "Ezra",
      "Nehemiah",
      "Esther",
      "Job",
      "Psalm",
      "Proverbs",
      "Ecclesiastes",
      "Song of Solomon",
      "Isaiah",
      "Jeremiah",
      "Lamentations",
      "Ezekiel",
      "Daniel",
      "Hosea",
      "Joel",
      "Amos",
      "Obadiah",
      "Jonah",
      "Micah",
      "Nahum",
      "Habakkuk",
      "Zephaniah",
      "Haggai",
      "Zechariah",
      "Malachi",
      "Matthew",
      "Mark",
      "Luke",
      "John",
      "Acts",
      "Romans",
      "1 Corinthians",
      "2 Corinthians",
      "Galatians",
      "Ephesians",
      "Philippians",
      "Colossians",
      "1 Thessalonians",
      "2 Thessalonians",
      "1 Timothy",
      "2 Timothy",
      "Titus",
      "Philemon",
      "Hebrews",
      "James",
      "1 Peter",
      "2 Peter",
      "1 John",
      "2 John",
      "3 John",
      "Jude",
      "Revelation",
  };
}
