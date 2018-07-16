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
    int total = 0;
    int score = 0;
    JLabel scoreBoard;
    JComboBox lev;

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

    public void process(DefaultListModel<String> model, JList<String> list, JTextField jt, Button next,JLabel scoreBoard,JComboBox lev)
    {
        this.model  = model;
        this.list   = list;
        this.jt     = jt;
        this.next   = next;
        this.scoreBoard = scoreBoard;
        this.lev    = lev;
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
                total++;
                String display = "";
                if(jt.getText().trim().equals(qs.getAnswer().trim()))
                {
                    score++;
                    display += "Score : "+score+ " / " +total;
                    //myDialog.setTitle("ok "+score+" / "+total);
                }else{
                    display += "Score : "+score+ " / " +total;
                    display += " prev answer : "+qs.getAnswer().trim();
                }
                scoreBoard.setText(display);
                addDataTolist();

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
                        total++;
                        String display = "";
                        if(content.equals(qs.getAnswer()))
                        {
                            score++;
                            display += "Score : "+score+ " / " +total;
                            //myDialog.setTitle("ok "+score+" / "+total);
                        }else{
                            display += "Score : "+score+ " / " +total;
                            display += " prev answer : "+qs.getAnswer().trim();
                        }

                        //myDialog.setVisible(true);
                        scoreBoard.setText(display);
                        addDataTolist();

                    }
                    catch (NullPointerException e)
                    {

                    }

                }
            }
        });

    }

    private void displayScore(){

    }

    //get new question
    public void addDataTolist()
    {

        qs = db.getData(vSelectList,lev.getSelectedIndex());
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
