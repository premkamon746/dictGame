package gui;

import process.DictProcessPlay;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class DictTabPanelPlay extends JPanel
{
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);
    JTextField jt = new JTextField("",40);
    Button next = new Button("Next >>");
    JFrame jf;
    Vector<String>  vSelectList;


    public DictTabPanelPlay(JFrame jf, Vector<String>  vSelectList)
    {
        /////
        this.jf = jf;
        this.vSelectList = vSelectList;
        DictProcessPlay processPlay = new DictProcessPlay(jf, vSelectList);


        JPanel endPanel = new JPanel();
        endPanel.setLayout(new FlowLayout());

        endPanel.add(jt);
        endPanel.add(next);

        setLayout(new BorderLayout());
        add(list, BorderLayout.CENTER);
        add(endPanel, BorderLayout.PAGE_END);

        SwingUtilities.invokeLater(() -> jt.requestFocus());

        processPlay.process(model, list, jt, next);
    }

}
