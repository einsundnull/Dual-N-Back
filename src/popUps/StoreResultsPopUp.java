package popUps;

import java.awt.Color;

import enums.TypeOfPopUpWindow;
import files.ResultsFiles;
import language.LanguageClass;
import screens.StartScreen;
import window.CustomPopUpWindow;

public class StoreResultsPopUp {
	public StoreResultsPopUp() {

		StartScreen.mainWindow.getFront().setOpacity(0.7);
		StartScreen.mainWindow.getFront().setMouseTransparent(true);

		CustomPopUpWindow stagePopUpWindow = new CustomPopUpWindow(TypeOfPopUpWindow.WARNING);
		stagePopUpWindow.setText(StartScreen.OverwriteData);
		stagePopUpWindow.setAlwaysOnTop(true);
		stagePopUpWindow.getConfirmButton().setText(LanguageClass.word(StartScreen.indexLang, 15));
		stagePopUpWindow.getCancelButton().setText(LanguageClass.word(StartScreen.indexLang, 16));
		stagePopUpWindow.getConfirmButton().setOnMouseClicked(() -> {

			StartScreen.btns.get(4).getFront().setBackground(Color.WHITE);
			stagePopUpWindow.close();
			StartScreen.mainWindow.getFront().setOpacity(1);
			StartScreen.mainWindow.getFront().setMouseTransparent(false);
			StartScreen.btns.get(4).setDisable(true);
			ResultsFiles.copyDirectory("temp", "results");
		});

		stagePopUpWindow.getCancelButton().setOnMouseClicked(() -> {
			stagePopUpWindow.close();
			StartScreen.mainWindow.getFront().setOpacity(1);
			StartScreen.mainWindow.getFront().setMouseTransparent(false);
			StartScreen.btns.get(4).setDisable(false);
		});
//		stagePopUpWindow.setOnCloseRequest(()->{
//			stagePopUpWindow.close();
//			StartScreen.mainWindow.getFront().setOpacity(1);
//			StartScreen.mainWindow.getFront().setMouseTransparent(false);
//			StartScreen.btns.get(4).setDisable(false);			
//		});
	}
}
