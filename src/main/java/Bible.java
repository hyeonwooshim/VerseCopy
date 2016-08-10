import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class Bible {
    private String koreanFileName = "KoreanVer.txt";
	private String englishFileName = "NKJVer.txt";
	private int totalNumberOfVerses = 31102;
    private String[] allVersesEng; //may not be needed
	private String[] allVersesKor;

    private String[][][] engBook;
    private String[][][] korBook;

    FileInputStream kv;
	BufferedReader inKorean;
    FileInputStream nkjv;
	BufferedReader inEnglish;

    public Bible() throws IOException {
        allVersesEng = new String[totalNumberOfVerses];
        allVersesKor = new String[totalNumberOfVerses];

        kv = new FileInputStream(koreanFileName);
        inKorean = new BufferedReader(new InputStreamReader(kv, "UTF8"));
        nkjv = new FileInputStream(englishFileName);
        inEnglish = new BufferedReader(new InputStreamReader(nkjv));

        engBook = readText(inEnglish);
        korBook = readText(inKorean);

        readInEng(); //may not need this
        readInKor(); //may not need this

    }

    /**
    * Returns book number given a string of the name or part of the name.
    * Returns a -1 if book is not found.
    *
    * @param str    name of the book
    * @return the number of the book
    */
    public int findBook(String str){
        if (str.isEmpty()) return -1;

		String lower = str.toLowerCase();
		for(int i = 0; i < LIST_ENG.length; i++){
			String bookName = LIST_ENG[i].toLowerCase();
			if (bookName.indexOf(lower) >= 0 || LIST_KOR[i].equals(str)){
				return i;
			}
		}

		return -1;
	}

    /**
    * Returns the line of English version at book #, chapter #, and verse #
    *
    * @param book
    * @param chap
    * @param verse
    * @return the line
    */
    public String getEngLineAt(int book, int chap, int verse) {
        return engBook[book][chap][verse];
    }

    /**
    * Returns the line of Korean version at book #, chapter #, and verse #
    *
    * @param book
    * @param chap
    * @param verse
    * @return the line
    */
    public String getKorLineAt(int book, int chap, int verse) {
        return korBook[book][chap][verse];
    }

    public boolean validChapter(int book, int chap) {
        if (chap <= 0) {
            return false;
        } else if (chap > engBook[book].length) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validVerse(int book, int chap, int verse) {
        if (!validChapter(book, chap)) {
            return false;
        }

        if (verse <= 0) {
            return false;
        } else if (verse > engBook[book][chap-1].length) {
            return false;
        } else {
            return true;
        }
    }


    /**
    * Returns String of shortest possible input names for each book
    *
    * @return string of shortest possible names
    */
    public String[] getShortestPossibleEnglishNames() {
        String[] bests = new String[LIST_ENG.length];
        for (int i = 0; i < LIST_ENG.length; i++) {
            String best = "";
            String name = LIST_ENG[i];
            for (int j = 1; j <= name.length(); j++) {
                String sub = name.substring(0, j);
                if (findBook(sub) == i) {
                    bests[i] = sub;
                    break;
                }
            }
        }

        return bests;
    }



    /**
    * Returns 3D String array of Bible given a version
    *
    * @param input    BufferedReader for the .txt file of the version
    * @return array of strings of the Bible
    */
    private String[][][] readText(BufferedReader input) throws IOException {
		// Create jagged 3D array of all the lines
		String[][][] book = new String[66][][]; //number of books
		for (int i = 0; i < 66; i++) { //assign correct number of chapters
			book[i] = new String[numChapters[i]][];
		}

		int previousVerse = 0;
		int currentVerse = 1;

		//Process first line of the file
		String line = input.readLine();
		String oneLine = line.substring(1);
		oneLine = oneLine.replaceAll("[^0-9]+", " "); //returns all integers in the string
		String[] d = oneLine.trim().split(" ");
		currentVerse = Integer.parseInt(d[1]);

		int num = 1; //keep track of how many lines we've read
		for (int i = 0; i < 66; i++) { //for each book
			for (int j = 0; j < numChapters[i]; j++) { //for each chapter
				String[] chapLineCollection = new String[176];

				int index = 0; //keep track of line number starting at 0 read for EACH CHAPTER
				while (previousVerse < currentVerse && num <= totalNumberOfVerses){
					previousVerse = currentVerse;

					line = line.substring(line.indexOf(" ")+1); //keep just the content of the line
					chapLineCollection[index] = line;
					index++; //increment index in chapLineCollection String[]

					//read in new line and check chapter
					line = input.readLine();
					if (line != null) {
					oneLine = line.substring(1);
					oneLine = oneLine.replaceAll("[^0-9]+", " ");
					d = oneLine.trim().split(" ");
					currentVerse = Integer.parseInt(d[1]);
					}
					num++; //increment total number of lines we've read
				}

				book[i][j] = Arrays.copyOfRange(chapLineCollection, 0, index);

				index = 0;
				previousVerse = 0;

			}
		}
		return book;
	}


    // not quite necessary
    private void readInEng() throws IOException {
        String line = "";
		int num = 0;
		while ((line = inEnglish.readLine()) != null){
			allVersesEng[num] = line;
			num++;
		}
    }

    private void readInKor() throws IOException {
        String line = "";
		int num = 0;
		while ((line = inKorean.readLine()) != null){
			allVersesKor[num] = line;
			num++;
		}
    }

    public final int[] numChapters = {
    		50
    		,40
    		,27
    		,36
    		,34
    		,24
    		,21
    		,4
    		,31
    		,24
    		,22
    		,25
    		,29
    		,36
    		,10
    		,13
    		,10
    		,42
    		,150
    		,31
    		,12
    		,8
    		,66
    		,52
    		,5
    		,48
    		,12
    		,14
    		,3
    		,9
    		,1
    		,4
    		,7
    		,3
    		,3
    		,3
    		,2
    		,14
    		,4
    		,28
    		,16
    		,24
    		,21
    		,28
    		,16
    		,16
    		,13
    		,6
    		,6
    		,4
    		,4
    		,5
    		,3
    		,6
    		,4
    		,3
    		,1
    		,13
    		,5
    		,5
    		,3
    		,5
    		,1
    		,1
    		,1
    		,22
    };

    public final int[] numVerses = {
        			1533 //Genesis
        			,1213 //Exodus
        			,859 //Leviticus
        			,1288 //etc.
        			,959
        			,658
        			,618
        			,85
        			,810
        			,695
        			,816
        			,719
        			,942
        			,822
        			,280
        			,406
        			,167
        			,1070
        			,2461
        			,915
        			,222
        			,117
        			,1292
        			,1364
        			,154
        			,1273
        			,357
        			,197
        			,73
        			,146
        			,21
        			,48
        			,105
        			,47
        			,56
        			,53
        			,38
        			,211
        			,55
        			,1071 //Matthew
        			,678 //Mark
        			,1151 //Luke
        			,879 //John
        			,1007 //Acts
        			,433 //Romans
        			,437 //1 Corinthians
        			,257 //2 Corinthians
        			,149 //Galatians
        			,155 //Ephesians
        			,104 //Philippians
        			,95 //Colossians
        			,89 //1 Thessalonians
        			,47 //2 Thessalonians
        			,113 //1 Timothy
        			,83 //2 Timothy
        			,46 //Titus
        			,25 //Philemon
        			,303 //Hebrews
        			,108 //James
        			,105 //1 Peter
        			,61 //2 Peter
        			,105 //1 John
        			,13 //2 John
        			,14 //3 John
        			,25 //Jude
        			,404 //Revelation
    };

    public final String[] LIST_KOR = {
    		"창"
    		,"출"
    		,"레"
    		,"민"
    		,"신"
    		,"수"
    		,"삿"
    		,"룻"
    		,"삼상"
    		,"삼하"
    		,"왕상"
    		,"왕하"
    		,"대상"
    		,"대하"
    		,"스"
    		,"느"
    		,"에"
    		,"욥"
    		,"시"
    		,"잠"
    		,"전"
    		,"아"
    		,"사"
    		,"렘"
    		,"애"
    		,"겔"
    		,"단"
    		,"호"
    		,"욜"
    		,"암"
    		,"옵"
    		,"욘"
    		,"미"
    		,"나"
    		,"합"
    		,"습"
    		,"학"
    		,"슥"
    		,"말"
    		,"마"
    		,"막"
    		,"눅"
    		,"요"
    		,"행"
    		,"롬"
    		,"고전"
    		,"고후"
    		,"갈"
    		,"엡"
    		,"빌"
    		,"골"
    		,"살전"
    		,"살후"
    		,"딤전"
    		,"딤후"
    		,"딛"
    		,"몬"
    		,"히"
    		,"약"
    		,"벧전"
    		,"벧후"
    		,"요일"
    		,"요일"
    		,"요삼"
    		,"유"
    		,"계"
	};

    public final String[] LIST_ENG = {
                "Genesis"
    			,"Exodus"
    			,"Leviticus"
    			,"Numbers"
    			,"Deuteronomy"
    			,"Joshua"
    			,"Judges"
    			,"Ruth"
    			,"1 Samuel"
    			,"2 Samuel"
    			,"1 Kings"
    			,"2 Kings"
    			,"1 Chronicles"
    			,"2 Chronicles"
    			,"Ezra"
    			,"Nehemiah"
    			,"Esther"
    			,"Job"
    			,"Psalm"
    			,"Proverbs"
    			,"Ecclesiastes"
    			,"Song of Solomon"
    			,"Isaiah"
    			,"Jeremiah"
    			,"Lamentations"
    			,"Ezekiel"
    			,"Daniel"
    			,"Hosea"
    			,"Joel"
    			,"Amos"
    			,"Obadiah"
    			,"Jonah"
    			,"Micah"
    			,"Nahum"
    			,"Habakkuk"
    			,"Zephaniah"
    			,"Haggai"
    			,"Zechariah"
    			,"Malachi"
    			,"Matthew"
    			,"Mark"
    			,"Luke"
    			,"John"
    			,"Acts"
    			,"Romans"
    			,"1 Corinthians"
    			,"2 Corinthians"
    			,"Galatians"
    			,"Ephesians"
    			,"Philippians"
    			,"Colossians"
    			,"1 Thessalonians"
    			,"2 Thessalonians"
    			,"1 Timothy"
    			,"2 Timothy"
    			,"Titus"
    			,"Philemon"
    			,"Hebrews"
    			,"James"
    			,"1 Peter"
    			,"2 Peter"
    			,"1 John"
    			,"2 John"
    			,"3 John"
    			,"Jude"
    			,"Revelation"
    };

}
