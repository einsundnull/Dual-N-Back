package files;

import static screens.OptionMenu.audioFileArray;
import static screens.OptionMenu.chooseAudioFileBooleanArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Formatter;
import java.util.Scanner;
import java.util.stream.Stream;

import logics.DurationTraining;
import screens.OptionMenu;
import screens.StartScreen;
import screens.TrainingScreen;
import training.TimerClass;

public class FilesCls {

	public static File fileStartUpSettings;
	public static File fileUserSettings;
	public static String programName = "n-back";
	public static String programPath;
	public static String programPathData;
	public static String audioFilePath;
//	public static String[] audioFileArray = new String [OptionMenu.numberOfRecordButtons]; 

	public static void setProgramPath() {
		programPathData = System.getProperty("user.home") + File.separator + programName + File.separator + "data";
		try {
			programPath = System.getProperty("user.home") + File.separator + programName + File.separator + "data" + File.separator + StartScreen.user + "_data";
		} catch (Exception e) {

		}

	}

	public static void createDataDirectory() {
		programPath = System.getProperty("user.home") + File.separator + programName + File.separator + "data" + File.separator + StartScreen.user + "_data";
		new File(programPath).mkdir();
		new File(programPath + File.separator + "temp").mkdir();
		new File(programPath + File.separator + "temp" + File.separator + "regularTraining").mkdir();
		new File(programPath + File.separator + "temp" + File.separator + "durationTraining").mkdir();
		new File(programPath + File.separator + "results").mkdir();
		new File(programPath + File.separator + "results" + File.separator + "regularTraining").mkdir();
		new File(programPath + File.separator + "results" + File.separator + "durationTraining").mkdir();

	}

	public static File createStartUpSettingsFile() {
		new File(System.getProperty("user.home") + File.separator + programName).mkdir();
		new File(System.getProperty("user.home") + File.separator + programName + File.separator + "user").mkdir();
		new File(System.getProperty("user.home") + File.separator + programName + File.separator + "data").mkdir();
		fileStartUpSettings = new File(System.getProperty("user.home") + File.separator + programName + File.separator + "data" + File.separator + "settings");
		if (!fileStartUpSettings.exists()) {
			Formatter formatter;
			try {
				formatter = new Formatter(fileStartUpSettings);

				formatter.format("%s%d\n %s%s\n %s%s\n ", "index_of_language: ", 0, "User_available ", "No", "Version: ", "2.0").close();

				new File(System.getProperty("user.home") + File.separator + programName).mkdir();
				new File(System.getProperty("user.home") + File.separator + programName + File.separator + "data").mkdir();
				new File(System.getProperty("user.home") + File.separator + programName + File.separator + "data" + File.separator + "_data" + File.separator + "Example_User_data")
						.mkdir();
				new File(System.getProperty("user.home") + File.separator + programName + File.separator + "user").mkdir();

				File source = new File(FilesCls.class.getResource("/example/Example_User").getFile());
				File target = new File(System.getProperty("user.home") + File.separator + programName + File.separator + "user" + File.separator + "Example_User");
				Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);

				source = new File(FilesCls.class.getResource("/example/Example_user_data").getFile());
				target = new File(System.getProperty("user.home") + File.separator + programName + File.separator + "data" + File.separator + "Example_User_data");
				copyDirectory(source.toPath(), target.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileStartUpSettings;
	}

	public static void fileStartUpSettingsGetValues() {
		try {
			Scanner scn = new Scanner(fileStartUpSettings);
			scn.next();
			StartScreen.indexLang = scn.nextInt();
			scn.next();
			StartScreen.userExists = scn.next();
			scn.next();
			StartScreen.versionsNumber = scn.next();
			scn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File createFileUserSettings() {
		if (new File(System.getProperty("user.home") + File.separator + programName + File.separator + "user" + File.separator + StartScreen.user).exists()) {
			new File(System.getProperty("user.home") + File.separator + programName + File.separator + "user" + File.separator + StartScreen.user).delete();
		}
		fileUserSettings = new File(System.getProperty("user.home") + File.separator + programName + File.separator + "user" + File.separator + StartScreen.user);
		try {
			String write = "n-back: " + 1 + "\nsessionLength: " + 20 + "\nduration_Training: " + false + "\nrepeat_Time: " + TimerClass.repeatTime + "\ndateOfLastTraining: "
					+ ResultsFiles.getDay() + "\nincludeAudio: " + true + "\nincludeVisualCol: " + false + "\nincludeVisualPos: " + true + "\nshowProgressBarDurationTraining: "
					+ true + "\nshowProgressBarRegularTraining: " + false + "\nshowRightAndFalseClicksInProgressBar: " + false + "\nshowRightClicksImediately: " + false
					+ "\nconfirmMatchAudio: " + "L" + "\nconfirmMatchColor: " + "D" + "\nconfirmMatchPosition: " + "S" + "\ncolorOfRectangle: " + "0";
			;
			FileWriter wrt = new FileWriter(fileUserSettings);
			wrt.write(write);
			for (int i = 0; i < OptionMenu.numberOfRecordButtons; i++) {
				String temp = "ChooseSoundFile_" + i + " " + "false";
				wrt.write(temp);
			}
			wrt.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return fileUserSettings;
	}

	public static void storeUserSettings() {
		String level = StartScreen.flds.get(0).getText().replaceAll("[^0-9]", "");
		String sessionLength = StartScreen.flds.get(1).getText().replaceAll("[^0-9]", "");
		String path = System.getProperty("user.home") + File.separator + programName + File.separator + "user" + File.separator + StartScreen.user;
		String write = "n-back: " + level + "\nsessionLength: " + sessionLength + "\ndateOfLastTraining: " + StartScreen.dateOfLastUse + "\nrepeat_Time: " + TimerClass.repeatTime
				+ "\nincludeAudio: " + StartScreen.includeAudio + "\nincludeVisualCol: " + StartScreen.includeColor + "\nincludeVisualPos: " + StartScreen.includePosition
				+ "\ndurationTraining: " + StartScreen.durationTraining + "\nshowProgressBarDurationTraining: " + DurationTraining.showProgressBarDurationTraining
				+ "\nshowProgressBarRegularTraining: " + DurationTraining.showProgressBarRegularTraining + "\nshowRightAndFalseClicksInProgressBar: "
				+ DurationTraining.showRightAndFalseClicksInProgressBar + "\nshowRightClicksImediately: " + DurationTraining.showRightClicksImediately + "\nconfirmMatchAudio: "
				+ TrainingScreen.confirmMatchAudio + "\nconfirmMatchColor: " + TrainingScreen.confirmMatchColor + "\nconfirmMatchPosition: " + TrainingScreen.confirmMatchPosition
				+ "\ncolorOfRectangle: " + TrainingScreen.colorIndex;
		try {
			FileWriter wrt = new FileWriter(path);
			wrt.write(write);
			for (int i = 0; i < OptionMenu.numberOfRecordButtons; i++) {
				wrt.write("\nChooseSoundFile_" + i + " " + chooseAudioFileBooleanArray[i]);
			}
			wrt.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void scanFileUserSettingsAndSetValuesToTextFields() {
		try {
			Scanner scn = new Scanner(fileUserSettings);
			scn.next();
			StartScreen.flds.get(0).setText(String.valueOf(scn.nextInt()));
			scn.next();
			StartScreen.flds.get(1).setText(String.valueOf(scn.nextInt()));
			scn.next();
			StartScreen.dateOfLastUse = scn.next();
			scn.next();
			TimerClass.repeatTime = scn.nextInt();
			scn.next();
			StartScreen.includeAudio = scn.nextBoolean();
			scn.next();
			StartScreen.includeColor = scn.nextBoolean();
			scn.next();
			StartScreen.includePosition = scn.nextBoolean();
			scn.next();
			StartScreen.durationTraining = scn.nextBoolean();
			scn.next();
			DurationTraining.showProgressBarDurationTraining = scn.nextBoolean();
			scn.next();
			DurationTraining.showProgressBarRegularTraining = scn.nextBoolean();
			scn.next();
			DurationTraining.showRightAndFalseClicksInProgressBar = scn.nextBoolean();
			scn.next();
			DurationTraining.showRightClicksImediately = scn.nextBoolean();
			scn.next();
			TrainingScreen.confirmMatchAudio = scn.next();
			scn.next();
			TrainingScreen.confirmMatchColor = scn.next();
			scn.next();
			TrainingScreen.confirmMatchPosition = scn.next();
			scn.next();
			TrainingScreen.colorIndex =  Integer.parseInt(scn.next());
			scn.next();
			int i = 0;
			while (i < OptionMenu.numberOfRecordButtons) {
				chooseAudioFileBooleanArray[i] = scn.nextBoolean();
				i++;
				scn.next();
			}
			scn.close();
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("resource")
	public static void storeFileStartUpSettings() {
		try {
			Formatter frm;
			frm = new Formatter(System.getProperty("user.home") + File.separator + programName + File.separator + "data" + File.separator + "settings").format(
					"%s%d\n%s%s\n%s%s\n%s%s\n", "index_of_language: ", StartScreen.indexLang, "User_available: ", StartScreen.userExists, "Version: ", "2.0", "date_of_last_use: ",
					StartScreen.dateOfLastUse);
			frm.close();
		} catch (FileNotFoundException ex) {
		}

	}

	public static void setAudioFilePath() {
		try {
			FilesCls.audioFilePath = FilesCls.programPath + File.separator + "AUDIO_FILES";
		} catch (Exception e) {

		}

	}

	public static void loadAudioFiles() {
		try {
			File file = new File(audioFilePath + File.separator + "audioFiles");
			if (file.exists()) {
				Scanner scn = new Scanner(file);
				int i = 0;
				while (i < OptionMenu.numberOfRecordButtons) {
					audioFileArray[i] = scn.next();
					i++;
				}
				scn.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void createCheckBoxesChooseAudioFileBooleanArray() {
		for (int i = 0; i < OptionMenu.numberOfRecordButtons; i++) {
			chooseAudioFileBooleanArray[i] = false;
		}
	}

	public static void createAudioFileArray() {
		new File(FilesCls.audioFilePath).mkdir();
		audioFileArray = new String[] { "#", "#", "#", "#", "#", "#", "#", "#", "#" };
	}

	public static void writeAudioArrayFile() {
		try {

			FileWriter wrt = new FileWriter(new File(audioFilePath + File.separator + "audioFiles"));
			for (int i = 0; i < OptionMenu.numberOfRecordButtons; i++) {
				wrt.write(audioFileArray[i] + "\n");
			}
			wrt.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void copyDirectory(Path src, Path dest) throws IOException {
		try (Stream<Path> stream = Files.walk(src)) {
			stream.forEach(source -> {
				Path target = dest.resolve(src.relativize(source));
				try {
					if (Files.isDirectory(source)) {
						Files.createDirectories(target);
					} else {
						Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
		}
	}
}
