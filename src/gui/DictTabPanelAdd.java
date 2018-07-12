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
    MyDialog alertDialog;
    final DictProcessAdd addProcess;

    JPanel addDict = new JPanel();

    public DictTabPanelAdd(JFrame jf)
    {
        this.jf = jf;
        alertDialog = new MyDialog(jf,"message");

        table = new JTable();
        table.setModel(model);

        setUpUi();
        addChild();

        addProcess = new DictProcessAdd(jf);
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

    private void setUpUi()
    {
       setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       search.setMaximumSize( search.getPreferredSize() );

        SpringLayout layout = new SpringLayout();
        //addDict.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        addDict.setLayout(layout);

        //addDict.setBorder(BorderFactory.createTitledBorder("Add New"));

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


        JLabel exSent = new JLabel("Ex Sent: ");
        JTextField exSentText = new JTextField(40);
        addDict.add(exSent);
        addDict.add(exSentText);
        layout.putConstraint(SpringLayout.NORTH, exSent, 25, SpringLayout.SOUTH, grpLabel);
        layout.putConstraint(SpringLayout.WEST, exSent, 25, SpringLayout.WEST, addDict);

        layout.putConstraint(SpringLayout.NORTH, exSentText, 25, SpringLayout.SOUTH, grpLabel);
        layout.putConstraint(SpringLayout.WEST, exSentText, 50,  SpringLayout.EAST, thaiLabel);


        JButton submit = new JButton("save");
        addDict.add(submit);

        layout.putConstraint(SpringLayout.NORTH, submit, 25, SpringLayout.SOUTH, exSent);
        layout.putConstraint(SpringLayout.WEST, submit, 25, SpringLayout.WEST, addDict);

        final JLabel message = new JLabel();
        addDict.add(message);

        layout.putConstraint(SpringLayout.NORTH, message, 25, SpringLayout.SOUTH, grpText);
        layout.putConstraint(SpringLayout.WEST, message, 50,  SpringLayout.EAST, thaiLabel);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eng      = engText.getText();
                String thai     = thaiText.getText();
                String grp      = grpText.getText();
                String sent      = exSentText.getText();
                System.out.print(eng+thai+grp);
                message.setText("");
                if(eng.isEmpty() || thai.isEmpty()){
                    message.setText("thai and english can't not be empty.");
                    message.setForeground(Color.RED);
                }else{
                    DictDatabase db = new DictDatabase();
                    try
                    {
                        db.saveDict(thai, eng, grp, sent);
                        model.setRowCount(0);
                        model.setColumnCount(0);
                        addProcess.process(model, search.getText());
                    }
                    catch (Exception excp)
                    {
                        alertDialog.setVisible(true);
                    }
                }
            }
        });



    }

    private void addChild(){
        add(search);
        add(table);
        add(addDict);
    }

}
