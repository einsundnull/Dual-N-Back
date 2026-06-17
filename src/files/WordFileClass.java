package files;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class WordFileClass {

	private static String filePath = "";
	public static ArrayList<String> wordList = new ArrayList<String>();
	private static String[] defaultWordList;

	static {
		defaultText();
		readWordsFromFile();
	}

	public WordFileClass() {
		// TODO Auto-generated constructor stub
	}

	public static void readWordsFromFile() {
		if(new File(filePath).exists()) {
			try {
				Scanner scn = new Scanner(filePath);
				while (scn.hasNextLine()) {
					wordList.add(scn.nextLine());
				}
				scn.close();
			} catch (Exception e) {

			}
		} else {
			defaultText();
			for (int i = 0; i < 8; i++) {
				wordList.add(defaultWordList[i]);
			}
		}
	}

	private static void defaultText() {
		defaultWordList = new String[] { "yes", "no", "but", "whether", "if", "why", "than", "after" };
	}

}
