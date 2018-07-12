package gui;

import process.DictProcessAdd;
import process.DictProcessChoose;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

    DefaultListModel<String> modelSelect = new DefaultListModel<>();
    JList<String> listSelect = new JList<>(modelSelect);
    private Vector<String> vSelectList;

    public DictTabPanelChoose(JFrame jf,Vector<String> vSelectList )
    {
        this.jf = jf;
        this.vSelectList = vSelectList;
        setUpUi();
        addChild();

        final DictProcessChoose addProcess = new DictProcessChoose(jf);
        search.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                list.clearSelection();
                addProcess.process(model, search.getText());
            }
        });
        addProcess.process(model, search.getText());

        list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    vSelectList.add(list.getSelectedValue().toString());
                    addItemToSelectedList();
                }
            }
        });


        listSelect.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    int selectIdx = listSelect.getSelectedIndex();
                    if (selectIdx != -1)
                    {
                        vSelectList.remove(selectIdx);
                        addItemToSelectedList();
                    }
                }
            }
        });
    }

    private void addItemToSelectedList()
    {
        modelSelect.clear();
        for(int i = 0; i < vSelectList.size(); i++)
        {
            modelSelect.addElement(vSelectList.get(i));
        }
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

        add(new JLabel("choose from : "));
        add(jsp);

        add(new JLabel("selected : "));
        add(listSelect);
    }


}
