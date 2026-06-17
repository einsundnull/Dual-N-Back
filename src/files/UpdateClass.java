package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import screens.StartScreen;

public class UpdateClass {

	private ArrayList<String> list;

	boolean add = true;

	public void update(String folder, String mode) {
		list = new ArrayList<String>();
		String date = "";
		String day = "";
		File file = new File(System.getProperty("user.home") + File.separator + "n-back" + File.separator + "data"
				+ File.separator + StartScreen.user + "_data" + File.separator + folder + File.separator
				+ "regularTraining" + File.separator + mode + File.separator + "overall");
		try {
			Scanner scn = new Scanner(file);
			while (scn.hasNext()) {
				String temp = scn.next();
				list.add(temp);
			}
			scn.close();
			int size = list.size();
			for (int i = 0; i < size; i++) {
				if (list.get(i).equals("Next_Day")) {
					list.remove(i);
					list.add(i, "");
				}
			}
			for (int i = 0; i < size; i++) {
				try {
					if (list.get(i).equals("")) {
						list.remove(i);
					}
				} catch (IndexOutOfBoundsException e) {
				}

			}
			size = list.size();
			for (int i = 0; i < size; i++) {
				String temp = list.get(i);
				if (temp.equals("date:")) {
					date = list.get(i + 1);
				}
				if (temp.equals("day:")) {
					day = list.get(i + 1);
				}
				try {
					if (!date.equals(day)) {
						day = date;
						list.add(i, "Next_Day");
					}

				} catch (IndexOutOfBoundsException e) {
				}
				if (temp.equals("day:") && list.get(i + 1).equals("Next_Day")) {
					list.remove(i + 1);
					list.add("");
				}

			}
			size = list.size();
			while (list.get(0).equals("Next_Day")) {
				list.remove(0);
			}
			
			while (list.get(2).equals("Next_Day")) {
				list.remove(2);
			}
			size = list.size();
			Formatter frm = new Formatter(file);
			for (int i = 0; i < size; i++) {
				frm.format(list.get(i) + "\n");
			}
			frm.close();
		} catch (FileNotFoundException | FileSystemNotFoundException e) {
			
		}

	}

}
