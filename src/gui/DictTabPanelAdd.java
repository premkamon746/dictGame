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
    JFrame jf;

    JPanel addDict = new JPanel();

    public DictTabPanelAdd(JFrame jf)
    {
        this.jf = jf;


        table = new JTable();
        table.setModel(model);

        setUpUi();
        addChild();

        final DictProcessAdd addProcess = new DictProcessAdd(jf);
        search.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                model.setRowCount(0);
                model.setColumnCount(0);
                addProcess.process(model, search.getText());
            }
        });
        model.setRowCount(0);
        model.setColumnCount(0);
        addProcess.process(model, search.getText());
    }

    private void setUpUi(){
       setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       search.setMaximumSize( search.getPreferredSize() );

        SpringLayout layout = new SpringLayout();
        addDict.setLayout(layout);

        JLabel thaiLabel = new JLabel("Thai: ");
        JTextField thaiText = new JTextField(40);
        addDict.add(thaiLabel);
        addDict.add(thaiText);
        layout.putConstraint(SpringLayout.WEST, thaiLabel, 25, SpringLayout.WEST, addDict);
        layout.putConstraint(SpringLayout.WEST, thaiText, 50,  SpringLayout.EAST, thaiLabel);


        JLabel engLabel = new JLabel("English: ");
        JTextField engText = new JTextField(40);
        addDict.add(engLabel);
        addDict.add(engText);
        layout.putConstraint(SpringLayout.NORTH, engLabel, 25, SpringLayout.SOUTH, thaiLabel);
        layout.putConstraint(SpringLayout.WEST, engLabel, 25, SpringLayout.WEST, addDict);

        layout.putConstraint(SpringLayout.NORTH, engText, 25, SpringLayout.SOUTH, thaiLabel);
        layout.putConstraint(SpringLayout.WEST, engText, 50,  SpringLayout.EAST, thaiLabel);


        JLabel grpLabel = new JLabel("Group: ");
        JTextField grpText = new JTextField(40);
        addDict.add(grpLabel);
        addDict.add(grpText);
        layout.putConstraint(SpringLayout.NORTH, grpLabel, 25, SpringLayout.SOUTH, engLabel);
        layout.putConstraint(SpringLayout.WEST, grpLabel, 25, SpringLayout.WEST, addDict);

        layout.putConstraint(SpringLayout.NORTH, grpText, 25, SpringLayout.SOUTH, engLabel);
        layout.putConstraint(SpringLayout.WEST, grpText, 50,  SpringLayout.EAST, thaiLabel);


    }

    private void addChild(){
        add(search);
        add(table);
        add(addDict);
    }

}
