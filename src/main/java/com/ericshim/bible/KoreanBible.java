package com.ericshim.bible;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This Object reads in Korean text of the Bible,
 * and stores it as 3D String arrays with each dimension representing
 * the book, chapter, and verse, respectively.
 * The names of the files are hard-coded at the moment, but
 * can be easily changed to allow the names to be parameters
 * in the constructor.
 *
 * @author Eric Shim
 */
public class KoreanBible extends Bible {
  private static final String LANGUAGE = "Korean";
  private static final String FILE_NAME = "KoreanVer.txt";

  public KoreanBible() throws IOException {
    super(new BufferedReader(
        new InputStreamReader(
            KoreanBible.class.getResourceAsStream('/' + FILE_NAME),
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
    return LIST_KOR_LONG;
  }

  @Override
  public String[] getShortenedBookNames() {
    return LIST_KOR_SHORT;
  }

  @Override
  public int getBookIndex(String book) {
    if (book.isEmpty()) return -1;

    // First check through the abbreviations.
    for(int i = 0; i < LIST_KOR_SHORT.length; i++){
      if (LIST_KOR_SHORT[i].equals(book)) return i;
    }

    for(int i = 0; i < LIST_KOR_LONG.length; i++){
      if (LIST_KOR_LONG[i].contains(book)) return i;
    }
    return -1;
  }

  private static final String[] LIST_KOR_SHORT = {
      "창",
      "출",
      "레",
      "민",
      "신",
      "수",
      "삿",
      "룻",
      "삼상",
      "삼하",
      "왕상",
      "왕하",
      "대상",
      "대하",
      "스",
      "느",
      "에",
      "욥",
      "시",
      "잠",
      "전",
      "아",
      "사",
      "렘",
      "애",
      "겔",
      "단",
      "호",
      "욜",
      "암",
      "옵",
      "욘",
      "미",
      "나",
      "합",
      "습",
      "학",
      "슥",
      "말",
      "마",
      "막",
      "눅",
      "요",
      "행",
      "롬",
      "고전",
      "고후",
      "갈",
      "엡",
      "빌",
      "골",
      "살전",
      "살후",
      "딤전",
      "딤후",
      "딛",
      "몬",
      "히",
      "약",
      "벧전",
      "벧후",
      "요일",
      "요일",
      "요삼",
      "유",
      "계",
  };

  private static final String[] LIST_KOR_LONG = {
      "창세기",
      "출애굽기",
      "레위기",
      "민수기",
      "신명기",
      "여호수아",
      "사사기",
      "룻기",
      "사무엘상",
      "사무엘하",
      "열왕기",
      "열왕기",
      "역대기",
      "역대기",
      "에스라",
      "느헤미",
      "에스더",
      "옵기",
      "시편",
      "잠언",
      "전도서",
      "아가",
      "이사야",
      "예레미야",
      "애가",
      "에스겔",
      "다니엘",
      "호세아",
      "요엘",
      "아모스",
      "오바댜",
      "요나",
      "미가",
      "나훔",
      "하박국",
      "스바냐",
      "학개",
      "스가랴",
      "말라기",
  };
}
