package process;

import db.DictDatabase;
import gui.MyDialog;
import model.Question;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class DictProcessPlay {

    private DefaultListModel<String> model;
    private JList<String> list;
    private JTextField jt;
    private Button next;

    private Question qs = new Question();
    private DictDatabase db = new DictDatabase();
    JFrame jf;
    private MyDialog myDialog;
    Vector<String>  vSelectList;

    public DictProcessPlay(JFrame jf,Vector<String> vSelectList)
    {
        this.jf = jf;
        this.vSelectList = vSelectList;
        myDialog = new MyDialog(jf, "alert");
    }

    public void setThai(){
        db.setLang("thai");
    }

    public void setEnglish(){
        db.setLang("english");
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
                else
                {
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

        list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting())
                {
                    try {
                        String selectText = list.getSelectedValue().toString();
                        //String[] values = selectText.split("\\s+");

                        String content = selectText.substring(selectText.indexOf(' ') + 1);

                        //do stuff here when enter pressed
                        System.out.println(content+" "+qs.getAnswer());
                        if (content.trim().equals(qs.getAnswer())) {
                            myDialog.setTitle("ok");
                        } else {
                            myDialog.setTitle("fail");
                        }
                        myDialog.setVisible(true);

                    }
                    catch (NullPointerException e)
                    {

                    }

                }
            }
        });

    }

    //get new question
    public void addDataTolist()
    {
        qs = db.getData(vSelectList);
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
