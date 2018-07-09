package gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MyDialog extends JDialog {
    private JButton confirmBtn = new JButton("Close");

    public MyDialog(JFrame frame, String title) {
        super(frame, title, false);
        JPanel panel = new JPanel();

        panel.add(confirmBtn);
        getRootPane().setDefaultButton(confirmBtn);

        confirmBtn.registerKeyboardAction(confirmBtn.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                JComponent.WHEN_FOCUSED);

        confirmBtn.registerKeyboardAction(confirmBtn.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                JComponent.WHEN_FOCUSED);



        add(panel);
        pack();
        setLocationRelativeTo(frame);

        SwingUtilities.invokeLater(() -> confirmBtn.requestFocusInWindow());
    }



    public void addConfirmListener(ActionListener listener) {
        confirmBtn.addActionListener(listener);
    }
}
