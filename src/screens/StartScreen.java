package screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import arrays.CustomButtonArray;
import arrays.LabelArray;
import arrays.PaneArray;
import arrays.TextFieldArray;
import enums.TypeOfWindow;
import files.FilesCls;
import files.ResultsFiles;
import language.LanguageClass;
import lists.Lists;
import logics.CreateUser;
import logics.DurationTraining;
import logics.Results;
import logics.SelectUser;
import popUps.ChangeCheckBoxWithoutSavingPopUp;
import popUps.ChangeUserWithoutSavingPopUp;
import popUps.ExitWithoutSavingResultsPopUp;
import popUps.PleaseStoreResultsBeforeCreateNewUserPopUp;
import popUps.StoreResultsPopUp;
import training.AudioClass;
import training.Intro;
import training.Rect;
import training.TimerClass;
import window.CustomCheckBox;
import window.CustomTimer;
import window.CustomWindow;
import window.SwingLabel;
import window.SwingPanel;

public class StartScreen {

    // --- State ---
    public static boolean trainingStarted     = false;
    public static boolean createUser;
    public static boolean testMode;
    public static boolean keepLengthOfTrialsConstant;
    private static boolean play;
    public static boolean durationTraining    = false;
    public static boolean includeAudio        = true;
    public static boolean includeColor        = false;
    public static boolean includePosition     = true;
    public static boolean setProgressBarVisible = true;

    // --- UI components ---
    public static CustomButtonArray btns;
    public static CustomCheckBox ckbxAudio;
    public static CustomCheckBox ckbxPos;
    public static CustomCheckBox ckbxCol;
    public static CustomCheckBox ckbxDurtionTraining;
    public static int checkBoxIndex = 4;

    public static OptionMenu options;
    public static CustomWindow mainWindow;

    public static TextFieldArray flds;
    public static LabelArray     lbls;
    public static SwingLabel     lblSelectUser   = new SwingLabel();
    public static SwingLabel     speed;
    public static SwingLabel     versionAndUpdate;
    public static SwingLabel     lblUserFilePath;

    public static SelectUser selectUser;

    // --- Dashboard containers ---
    private static SwingPanel sidebar, content, modusCard, paramCard;
    private static SwingLabel brand, headerLbl, sectionModus, sectionParam, lblUnit, lblSprache;

    // --- Dimensions ---
    public static int    indexLang;
    public static int    durationInMinutes;
    public static int    sessionLengthInMinutes;
    public static int    trials;
    public static int    additionalDuration;
    public static int    sessions;
    public static double prefHeight, prefWidth;
    public static double screenHeight, screenWidth;

    // --- Settings strings ---
    public static String dateOfLastUse, directoryAddition, fileAddition;
    public static String positionCheckBoxText, colorCheckBoxText;
    public static String OverwriteData, actualDate, modePath;
    public static String user, userExists, userToDelete, versionsNumber;

    private static String CreateUserTxt, SelectUserTxt, StartTrainingTxt;
    private static String ShowResultsTxt, StoreResultsTxt, TestVolumeTxt;
    private static String ReadManualTxt, LevelTxt, TimeTxt, UserITxt;

    private final String appVersion = "2021.04.14";

    private static PaneArray paneLngI;
    private SwingPanel menu;
    private static final int[] flagLang = { 14, 4, 10, 8, 15, 5 };   // pane index -> language index

    // --- Theme (light "dashboard") ---
    private static final String  FONT       = "Segoe UI";
    private static final Color COL_BG       = new Color(0xEE, 0xF1, 0xF5);
    private static final Color COL_SIDEBAR  = Color.WHITE;
    private static final Color COL_CARD     = Color.WHITE;
    private static final Color COL_BORDER   = new Color(0xE2, 0xE5, 0xEA);
    private static final Color COL_ACCENT   = new Color(0x2D, 0x7F, 0xF9);
    private static final Color COL_ACCENT_H = new Color(0x1F, 0x6F, 0xE5);
    private static final Color COL_TEXT     = new Color(0x1E, 0x24, 0x30);
    private static final Color COL_TEXT_DIM = new Color(0x6B, 0x72, 0x80);
    private static final Color COL_NAV_HOVER= new Color(0xF0, 0xF3, 0xF7);

    // -----------------------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartScreen().initUI());
    }

    private void initUI() {
        new FilesCls();
        FilesCls.createStartUpSettingsFile();
        FilesCls.fileStartUpSettingsGetValues();
        getScreenSize();

        prefWidth  = screenWidth  * 0.5 - 6;
        prefHeight = screenHeight * 0.6 - screenWidth * 0.02375;

        mainWindow = new CustomWindow(TypeOfWindow.MAINSTAGE,
                                      screenWidth * 0.6, screenHeight * 0.7);
        mainWindow.onlyAllowToSendToTray(true);
        mainWindow.getFront().setOpaque(true);
        mainWindow.getFront().setBackground(COL_BG);

        buildContainers();

        try {
            setButtons();
            setModeCheckBoxes();
            checkBoxIndex = 4;
            combinedCheckBoxLogic();
            setLanguageButtons();
            setLanguageMenu();
            setBtnOnAction();
            setLabels();
            setFields();
        } catch (Exception e) { e.printStackTrace(); }

        setRootOnKeyPressed();
        options = new OptionMenu();
        OptionMenu.setOptionMenu();
        Lists.setColorList();
        setLanguage(indexLang);

        if ("No".equals(userExists)) btns.get(0).setDisable(true);

        String path = System.getProperty("user.home") + File.separator + "n-back" + File.separator + "user";
        String[] list = new File(path).list();
        if (list == null || list.length < 1) btns.get(0).setDisable(true);

        mainWindow.getFront().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                mainWindow.getFront().requestFocusInWindow();
            }
        });

        mainWindow.setMaximized();
        mainWindow.show();

        // Reflow once the real window size is known, and on every resize.
        SwingUtilities.invokeLater(this::layoutDashboard);
        mainWindow.changeListener(this::layoutDashboard);

        // Play startup sound after 40 ms
        new CustomTimer(40, () -> {
            java.net.URL url = getClass().getResource("/sound/dingIII.wav");
            if (url != null) AudioClass.playAsync(url);
        });
    }

    // -----------------------------------------------------------------------
    private void buildContainers() {
        SwingPanel front = mainWindow.getFront();

        sidebar = new SwingPanel();
        sidebar.setOpaque(true);
        sidebar.setBackground(COL_SIDEBAR);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, COL_BORDER));
        front.add(sidebar);

        content = new SwingPanel();
        content.setOpaque(true);
        content.setBackground(COL_BG);
        front.add(content);

        brand = new SwingLabel("N-BACK");
        brand.setForeground(COL_ACCENT);
        brand.setFont(new Font(FONT, Font.BOLD, 22));
        sidebar.add(brand);

        lblSprache = new SwingLabel("Language");
        lblSprache.setForeground(COL_TEXT_DIM);
        lblSprache.setFont(new Font(FONT, Font.PLAIN, 11));
        sidebar.add(lblSprache);

        versionAndUpdate = new SwingLabel("v" + versionsNumber + "   " + appVersion);
        versionAndUpdate.setForeground(COL_TEXT_DIM);
        versionAndUpdate.setFont(new Font(FONT, Font.PLAIN, 11));
        sidebar.add(versionAndUpdate);

        headerLbl = new SwingLabel("");
        headerLbl.setForeground(COL_TEXT);
        headerLbl.setFont(new Font(FONT, Font.BOLD, 26));
        content.add(headerLbl);

        modusCard = makeCard();
        content.add(modusCard);
        paramCard = makeCard();
        content.add(paramCard);

        sectionModus = makeSection("MODUS");
        modusCard.add(sectionModus);
        sectionParam = makeSection("PARAMETER");
        paramCard.add(sectionParam);

        lblUnit = new SwingLabel("min");
        lblUnit.setForeground(COL_TEXT_DIM);
        lblUnit.setFont(new Font(FONT, Font.PLAIN, 14));
        paramCard.add(lblUnit);
    }

    private SwingPanel makeCard() {
        SwingPanel c = new SwingPanel();
        c.setOpaque(true);
        c.setBackground(COL_CARD);
        c.setBorder(BorderFactory.createLineBorder(COL_BORDER));
        return c;
    }

    private SwingLabel makeSection(String text) {
        SwingLabel l = new SwingLabel(text);
        l.setForeground(COL_TEXT_DIM);
        l.setFont(new Font(FONT, Font.BOLD, 12));
        return l;
    }

    // -----------------------------------------------------------------------
    // Single source of truth for all positions; runs on init and on resize.
    private void layoutDashboard() {
        if (sidebar == null) return;
        int W = mainWindow.getFront().getWidth();
        int H = mainWindow.getFront().getHeight();
        if (W <= 0 || H <= 0) { W = (int) screenWidth; H = (int) screenHeight; }

        int sw = Math.max(210, Math.min((int)(W * 0.20), 300));
        sidebar.setBounds(0, 0, sw, H);
        content.setBounds(sw, 0, W - sw, H);
        int cw = W - sw;

        // --- Sidebar ---
        brand.setBounds(24, 22, sw - 48, 30);
        lbls.get(0).setBounds(24, 60, sw - 48, 22);   // user label

        int navX = 14, navW = sw - 28, navH = 42, gap = 6, navY = 104;
        int[] navOrder = { 0, 1, 3, 4, 5, 6 };          // Select, Create, Results, Save, Test, Manual
        for (int k = 0; k < navOrder.length; k++) {
            btns.get(navOrder[k]).setBoundsDirect(navX, navY + k * (navH + gap), navW, navH);
        }

        // language flags (3 per row) + earth menu near bottom
        int flagsY = H - 150;
        lblSprache.setBounds(24, flagsY - 24, sw - 48, 18);
        int fw = 32, fh = 22, fgx = 10, fgy = 8;
        for (int i = 0; i < 6; i++) {
            int col = i % 3, row = i / 3;
            paneLngI.get(i).setBounds(24 + col * (fw + fgx), flagsY + row * (fh + fgy), fw, fh);
        }
        menu.setBounds(sw - 50, flagsY - 4, 30, 30);
        highlightActiveFlag();
        versionAndUpdate.setBounds(24, H - 30, sw - 48, 18);

        // --- Content ---
        headerLbl.setBounds(32, 22, cw - 64, 34);

        int cardX = 32, cardW = cw - 64;
        int modusH = 210;
        modusCard.setBounds(cardX, 72, cardW, modusH);
        sectionModus.setBounds(18, 14, 200, 18);
        ckbxAudio.setLayoutX(22);           ckbxAudio.setLayoutY(50);
        ckbxPos.setLayoutX(22);             ckbxPos.setLayoutY(90);
        ckbxCol.setLayoutX(22);             ckbxCol.setLayoutY(130);
        ckbxDurtionTraining.setLayoutX(22); ckbxDurtionTraining.setLayoutY(170);

        int paramY = 72 + modusH + 22, paramH = 150;
        paramCard.setBounds(cardX, paramY, cardW, paramH);
        sectionParam.setBounds(18, 14, 200, 18);
        lbls.get(1).setBounds(22, 52, 170, 28);            // Level label
        flds.get(0).setBounds(200, 50, 90, 34);
        lbls.get(2).setBounds(22, 98, 170, 28);            // Duration label
        flds.get(1).setBounds(200, 96, 90, 34);
        lblUnit.setBounds(298, 98, 60, 28);

        int startY = paramY + paramH + 26;
        int startW = Math.min(320, cardW);
        btns.get(2).setBoundsDirect(cardX, startY, startW, 52);

        lblUserFilePath.setBounds(32, H - 30, cw - 64, 20);

        sidebar.revalidate(); sidebar.repaint();
        content.revalidate(); content.repaint();
    }

    // -----------------------------------------------------------------------
    private void getScreenSize() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth  = screen.getWidth();
        screenHeight = screen.getHeight();
    }

    public static void disableButtons(boolean disable) {
        for (int i = 0; i < btns.size(); i++) btns.get(i).setDisable(disable);
    }

    // -----------------------------------------------------------------------
    private void setRootOnKeyPressed() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() != java.awt.event.KeyEvent.KEY_PRESSED) return false;
            int code = e.getKeyCode();

            if (code == java.awt.event.KeyEvent.VK_F1) {
                if (!btns.get(2).isDisable()) {
                    OptionMenu.drop.playAndBack();
                    OptionMenu.setAudioConfirmButton();
                    OptionMenu.setColorConfirmButton();
                    OptionMenu.setPositionConfirmButton();
                    FilesCls.storeUserSettings();
                    OptionMenu.setTextToConfirmButtonTextFields();
                }
            }
            if (code == java.awt.event.KeyEvent.VK_ESCAPE) {
                setLanguagesButtonsVisible(true, false);
                Intro.cancel();
            }
            if (code == java.awt.event.KeyEvent.VK_D) {
                switchFromRegularToDurationTraining(false);
            }
            if (code == java.awt.event.KeyEvent.VK_S) {
                OptionMenu.setRepetTimeToHighSpeed();
            }
            if (code == java.awt.event.KeyEvent.VK_DOWN) {
                double t = TimerClass.repeatTime;
                TimerClass.repeatTime = (int)(t - t * 0.05);
            }
            if (code == java.awt.event.KeyEvent.VK_UP) {
                double t = TimerClass.repeatTime;
                TimerClass.repeatTime = (int)(t + t * 0.05);
            }
            return false;
        });
    }

    // -----------------------------------------------------------------------
    public static void switchFromRegularToDurationTraining(boolean selectUserProcess) {
        if (!selectUserProcess) {
            durationTraining = !durationTraining;
        }
        if (ckbxDurtionTraining != null) ckbxDurtionTraining.setSelected(durationTraining);
    }

    private static void enableButtonsAfterChoosingLanguage() {
        btns.get(1).setDisable(false);
        btns.get(5).setDisable(false);
        btns.get(6).setDisable(false);
        if ("Yes".equals(userExists)) btns.get(0).setDisable(false);
    }

    public static SwingPanel getMainWindow() { return mainWindow.getFront(); }

    public static void getValuesFromTextfields() {
        String lbl = flds.get(0).getText().replaceAll("[^0-9]", "");
        flds.get(0).setText(lbl);
        Rect.nback = lbl.isEmpty() ? 1 : Integer.parseInt(lbl);

        String length = flds.get(1).getText().replaceAll("[^0-9]", "");
        durationInMinutes = length.isEmpty() ? 1 : Integer.parseInt(length);
        flds.get(1).setText(length);
        sessionLengthInMinutes = durationInMinutes;
        if (sessionLengthInMinutes % 2 != 0) sessionLengthInMinutes++;
    }

    public static void setLanguage(int idx) {
        CreateUserTxt    = LanguageClass.word(idx, 0);
        SelectUserTxt    = LanguageClass.word(idx, 1);
        StartTrainingTxt = LanguageClass.word(idx, 2);
        ShowResultsTxt   = LanguageClass.word(idx, 3);
        StoreResultsTxt  = LanguageClass.word(idx, 4);
        UserITxt         = LanguageClass.word(idx, 6);
        LevelTxt         = LanguageClass.word(idx, 8);
        TimeTxt          = LanguageClass.word(idx, 9);
        OverwriteData    = LanguageClass.word(idx, 17);
        TestVolumeTxt    = LanguageClass.word(idx, 27);
        ReadManualTxt    = LanguageClass.word(idx, 31);

        if (FilesCls.fileUserSettings == null) lbls.get(0).setText(UserITxt);

        // Mode checkboxes (i18n where keys exist; "Visual"=11, "Auditory"=12, "Long Time Training"=13)
        ckbxAudio.setText(LanguageClass.word(idx, 12));
        ckbxPos.setText(LanguageClass.word(idx, 11));
        ckbxCol.setText("Color");
        ckbxDurtionTraining.setText(LanguageClass.word(idx, 13));
        ckbxAudio.setTextWidth(300);
        ckbxCol.setTextWidth(300);
        ckbxPos.setTextWidth(300);

        lbls.get(1).setText(LevelTxt);
        lbls.get(2).setText(TimeTxt);
        btns.get(0).setText(SelectUserTxt);
        btns.get(1).setText(CreateUserTxt);
        btns.get(2).setText(StartTrainingTxt);
        btns.get(3).setText(ShowResultsTxt);
        btns.get(4).setText(StoreResultsTxt);
        btns.get(5).setText(TestVolumeTxt);
        btns.get(6).setText(ReadManualTxt);
        lbls.get(0).setText(LanguageClass.word(indexLang, 5) + user);
        if (headerLbl != null)  headerLbl.setText(StartTrainingTxt);

        enableButtonsAfterChoosingLanguage();
        highlightActiveFlag();
        new FilesCls();
        FilesCls.storeFileStartUpSettings();
    }

    // -----------------------------------------------------------------------
    private void setBtnOnAction() throws FileNotFoundException {
        btns.get(0).setOnMouseClicked(() -> {
            if (!btns.get(4).isDisable()) {
                new ChangeUserWithoutSavingPopUp();
            } else {
                if (selectUser == null) selectUser = new SelectUser();
                selectUser.getDrop().play();
            }
            mainWindow.disable(true);
        });

        btns.get(1).setOnMouseClicked(() -> {
            createUser = true;
            if (btns.get(4).isDisable()) new CreateUser();
            else new PleaseStoreResultsBeforeCreateNewUserPopUp();
        });

        btns.get(2).setOnMouseClicked(() -> {
            if (Results.includedModes > 0) {
                if (play) { play = false; btns.get(5).setText(TestVolumeTxt); }

                String level = flds.get(0).getText().replaceAll("[^0-9]", "");
                flds.get(0).setBackground(level.isEmpty() ? Color.RED : Color.WHITE);
                if (level.isEmpty() || level.equals("0")) flds.get(0).setText("1");

                String length = flds.get(1).getText().replaceAll("[^0-9]", "");
                flds.get(1).setBackground(length.isEmpty() ? Color.RED : Color.WHITE);
                if (length.isEmpty() || length.equals("0")) flds.get(1).setText("1");

                try {
                    flds.get(0).setBackground(Color.WHITE);
                    flds.get(1).setBackground(Color.WHITE);
                    FilesCls.storeUserSettings();
                    getValuesFromTextfields();
                    TimerClass.maxPresentations = DurationTraining.addaptMaxPresentationsToRepeatTime();
                    Results.determineMode();
                    new TrainingScreen();
                } catch (Exception ex) { ex.printStackTrace(); }
            }
        });

        btns.get(3).setOnMouseClicked(() -> {
            try { new ResultScreen(); } catch (Exception e) { e.printStackTrace(); }
        });

        btns.get(4).setOnMouseClicked(() -> new StoreResultsPopUp());

        btns.get(5).setOnMouseClicked(() -> {
            try {
                String lang = LanguageClass.word(indexLang, 28);
                java.net.URL url = AudioClass.class.getResource("/audio/" + lang + "9.mp3");
                if (!play && url != null) {
                    AudioClass.playAsync(url);
                    play = true;
                    btns.get(5).setText(LanguageClass.word(indexLang, 20));
                } else {
                    play = false;
                    btns.get(5).setText(TestVolumeTxt);
                }
            } catch (Exception e) { e.printStackTrace(); }
        });

        btns.get(6).setOnMouseClicked(() -> new ManualScreen());

        mainWindow.setExitButtonOnAction(() -> {
            if (!btns.get(4).isDisable()) new ExitWithoutSavingResultsPopUp();
            else System.exit(0);
        });
    }

    // -----------------------------------------------------------------------
    private void setButtons() throws FileNotFoundException {
        // Created in the sidebar; the Start button (index 2) is moved to the content area.
        btns = new CustomButtonArray(7, sidebar, 0, 0, 160, 42, 0, 0);
        content.add(btns.get(2).getFrame());

        int[] navOrder = { 0, 1, 3, 4, 5, 6 };
        for (int idx : navOrder) {
            btns.get(idx).setFlat(COL_SIDEBAR, COL_TEXT, COL_NAV_HOVER);
            btns.get(idx).setFontStyle(FONT, Font.PLAIN, 15);
            btns.get(idx).setTextAlignLeft();
        }
        // Primary call-to-action.
        btns.get(2).setFlat(COL_ACCENT, Color.WHITE, COL_ACCENT_H);
        btns.get(2).setFontStyle(FONT, Font.BOLD, 16);

        btns.get(2).setDisable(true);
        btns.get(3).setDisable(true);
        btns.get(4).setDisable(true);
        btns.get(6).setText(LanguageClass.word(indexLang, 31));
    }

    private void setFields() {
        flds = new TextFieldArray(2, paramCard, 0, 0,
                new Font(FONT, Font.PLAIN, 18), 90, 34, 0, 0);
        for (int i = 0; i < 2; i++) {
            flds.get(i).setHorizontalAlignment(JTextField.CENTER);
            flds.get(i).setBorder(BorderFactory.createLineBorder(COL_BORDER));
        }
        flds.get(0).setText("1");
        flds.get(0).setEnabled(false);
        Rect.nback = 1;
        flds.get(1).setText("20");
        flds.get(1).setEnabled(false);
    }

    private void setLabels() {
        lblUserFilePath = new SwingLabel();
        lblUserFilePath.setForeground(COL_TEXT_DIM);
        lblUserFilePath.setFont(new Font(FONT, Font.PLAIN, 12));
        content.add(lblUserFilePath);

        // 0 = user (sidebar), 1 = level label, 2 = duration label (param card)
        lbls = new LabelArray(3, paramCard, 0, 0,
                new Font(FONT, Font.PLAIN, 15), 200, 28, 0, 0);
        sidebar.add(lbls.get(0));   // re-parent user label from param card to sidebar
        lbls.get(0).setFont(new Font(FONT, Font.PLAIN, 14));
        lbls.get(0).setForeground(COL_TEXT_DIM);
        lbls.get(1).setForeground(COL_TEXT);
        lbls.get(2).setForeground(COL_TEXT);
    }

    // -----------------------------------------------------------------------
    private void setLanguageButtons() {
        paneLngI = new PaneArray(10, sidebar, 0, 0, null, 32, 22, 0, 0, true);

        addFlagButton(0, "/flag/jp.png", 14);
        addFlagButton(1, "/flag/am.png", 4);
        addFlagButton(2, "/flag/ge.png", 10);
        addFlagButton(3, "/flag/fr.png", 8);
        addFlagButton(4, "/flag/kr.png", 15);
        addFlagButton(5, "/flag/es.png", 5);
    }

    private void addFlagButton(int paneIndex, String resource, int langIndex) {
        java.net.URL url = getClass().getResource(resource);
        if (url == null) return;
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(32, 22, Image.SCALE_SMOOTH);
        JLabel lbl = new JLabel(new ImageIcon(img));
        lbl.setBounds(0, 0, 32, 22);
        SwingPanel pane = paneLngI.get(paneIndex);
        pane.add(lbl);
        pane.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        pane.setOnMouseClicked(() -> {
            indexLang = langIndex;
            setLanguage(indexLang);
            highlightActiveFlag();
        });
        // Hover affordance
        pane.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                pane.setBorder(BorderFactory.createLineBorder(COL_ACCENT, 2));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                highlightActiveFlag();
            }
        });
    }

    /** Outlines the flag of the currently selected language. */
    private static void highlightActiveFlag() {
        if (paneLngI == null) return;
        for (int i = 0; i < flagLang.length; i++) {
            paneLngI.get(i).setBorder(flagLang[i] == indexLang
                ? BorderFactory.createLineBorder(COL_ACCENT, 2)
                : BorderFactory.createLineBorder(COL_BORDER, 1));
        }
    }

    private void setLanguageMenu() {
        // Earth-icon toggle removed: language flags are always visible in the sidebar.
        menu = new SwingPanel();
        menu.setVisible(false);
        sidebar.add(menu);
    }

    private void setLanguagesButtonsVisible(boolean visMenu, boolean visLangBtns) {
        // Flags stay visible at all times now; parameters kept for call-site compatibility.
        for (int i = 0; i < 10; i++) paneLngI.get(i).setVisible(true);
    }

    // -----------------------------------------------------------------------
    private void setModeCheckBoxes() {
        double sz = 18;

        ckbxAudio          = new CustomCheckBox();
        ckbxCol            = new CustomCheckBox();
        ckbxPos            = new CustomCheckBox();
        ckbxDurtionTraining = new CustomCheckBox();

        for (CustomCheckBox cb : new CustomCheckBox[]{ckbxAudio, ckbxCol, ckbxPos, ckbxDurtionTraining}) {
            cb.setPrefSize(sz, sz);
            cb.setTextColor(COL_TEXT);
            cb.setTextFont(new Font(FONT, Font.PLAIN, 15));
        }

        ckbxAudio.setSelected(includeAudio);
        ckbxCol.setSelected(includeColor);
        ckbxPos.setSelected(includePosition);
        ckbxDurtionTraining.setSelected(durationTraining);

        ckbxAudio.setOnAction(() -> {
            checkBoxIndex = 0;
            if (!btns.get(4).isDisable()) new ChangeCheckBoxWithoutSavingPopUp();
            else combinedCheckBoxLogic();
        });
        ckbxCol.setOnAction(() -> {
            checkBoxIndex = 1;
            if (!btns.get(4).isDisable()) new ChangeCheckBoxWithoutSavingPopUp();
            else combinedCheckBoxLogic();
        });
        ckbxPos.setOnAction(() -> {
            checkBoxIndex = 2;
            if (!btns.get(4).isDisable()) new ChangeCheckBoxWithoutSavingPopUp();
            else combinedCheckBoxLogic();
        });
        ckbxDurtionTraining.setOnAction(() -> switchFromRegularToDurationTraining(false));

        modusCard.add(ckbxAudio.newCheckBox());
        modusCard.add(ckbxCol.newCheckBox());
        modusCard.add(ckbxPos.newCheckBox());
        modusCard.add(ckbxDurtionTraining.newCheckBox());
    }

    private static boolean switchMode(boolean mode) { return !mode; }

    public static void combinedCheckBoxLogic() {
        if (checkBoxIndex == 0) includeAudio    = switchMode(includeAudio);
        if (checkBoxIndex == 1) includeColor    = switchMode(includeColor);
        if (checkBoxIndex == 2) includePosition = switchMode(includePosition);

        ckbxAudio.setSelected(includeAudio);
        ckbxCol.setSelected(includeColor);
        ckbxPos.setSelected(includePosition);
        ckbxDurtionTraining.setSelected(durationTraining);

        btns.get(4).setDisable(true);
        mainWindow.getFront().setEnabled(true);
        ResultsFiles.initialiseStoringFilePaths();
        Results.countIncludedModes();
        Results.determineMode();
    }
}
