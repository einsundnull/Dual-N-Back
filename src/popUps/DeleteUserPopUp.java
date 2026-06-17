package popUps;

import java.awt.Color;

import enums.TypeOfPopUpWindow;
import files.ResultsFiles;
import language.LanguageClass;
import logics.SelectUser;
import screens.StartScreen;
import window.CustomPopUpWindow;

public class DeleteUserPopUp {

	private CustomPopUpWindow popUp;

	public DeleteUserPopUp() {
		popUp = new CustomPopUpWindow(TypeOfPopUpWindow.WARNING);
		int indexLang = StartScreen.indexLang;
		String yes = LanguageClass.word(indexLang, 15);
		String no = LanguageClass.word(indexLang, 16);
		String deleteUser = LanguageClass.word(indexLang, 50);
		SelectUser.drop.disable(true);
		popUp.setAlwaysOnTop(true);
		popUp.setText(deleteUser);
		popUp.allowToCloseByPressingESC(true);
		popUp.getConfirmButton().setText(yes);

		popUp.setConfirmButtonOnAction(() -> {

				ResultsFiles.deleteUser();
				SelectUser.scroll.getDisplay().getChildren().clear();
				SelectUser.setUserNameBtnsAndDeleteButtons();
				if (StartScreen.userToDelete.equals(StartScreen.user)) {
					StartScreen.user = null;
					StartScreen.lbls.get(0).setText("");
					StartScreen.btns.get(3).setDisable(true);
					StartScreen.btns.get(4).getFront().setBackground(Color.WHITE);
				}
				StartScreen.btns.get(2).setDisable(true);
				SelectUser.drop.disable(false);
				popUp.close();
//				SelectUser.setUserNameBtnsAndDeleteButtons();

		});

		popUp.getCancelButton().setText(no);
		popUp.setCancelButtonOnAction(() -> {
			SelectUser.drop.disable(false);
			popUp.close();
		});

		popUp.setOnCloseRequest(() -> {
			SelectUser.drop.disable(false);
			popUp.close();
		});
	}
	
	public boolean isVisible(){
		return popUp.isVisible();
	}

}
