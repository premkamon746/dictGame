package gui;

import process.DictProcessAdd;
import process.DictProcessChoose;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class DictTabPanelChoose extends JPanel
{
    JTextField search = new JTextField(40);
    JTable table;
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);
    JFrame jf;

    public DictTabPanelChoose(JFrame jf)
    {
        this.jf = jf;
        setUpUi();
        addChild();

        final DictProcessChoose addProcess = new DictProcessChoose(jf);
        search.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                addProcess.process(model, search.getText());
            }
        });
        addProcess.process(model, search.getText());
    }

    private void setUpUi()
    {
        setSize(jf.getWidth(),jf.getHeight());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        search.setMaximumSize( search.getPreferredSize() );
    }

    private void addChild(){
        add(search);

        JScrollPane jsp = new JScrollPane(list);
        jsp.setBackground(Color.blue);
        jsp.setSize(getWidth(),getHeight());
        add(jsp);
    }


}
