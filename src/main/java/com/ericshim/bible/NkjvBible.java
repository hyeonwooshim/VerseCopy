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
            "UTF-8"
        )
    ));
  }

  @Override
  public String getLanguage() {
    return LANGUAGE;
  }

  @Override
  public String[] getBookNames() {
    return LIST_NKJV_LONG;
  }

  @Override
  public String[] getShortenedBookNames() {
    return LIST_NKJV_SHORT;
  }

  @Override
  public int getBookIndex(String book) {
    if (book.isEmpty()) return -1;

    // First check through the abbreviations.
    for(int i = 0; i < LIST_NKJV_SHORT.length; i++){
      if (LIST_NKJV_SHORT[i].equalsIgnoreCase(book)) return i;
    }
    return super.getBookIndex(book);
  }

  /**
   * Returns String of shortest possible input names for each book.
   * Used for initialization for the most part.
   *
   * @return string of shortest possible names
   */
  public String[] getShortestPossibleEnglishNames() {
    String[] bests = new String[LIST_NKJV_LONG.length];
    for (int i = 0; i < LIST_NKJV_LONG.length; i++) {
      String name = LIST_NKJV_LONG[i];
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

  private static final String[] LIST_NKJV_LONG = {
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
      "Psalms",
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

  private static final String[] LIST_NKJV_SHORT = {
      "Gen",
      "Ex",
      "Lev",
      "Num",
      "Deut",
      "Josh",
      "Judg",
      "Ruth",
      "1Sam",
      "2Sam",
      "1Kin",
      "2Kin",
      "1Chr",
      "2Chr",
      "Ezra",
      "Neh",
      "Esther",
      "Job",
      "Ps",
      "Prov",
      "Eccles",
      "Song",
      "Is",
      "Jer",
      "Lam",
      "Ezek",
      "Dan",
      "Hos",
      "Joel",
      "Amos",
      "Obad",
      "Jonah",
      "Mic",
      "Nah",
      "Hab",
      "Zeph",
      "Hag",
      "Zech",
      "Mal",
      "Matt",
      "Mark",
      "Luke",
      "John",
      "Acts",
      "Rom",
      "1Cor",
      "2Cor",
      "Gal",
      "Eph",
      "Phil",
      "Col",
      "1Thess",
      "2Thess",
      "1Tim",
      "2Tim",
      "Titus",
      "Philem",
      "Heb",
      "James",
      "1Pet",
      "2Pet",
      "1John",
      "2John",
      "3John",
      "Jude",
      "Rev",
  };
}
