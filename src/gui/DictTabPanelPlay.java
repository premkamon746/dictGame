package gui;

import process.DictProcessPlay;

import javax.swing.*;
import java.awt.*;

public class DictTabPanelPlay extends JPanel
{
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);
    JTextField jt = new JTextField("",40);
    Button next = new Button("Next >>");

    public DictTabPanelPlay()
    {
        /////
        JPanel endPanel = new JPanel();
        endPanel.setLayout(new FlowLayout());

        endPanel.add(jt);
        endPanel.add(next);

        setLayout(new BorderLayout());
        add(list, BorderLayout.CENTER);
        add(endPanel, BorderLayout.PAGE_END);

        SwingUtilities.invokeLater(() -> jt.requestFocus());
    }

    public void doBusinessLogic(DictProcessPlay dv)
    {
        dv.process(model, list, jt, next);
    }

}
