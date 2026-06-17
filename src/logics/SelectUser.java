package logics;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import enums.TypeOfDropMenu;
import enums.TypeOfScrollPane;
import files.FilesCls;
import files.ResultsFiles;
import language.LanguageClass;
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
                                      menuWidth, menuHeight);
        drop.setPrefSize(menuWidth, menuHeight);
        drop.setTitle(LanguageClass.word(StartScreen.indexLang, 1));
        drop.showCloseArrowAfterClosing(false);
        drop.showCloseArrowWhenDropMenuIsVisible(true);
        drop.add(scroll.newScrollPane());
        scroll.setBarVerticalBarVisible(false);

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

    // Modern row palette
    private static final String FONT       = "Segoe UI";
    private static final Color  ROW_BG     = Color.WHITE;
    private static final Color  ROW_TEXT   = new Color(0x1E, 0x24, 0x30);
    private static final Color  ROW_HOVER  = new Color(0xF0, 0xF3, 0xF7);
    private static final Color  DEL_TEXT   = new Color(0xD0, 0x40, 0x40);
    private static final Color  DEL_HOVER  = new Color(0xFC, 0xEC, 0xEC);

    public static void setUserNameBtnsAndDeleteButtons() {
        String path = System.getProperty("user.home") + File.separator
                    + "n-back" + File.separator + "user";
        File dir = new File(path);
        String[] list = dir.list();
        if (list == null) list = new String[0];

        int W    = (int) menuWidth;
        int pad  = 12, rowH = 46, gap = 6, delW = 40;
        int selW = W - pad * 2 - delW - 6;
        int y    = 10;

        for (int i = 0; i < list.length; i++) {
            final String userId = list[i];

            // Select row (flat, left-aligned, hover)
            CustomButton sel = new CustomButton(selW, rowH);
            sel.setID(userId);
            sel.setText(userId.replace("_", " "));
            sel.setFlat(ROW_BG, ROW_TEXT, ROW_HOVER);
            sel.setFontStyle(FONT, Font.PLAIN, 15);
            sel.setTextAlignLeft();
            sel.setLayoutX(pad);
            sel.setLayoutY(y);
            sel.setOnMouseClicked(() -> {
                StartScreen.user = userId.replace(" ", "_");
                selectUserProcess();
                drop.playAndBack();
                StartScreen.mainWindow.disable(false);
            });
            scroll.add(sel.newButton());

            // Delete button (✕)
            CustomButton del = new CustomButton(delW, rowH);
            del.setID(userId);
            del.setText("✕");
            del.setFlat(ROW_BG, DEL_TEXT, DEL_HOVER);
            del.setFontStyle(FONT, Font.BOLD, 15);
            del.setLayoutX(W - pad - delW);
            del.setLayoutY(y);
            del.setOnMouseClicked(() -> {
                StartScreen.userToDelete = del.getID();
                new DeleteUserPopUp();
            });
            scroll.add(del.newButton());

            y += rowH + gap;
        }

        double contentH = y + 10;
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
