package window;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import enums.TypeOfPopUpWindow;

/** Full Swing replacement for CustomPopUpWindow from CustomWindow.jar. */
public class CustomPopUpWindow {

    private final JDialog     dialog;
    private final JPanel      content;
    private final JLabel      textLabel;
    private JCheckBox         checkBox;
    private JTextField        inputField;
    private final CustomButton confirmBtn;
    private final CustomButton cancelBtn;
    private final CustomButton exitBtn;
    private Runnable closeRequestAction;

    public CustomPopUpWindow(TypeOfPopUpWindow type) {
        dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setModal(false);
        dialog.setSize(520, 320);
        dialog.setLocationRelativeTo(null);
        dialog.setAlwaysOnTop(true);

        content = new JPanel(null);
        content.setBackground(new Color(220, 220, 220));
        dialog.setContentPane(content);

        // Title bar / exit button
        exitBtn = new CustomButton(22, 22);
        exitBtn.setText("X");
        exitBtn.setLayout(496, 2);
        content.add(exitBtn.getFrame());
        exitBtn.setOnMouseClicked(() -> {
            if (closeRequestAction != null) closeRequestAction.run();
            else dialog.dispose();
        });

        textLabel = new JLabel();
        textLabel.setBounds(20, 30, 480, 160);
        textLabel.setVerticalAlignment(SwingConstants.TOP);
        content.add(textLabel);

        confirmBtn = new CustomButton(130, 32);
        confirmBtn.setText("OK");
        confirmBtn.setLayout(20, 260);
        content.add(confirmBtn.getFrame());

        cancelBtn = new CustomButton(130, 32);
        cancelBtn.setText("Cancel");
        cancelBtn.setLayout(370, 260);
        content.add(cancelBtn.getFrame());

        if (type == TypeOfPopUpWindow.CONFIRM) {
            checkBox = new JCheckBox();
            checkBox.setBounds(20, 200, 480, 30);
            content.add(checkBox);
        }
        if (type == TypeOfPopUpWindow.INPUT) {
            inputField = new JTextField();
            inputField.setBounds(20, 200, 480, 30);
            content.add(inputField);
        }

        dialog.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                if (closeRequestAction != null) closeRequestAction.run();
            }
        });

        dialog.setVisible(true);
    }

    // --- Text ---
    public void setText(String text) {
        textLabel.setText("<html>" + text.replace("\n", "<br>") + "</html>");
    }
    public void setCheckBoxText(String t)  { if (checkBox != null) checkBox.setText(t); }
    public void setCheckBoxPosition(double x, double y) {
        if (checkBox != null) checkBox.setBounds((int)x,(int)y,480,30);
    }
    public void setTextPositionY(double y) { textLabel.setBounds(20,(int)y,480,160); }
    public void setTextFieldText(String t) { if (inputField != null) inputField.setText(t); }

    // --- Config ---
    public void setAlwaysOnTop(boolean b)      { dialog.setAlwaysOnTop(b); }
    public void setOnMouseDragged(boolean b)   {}
    public void allowToCloseByPressingESC(boolean b) {
        if (b) {
            content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                   .put(KeyStroke.getKeyStroke("ESCAPE"), "close");
            content.getActionMap().put("close", new AbstractAction() {
                @Override public void actionPerformed(ActionEvent e) {
                    if (closeRequestAction != null) closeRequestAction.run();
                    else dialog.dispose();
                }
            });
        }
    }
    public void allowToCloseByCloseButton(boolean b) {}
    public void addSymbolsToConfirmButtons(boolean b) {}
    public void setPosition(int x, int y) { dialog.setLocation(x, y); }
    public void toFront() { dialog.toFront(); }

    // --- Lifecycle ---
    public void show()  { dialog.setVisible(true); }
    public void close() { dialog.dispose(); }
    public boolean isVisible() { return dialog.isVisible(); }

    // --- Enable/disable whole dialog ---
    public void disable(boolean d) {
        for (Component c : content.getComponents()) c.setEnabled(!d);
    }

    // --- Actions ---
    public void setOnCloseRequest(Runnable r)       { closeRequestAction = r; }
    public void setConfirmButtonOnAction(Runnable r) { confirmBtn.setOnMouseClicked(r); }
    public void setCancelButtonOnAction(Runnable r)  { cancelBtn.setOnMouseClicked(r); }

    // --- Accessors ---
    public CustomButton getConfirmButton() { return confirmBtn; }
    public CustomButton getCancelButton()  { return cancelBtn; }
    public CustomButton getExitButton()    { return exitBtn; }
    public JTextField   getTextField()     { return inputField; }
    public JDialog      getPrimaryStage()  { return dialog; }
}
