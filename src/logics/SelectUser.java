package logics;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import enums.TypeOfDropMenu;
import enums.TypeOfScrollPane;
import files.FilesCls;
import files.ResultsFiles;
import popUps.DeleteUserPopUp;
import screens.OptionMenu;
import screens.ResultScreen;
import screens.StartScreen;
import window.CustomButton;
import window.CustomDropMenu2;
import window.CustomScrollPane;
import window.CustomWindow;

public class SelectUser {

    static double menuWidth  = CustomWindow.getFrontWidth() / 4.6;
    static double menuHeight = CustomWindow.getFrontHeight();

    public static CustomDropMenu2  drop;
    public static CustomScrollPane scroll;

    public SelectUser() {
        StartScreen.mainWindow.disable(true);
        setScrollMenuChooseUser();
    }

    private void setScrollMenuChooseUser() {
        drop   = new CustomDropMenu2(TypeOfDropMenu.FROM_LEFT, false);
        scroll = new CustomScrollPane(TypeOfScrollPane.VERTICALLY_RIGHT,
                                      menuWidth - StartScreen.screenWidth * 0.0125, menuHeight);
        drop.setPrefSize(menuWidth, menuHeight);
        drop.showCloseArrowAfterClosing(false);
        drop.showCloseArrowWhenDropMenuIsVisible(true);
        drop.add(scroll.newScrollPane());
        scroll.setBarVerticalBarVisible(true);

        drop.closeArrowSetOnAction(() -> {
            if (drop.isPlay()) {
                // menu is open → close it
                drop.back();
                StartScreen.mainWindow.disable(false);
            } else {
                // menu is closed → open it
                drop.play();
                StartScreen.mainWindow.disable(true);
            }
        });

        javax.swing.JPanel dropPanel = drop.newDropMenu();
        StartScreen.mainWindow.markAsOverlay(dropPanel);
        StartScreen.mainWindow.getOverLayer().add(dropPanel);
        // bring to z-order front so it renders on top of main UI
        StartScreen.mainWindow.getOverLayer().setComponentZOrder(dropPanel, 0);
        StartScreen.mainWindow.getOverLayer().revalidate();
        StartScreen.mainWindow.getOverLayer().repaint();
        setUserNameBtnsAndDeleteButtons();
    }

    public static void setUserNameBtnsAndDeleteButtons() {
        String path = System.getProperty("user.home") + File.separator
                    + "n-back" + File.separator + "user";
        File dir = new File(path);
        String[] list = dir.list();
        if (list == null) list = new String[0];

        ArrayList<CustomButton> selectBtns = new ArrayList<>();
        ArrayList<CustomButton> deleteBtns = new ArrayList<>();

        for (int i = 0; i < list.length; i++) {
            final String userId = list[i];

            // Select button
            CustomButton sel = new CustomButton(
                StartScreen.screenWidth * 0.14375,
                StartScreen.screenWidth * 0.01875);
            sel.setID(userId);
            sel.setText(userId.replace("_", " "));
            sel.setLayoutX(StartScreen.screenWidth * 0.04375);
            sel.setLayoutY(StartScreen.screenWidth * 0.01875 + i * StartScreen.screenWidth * 0.025);
            sel.setOnMouseClicked(() -> {
                StartScreen.user = userId.replace(" ", "_");
                selectUserProcess();
                drop.playAndBack();
                StartScreen.mainWindow.disable(false);
            });
            selectBtns.add(sel);
            scroll.add(sel.newButton());

            // Delete button with red-X icon
            CustomButton del = new CustomButton(
                StartScreen.screenWidth * 0.01875,
                StartScreen.screenWidth * 0.01875);
            del.setID(userId);
            URL iconUrl = SelectUser.class.getResource("/img/redCross.png");
            if (iconUrl != null) {
                int sz = (int)(StartScreen.screenWidth * 0.0125);
                ImageIcon icon = new ImageIcon(
                    new ImageIcon(iconUrl).getImage().getScaledInstance(sz, sz, Image.SCALE_SMOOTH));
                del.getFrame().add(new JLabel(icon));
            } else {
                del.setText("X");
            }
            del.setLayoutX(StartScreen.screenWidth * 0.01875);
            del.setLayoutY(StartScreen.screenWidth * 0.01875 + i * StartScreen.screenWidth * 0.025);
            del.setOnMouseClicked(() -> {
                StartScreen.userToDelete = del.getID();
                new DeleteUserPopUp();
            });
            deleteBtns.add(del);
            scroll.add(del.newButton());
        }

        double contentH = StartScreen.screenWidth * 0.08125 + StartScreen.screenWidth * 0.025 * list.length;
        scroll.setDisplayHeight(contentH > menuHeight ? contentH : menuHeight);

        if (list.length <= 0) {
            StartScreen.mainWindow.disable(false);
            StartScreen.btns.get(0).setDisable(true);
            drop.back();
        }
    }

    public static void selectUserProcess() {
        FilesCls.fileUserSettings = new File(
            System.getProperty("user.home") + File.separator + "n-back"
            + File.separator + "user" + File.separator + StartScreen.user);
        StartScreen.btns.get(4).setDisable(true);
        StartScreen.btns.get(2).setDisable(false);
        StartScreen.btns.get(3).setDisable(false);
        StartScreen.flds.get(1).setEnabled(true);
        StartScreen.flds.get(0).setEnabled(true);
        StartScreen.lbls.get(0).setText(StartScreen.user.replace("_", " "));
        ResultScreen.resetValues();
        StartScreen.btns.get(4).setStyle("-fx-background-color: white");
        FilesCls.setProgramPath();
        FilesCls.createDataDirectory();
        FilesCls.setAudioFilePath();
        FilesCls.loadAudioFiles();
        FilesCls.scanFileUserSettingsAndSetValuesToTextFields();
        FilesCls.storeUserSettings();
        StartScreen.getValuesFromTextfields();
        ResultsFiles.initialiseStoringFilePaths();
        ResultsFiles.copyDirectory("results", "temp");
        StartScreen.switchFromRegularToDurationTraining(true);
        StartScreen.combinedCheckBoxLogic();
        OptionMenu.setCheckBoxesChooseAudioFileSelected();
        OptionMenu.setCheckBoxesOptionsSelected();

        try { StartScreen.mainWindow.getFront().add(OptionMenu.drop.newDropMenu()); }
        catch (Exception ignored) {}
    }

    public CustomDropMenu2 getDrop() { return drop; }
}
