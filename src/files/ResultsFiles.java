package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import screens.StartScreen;

public class ResultsFiles {

	private static String sep = File.separator;
	private static String data = "data";
	private static String prgName = "n-back";
	private static String userHome = System.getProperty("user.home");
	@SuppressWarnings("unused")
	private static String userFolder = StartScreen.user + "_data"; // matthias_data

	public static String tempAudioRegular;
	public static String tempColorRegular;
	public static String tempPositionRegular;
	public static String tempAudioDuration;
	public static String tempColorDuration;
	public static String tempPositionDuration;
	public static String tempAudioRegularPercentage;
	public static String tempColorRegularPercentage;
	public static String tempPositionRegularPercentage;
	public static String tempAudioDurationPercentage;
	public static String tempColorDurationPercentage;
	public static String tempPositionDurationPercentage;
	public static String tempCombinedAudioColorRegular;
	public static String tempCombinedAudioPositionRegular;
	public static String tempCombinedColorPositionRegular;
	public static String tempCombinedPositionRegular;
	public static String tempCombinedColorRegular;
	public static String tempCombinedAudioRegular;
	public static String tempCombinedAudioDuration;
	public static String tempCombinedColorDuration;
	public static String tempCombinedPositionDuration;
	public static String tempCombinedAudioColorDuration;
	public static String tempCombinedAudioPositionDuration;
	public static String tempCombinedColorPositionDuration;
	public static String tempCombinedAudioPositionRegularPercentage;
	public static String tempCombinedColorPositionRegularPercentage;
	public static String tempCombinedAudioColorDurationPercentage;
	public static String tempCombinedAudioPositionDurationPercentage;
	public static String tempCombinedColorPositionDurationPercentage;
	public static String tempCombinedAudioColorPosition;
	public static String tempCombinedAudioColorPositionRegular;
	public static String tempCombinedAudioColorPositionDuration;
	public static String tempCombinedAudioColorPositionRegularPercentage;
	public static String tempCombinedAudioColorDurationRegularPercentage;
	public static String overAllOnlyAudio;
	public static String overallOnlyColor;
	public static String overallOnlyPosition;
	public static String storingPathRegular;
	public static String storingPathDuration;


	public static void initialiseStoringFilePaths() {
//		String user = StartScreen.user;
//		user = user.replace(" ", "_");
		String tempReg = userHome + sep + prgName + sep + data + sep + StartScreen.user + "_data" + sep + "temp" + sep + "regularTraining";
		String tempDur = userHome + sep + prgName + sep + data + sep + StartScreen.user + "_data" + sep + "temp" + sep + "durationTraining";

		tempAudioRegular = tempReg + sep + "audio";
		tempColorRegular = tempReg + sep + "color";
		tempPositionRegular = tempReg + sep + "position";
		tempAudioDuration = tempDur + sep + "audio";
		tempColorDuration = tempDur + sep + "color";
		tempPositionDuration = tempDur + sep + "position";

		tempCombinedAudioColorPositionRegular = tempReg + sep + "audioColorPosition";
		tempCombinedAudioColorRegular = tempReg + sep + "audioColor";
		tempCombinedAudioPositionRegular = tempReg + sep + "audioPosition";
		tempCombinedColorPositionRegular = tempReg + sep + "colorPosition";
		tempCombinedAudioColorDuration = tempDur + sep + "audioColor";
		tempCombinedAudioPositionDuration = tempDur + sep + "audioPosition";
		tempCombinedColorPositionDuration = tempDur + sep + "colorPosition";

		tempAudioRegularPercentage = tempReg + sep + "audioPercentage";
		tempColorRegularPercentage = tempReg + sep + "colorPercentage";
		tempPositionRegularPercentage = tempReg + sep + "positionPercentage";
		tempAudioDurationPercentage = tempDur + sep + "audioPercentage";
		tempColorDurationPercentage = tempDur + sep + "colorPercentage";
		tempPositionDurationPercentage = tempDur + sep + "positionPercentage";

		tempCombinedAudioColorPositionRegularPercentage = tempReg + sep + "audioColorPosition";
		tempCombinedAudioPositionRegularPercentage = tempReg + sep + "audioPositionPercentage";
		tempCombinedColorPositionRegularPercentage = tempReg + sep + "colorPositionPercentage";
		tempCombinedAudioColorDurationPercentage = tempDur + sep + "audioColorPercentage";
		tempCombinedAudioColorDurationRegularPercentage = tempReg + sep + "audioColorPosition";
		tempCombinedColorPositionDurationPercentage = tempDur + sep + "colorPositionPercentage";
		tempCombinedAudioColorPositionRegular = tempReg + sep + "audioColorPosition";
		tempCombinedAudioColorPositionDuration = tempDur + sep + "audioColorPosition";	
		
		tempCombinedAudioDuration = tempDur + sep + "audioOnly";
		tempCombinedColorDuration = tempDur + sep + "colorOnly";
		tempCombinedPositionDuration = tempDur + sep + "positionOnly";
		tempCombinedAudioRegular = tempReg + sep + "audioOnly";
		tempCombinedColorRegular = tempReg + sep + "colorOnly";
		tempCombinedPositionRegular = tempReg + sep + "positionOnly";

	}


	public static void writeToFile(String input, String path) {

		File results = new File(path);
		try {
			FileWriter fwr = new FileWriter(results, true);
			fwr.write(input);
			fwr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToFile(ArrayList<String> input, String path) {

		File results = new File(path);
		try {
			FileWriter fwr = new FileWriter(results, true);
			for (int i = 0; i < input.size(); i++) {
				fwr.write(input.get(i));
			}
			fwr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void copyDirectory(String source, String target) {
		String user = StartScreen.user;
		user = user.replace(" ", "_");
		String userFolder = user + "_data";
		File sourceFile = new File(userHome + sep + prgName + sep + data + sep + userFolder + sep + source);
		File targetFile = new File(userHome + sep + prgName + sep + data + sep + userFolder + sep + target);
//		try {
////			FileUtils.copyDirectory(sourceFile, targetFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static void deleteUser() {
		String userFolder = StartScreen.userToDelete + "_data"; // matthias_data
		String user = "user";
		String userName = StartScreen.userToDelete;
		new File(userHome + sep + prgName + sep + user + sep + userName).delete();
		File dataFile = new File(userHome + sep + prgName + sep + data + sep + userFolder);
		File userFile = new File(userHome + sep + prgName + sep + user + sep + userName);
		if (dataFile.exists()) {
//			try {
//				FileUtils.cleanDirectory(dataFile);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			dataFile.delete();
		}
		if (userFile.exists()) {
			userFile.delete();
		}
	}

	public static void checkProgammForLastUse() {
		String data = "user";
		String userFolder = StartScreen.user; // matthias_data
		File file = new File(userHome + sep + prgName + sep + data + sep + userFolder);
		Scanner scn;
		try {
			scn = new Scanner(file);
			while (scn.hasNext()) {
				String temp = scn.next();
				if (temp.equals("day:")) {
					StartScreen.dateOfLastUse = scn.next();
				}
			}
			scn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}



	public static String getDateAndTimeForTrials() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd_HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public static String getDay() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

}
