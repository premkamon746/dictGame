package process;

import db.DictDatabase;
import gui.MyDialog;
import model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DictProcessPlay {

    private DefaultListModel<String> model;
    private JList<String> list;
    private JTextField jt;
    private Button next;

    private Question qs = new Question();
    private DictDatabase db = new DictDatabase();
    JFrame jf;
    private MyDialog myDialog;

    public DictProcessPlay(JFrame jf)
    {
        this.jf = jf;
        myDialog = new MyDialog(jf, "alert");
    }

    public void process(DefaultListModel<String> model, JList<String> list, JTextField jt, Button next)
    {
        this.model  = model;
        this.list   = list;
        this.jt     = jt;
        this.next   = next;
        addDataTolist();

        next.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                addDataTolist();//get new question
            }
        });

        jt.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                //do stuff here when enter pressed
                if(jt.getText().trim().equals(qs.getAnswer()))
                {
                    myDialog.setTitle("ok");
                }
                else {
                    myDialog.setTitle("fail");
                }
                myDialog.setVisible(true);
            }
        });

        myDialog.addConfirmListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addDataTolist();//get new question
                myDialog.setVisible(false);
            }
        });

    }

    //get new question
    private void addDataTolist()
    {
        qs = db.getData();
        model.clear();
        jt.setText("");
        ArrayList<String> choice  = qs.getItem();
        String qest               = qs.getQuestion();

        model.addElement(qest);
        for(int i = 0; i < choice.size(); i++)
        {
            model.addElement(i+1+". "+choice.get(i));
        }
    }
}
