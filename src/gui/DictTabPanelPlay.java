package gui;

import process.DictProcessPlay;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class DictTabPanelPlay extends JPanel
{
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);
    JTextField jt = new JTextField("",20);
    Button next = new Button("Next >>");
    JFrame jf;
    Vector<String>  vSelectList;
    JLabel jb = new JLabel();
    JToggleButton tgBtn = new JToggleButton("Thai / English");


    public DictTabPanelPlay(JFrame jf, Vector<String>  vSelectList)
    {
        /////
        this.jf = jf;
        this.vSelectList = vSelectList;
        final DictProcessPlay processPlay = new DictProcessPlay(jf, vSelectList);


        JPanel endPanel = new JPanel();
        endPanel.setLayout(new FlowLayout());

        jb.setText("Thai");
        tgBtn.setSelected(true);
        endPanel.add(jb);
        endPanel.add(tgBtn);
        endPanel.add(jt);
        endPanel.add(next);

        setLayout(new BorderLayout());
        add(list, BorderLayout.CENTER);
        add(endPanel, BorderLayout.PAGE_END);

        SwingUtilities.invokeLater(() -> jt.requestFocus());

        processPlay.process(model, list, jt, next);

        tgBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tgBtn.isSelected()) {
                    // State : on
                    processPlay.setThai();
                    jb.setText("thai");
                } else {
                    //State : off
                    processPlay.setEnglish();
                    jb.setText("english");
                }
            }
        });


    }

}
