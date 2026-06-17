package popUps;

import java.awt.Color;

import enums.TypeOfPopUpWindow;
import language.LanguageClass;
import logics.SelectUser;
import screens.StartScreen;
import window.CustomPopUpWindow;

public class ChangeUserWithoutSavingPopUp {

	private CustomPopUpWindow popUp = new CustomPopUpWindow(TypeOfPopUpWindow.WARNING);

	public ChangeUserWithoutSavingPopUp() {
		popUp.show();
		int indexLang = StartScreen.indexLang;
		String yes = LanguageClass.word(indexLang, 15);
		String no = LanguageClass.word(indexLang, 16);
		String changeUserWithout = LanguageClass.word(indexLang, 14);

		popUp.setAlwaysOnTop(true);
		popUp.setText(changeUserWithout);
		popUp.getConfirmButton().setText(yes);

		popUp.setConfirmButtonOnAction(() -> {
			if (StartScreen.createUser == false) {

				if (StartScreen.selectUser == null) {
					StartScreen.selectUser = new SelectUser();
					StartScreen.selectUser.getDrop().play();
				} else {
					StartScreen.selectUser.getDrop().play();
				}
				if (StartScreen.btns.get(4).isDisable() == true)
					StartScreen.btns.get(4).getFront().setBackground(Color.WHITE);
			}
			popUp.close();

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
