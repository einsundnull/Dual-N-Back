package popUps;

import enums.TypeOfPopUpWindow;
import screens.StartScreen;
import window.CustomPopUpWindow;

public class ChangeCheckBoxWithoutSavingPopUp {

	String confirmText = "Yes";
	String cancelText = "No";

	CustomPopUpWindow popUp = new CustomPopUpWindow(TypeOfPopUpWindow.WARNING);

	public ChangeCheckBoxWithoutSavingPopUp() {

		StartScreen.mainWindow.getFront().setOpacity(0.7);
		StartScreen.mainWindow.getFront().setMouseTransparent(true);

		popUp.setText("Do you want to save your results befor changing mode? \n " + "If yes, all recent results will be deleted");
		popUp.getCancelButton().setText(cancelText);
		popUp.getConfirmButton().setText(confirmText);
		popUp.setOnCloseRequest(() -> {
			closeOrCancel();
		});

		popUp.getCancelButton().setOnMouseClicked(() -> {

			closeOrCancel();
		});

		popUp.getConfirmButton().setOnMouseClicked(() -> {

			StartScreen.combinedCheckBoxLogic();
			popUp.close();
		});
	}

	private void closeOrCancel() {
		StartScreen.mainWindow.getFront().setOpacity(1);
		StartScreen.mainWindow.getFront().setMouseTransparent(false);
		popUp.close();

	}


}
