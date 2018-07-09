package gui;

import db.DictDatabase;
import process.DictProcessAdd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DictTabPanelAdd extends JPanel
{

    JTextField search = new JTextField(40);
    JTable table;
    DefaultTableModel model = new DefaultTableModel();

    public DictTabPanelAdd()
    {
        String []header = {"Thai","English","Group"};
        table = new JTable(null,header);
        table.setModel(model);
        add(search);
        add(new JScrollPane(table));

        search.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {

            }
        });
    }

    public void doBusinessLogic(DictProcessAdd da)
    {
        da.process(model, search.getText());

    }
}
