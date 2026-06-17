package screens;

import javax.swing.JLabel;

import window.SwingPanel;

/** Stub: result-filter checkbox menu (full port deferred). */
public class CheckBoxMenuFromResultsFile {

    private final SwingPanel pane;

    public CheckBoxMenuFromResultsFile(String fileAddition) {
        pane = new SwingPanel();
        pane.setBounds(0, 0, 300, 40);
        JLabel lbl = new JLabel("Results: " + fileAddition);
        lbl.setBounds(5, 5, 280, 30);
        pane.add(lbl);
    }

    public SwingPanel getPane() { return pane; }
}
