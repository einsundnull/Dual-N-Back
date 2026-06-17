package popUps;

import enums.TypeOfPopUpWindow;
import files.ResultsFiles;
import language.LanguageClass;
import screens.StartScreen;
import window.CustomPopUpWindow;

public class ExitWithoutSavingResultsPopUp {

	private CustomPopUpWindow popUp = new CustomPopUpWindow(TypeOfPopUpWindow.WARNING);

	public ExitWithoutSavingResultsPopUp() {

		int indexLang = StartScreen.indexLang;
		String yes = LanguageClass.word(indexLang, 15);
		String no = LanguageClass.word(indexLang, 16);
		String exit = LanguageClass.word(indexLang, 51);

		popUp.setAlwaysOnTop(true);
		popUp.setText(exit);
		popUp.getConfirmButton().setText(yes);
		popUp.setConfirmButtonOnAction(() -> {
			ResultsFiles.copyDirectory("results", "temp");
			System.exit(0);
		});

		
		
		popUp.getCancelButton().setText(no);
		popUp.setCancelButtonOnAction(() -> {

			popUp.close();
			StartScreen.mainWindow.disable(false);
			popUp = null;
		});

		popUp.setOnCloseRequest(() -> {
			popUp.close();
			StartScreen.mainWindow.disable(false);
			popUp = null;
		});
	}

}
