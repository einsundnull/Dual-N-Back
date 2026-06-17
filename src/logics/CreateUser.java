package logics;

import java.awt.event.KeyEvent;
import java.io.File;

import enums.TypeOfPopUpWindow;
import files.FilesCls;
import files.ResultsFiles;
import language.LanguageClass;
import screens.OptionMenu;
import screens.ResultScreen;
import screens.StartScreen;
import training.TimerClass;
import window.CustomPopUpWindow;

public class CreateUser {

    private static String Cancel, Create, User, User_already_exists;
    public static String newUserChoose;

    private CustomPopUpWindow createUserInputPopUp;
    private CustomPopUpWindow warningPopUp;

    public static void getLanguage(int idx) {
        User                = LanguageClass.word(idx, 6);
        Cancel              = LanguageClass.word(idx, 19);
        Create              = LanguageClass.word(idx, 26);
        User_already_exists = LanguageClass.word(idx, 7);
    }

    public CreateUser() {
        getLanguage(StartScreen.indexLang);
        StartScreen.mainWindow.disable(true);

        createUserInputPopUp = new CustomPopUpWindow(TypeOfPopUpWindow.INPUT);
        createUserInputPopUp.setOnMouseDragged(false);
        createUserInputPopUp.setAlwaysOnTop(true);
        createUserInputPopUp.setTextFieldText(User);
        createUserInputPopUp.getCancelButton().setText(Cancel);
        createUserInputPopUp.getConfirmButton().setText(Create);

        createUserInputPopUp.setOnCloseRequest(() -> {
            createUserInputPopUp.close();
            StartScreen.mainWindow.disable(false);
        });
        createUserInputPopUp.getExitButton().setOnMouseClicked(() -> {
            createUserInputPopUp.close();
            StartScreen.mainWindow.disable(false);
        });

        // ESC via key dispatcher
        java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                createUserInputPopUp.close();
                StartScreen.mainWindow.disable(false);
            }
            return false;
        });

        createUserInputPopUp.setConfirmButtonOnAction(() -> {
            String temp = createUserInputPopUp.getTextField().getText().replace(" ", "_");
            StartScreen.user = temp;
            checkWhetherUserAlreadyExists();
        });

        createUserInputPopUp.setCancelButtonOnAction(() -> {
            createUserInputPopUp.close();
            StartScreen.mainWindow.disable(false);
        });
    }

    private void checkWhetherUserAlreadyExists() {
        String user = StartScreen.user.replace(" ", "_");
        File userFile = new File(System.getProperty("user.home") + File.separator
                                 + "n-back" + File.separator + "user" + File.separator + user);
        if (userFile.exists()) {
            showAlreadyExistsWarning();
        } else {
            createUserLogic();
            StartScreen.mainWindow.disable(false);
            createUserInputPopUp.close();
        }
    }

    private void showAlreadyExistsWarning() {
        createUserInputPopUp.disable(true);
        warningPopUp = new CustomPopUpWindow(TypeOfPopUpWindow.WARNING);
        warningPopUp.setPosition(630, 250);
        warningPopUp.setText(User_already_exists);
        warningPopUp.getConfirmButton().setText(Create);
        warningPopUp.getCancelButton().setText(Cancel);
        warningPopUp.setAlwaysOnTop(true);
        warningPopUp.toFront();

        warningPopUp.setConfirmButtonOnAction(() -> {
            StartScreen.userToDelete = createUserInputPopUp.getTextField().getText().replace(" ", "_");
            ResultsFiles.deleteUser();
            createUserLogic();
            warningPopUp.close();
            createUserInputPopUp.close();
            StartScreen.mainWindow.disable(false);
        });
        warningPopUp.setCancelButtonOnAction(() -> {
            warningPopUp.close();
            createUserInputPopUp.disable(false);
            createUserInputPopUp.setAlwaysOnTop(true);
            StartScreen.createUser = false;
        });
        warningPopUp.setOnCloseRequest(() -> {
            warningPopUp.close();
            createUserInputPopUp.disable(false);
            createUserInputPopUp.setAlwaysOnTop(true);
            StartScreen.createUser = false;
        });
    }

    private void createUserLogic() {
        StartScreen.flds.get(0).setEnabled(true);
        StartScreen.flds.get(1).setEnabled(true);
        StartScreen.setLanguage(StartScreen.indexLang);
        StartScreen.lbls.get(0).setText(StartScreen.user.replace("_", " "));
        StartScreen.flds.get(0).setText("1");
        StartScreen.flds.get(1).setText("20");
        StartScreen.directoryAddition = "regularTraining";
        StartScreen.fileAddition      = "positionAndSound";
        StartScreen.ckbxAudio.setSelected(true);
        StartScreen.ckbxCol.setSelected(false);
        StartScreen.ckbxPos.setSelected(true);
        StartScreen.includeAudio    = true;
        StartScreen.includeColor    = false;
        StartScreen.includePosition = true;
        StartScreen.createUser  = false;
        StartScreen.userExists  = "Yes";
        TimerClass.repeatTime   = 950;

        FilesCls.storeFileStartUpSettings();
        FilesCls.createDataDirectory();
        FilesCls.setProgramPath();
        FilesCls.setAudioFilePath();
        ResultsFiles.initialiseStoringFilePaths();
        FilesCls.createAudioFileArray();
        FilesCls.createCheckBoxesChooseAudioFileBooleanArray();
        FilesCls.writeAudioArrayFile();
        FilesCls.createFileUserSettings();
        FilesCls.scanFileUserSettingsAndSetValuesToTextFields();
        FilesCls.storeUserSettings();
        StartScreen.getValuesFromTextfields();

        StartScreen.flds.get(0).setEnabled(true);
        StartScreen.flds.get(1).setEnabled(true);
        StartScreen.btns.get(0).setDisable(false);
        StartScreen.btns.get(2).setDisable(false);
        StartScreen.btns.get(3).setDisable(false);
        StartScreen.btns.get(4).setDisable(true);
        StartScreen.btns.get(5).setDisable(false);
        StartScreen.btns.get(4).setStyle("-fx-background-color: white");

        ResultScreen.resetValues();

        try {
            SelectUser.scroll.getDisplay().getChildren().clear();
            SelectUser.setUserNameBtnsAndDeleteButtons();
        } catch (Exception ignored) {}

        StartScreen.options = new OptionMenu();
        StartScreen.options.setOptionMenu();
        try {
            StartScreen.mainWindow.getFront().add(StartScreen.options.drop.newDropMenu());
        } catch (Exception ignored) {}
    }
}
