package popUps;

import enums.TypeOfPopUpWindow;
import language.LanguageClass;
import logics.CreateUser;
import screens.StartScreen;
import window.CustomPopUpWindow;

public class PleaseStoreResultsBeforeCreateNewUserPopUp {

	private CustomPopUpWindow popUp;

	public PleaseStoreResultsBeforeCreateNewUserPopUp() {
		popUp = new CustomPopUpWindow(TypeOfPopUpWindow.WARNING);
		int indexLang = StartScreen.indexLang;
		String yes = LanguageClass.word(indexLang, 15);
		String no = LanguageClass.word(indexLang, 16);
		String changeUserWithout = LanguageClass.word(indexLang, 54);

		popUp.setAlwaysOnTop(true);
		popUp.setText(changeUserWithout);
		popUp.getConfirmButton().setText(yes);
		popUp.setConfirmButtonOnAction(() -> {
			popUp.close();
		});

		popUp.getCancelButton().setText(no);
		popUp.setCancelButtonOnAction(() -> {
			popUp.close();
			new CreateUser();
		});

		popUp.setOnCloseRequest(() -> {
			popUp.close();
			StartScreen.mainWindow.disable(false);
			popUp = null;
		});
	}
}
