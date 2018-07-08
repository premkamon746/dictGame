import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyEvent;

public class Main {
    private JFrame frame = new JFrame("A JFrame");

    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);
    private JDialog alert = new JDialog();
    private MyDialog myDialog = new MyDialog(frame, "alert");
    JTextField jt = new JTextField();
    String asw = "";

    public static void main(String[] args)
    {
        Main m = new Main();
        m.createFrame();
    }

    private void createFrame()
    {

        //frame.setLayout(new BorderLayout());

        Connection con =  connect();
        asw = play(con);

        myDialog.addConfirmListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                asw = play(con);
                myDialog.setVisible(false);
            }
        });


        list.setLayoutOrientation(JList.VERTICAL);
        list.setCellRenderer(new DefaultListCellRenderer());
        list.setVisible(true);

        frame.add(list,BorderLayout.CENTER);

        JPanel panelBottom = new JPanel();
        Button next = new Button("Next >>");
        panelBottom.setLayout(new BoxLayout(panelBottom,BoxLayout.PAGE_AXIS));



        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                asw = play(con);
            }
        });

        jt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //do stuff here when enter pressed
                System.out.println(asw +" "+jt.getText());
                if(jt.getText().trim().equals(asw.trim()))
                {
                    myDialog.setTitle("ok");
                }
                else {
                    myDialog.setTitle("fail");
                }
                myDialog.setVisible(true);
            }
        });




        panelBottom.add(jt);
        panelBottom.add(next);

        frame.add(panelBottom,BorderLayout.PAGE_END);


        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> jt.requestFocus());

//        try {
//            con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    private String play(Connection con){
        model.clear();
        jt.setText("");
        ArrayList<String> q = getRandomWord(con);
        ArrayList<String> a = getChoice(con,q.get(1));

        model.addElement(q.get(0));
        for(int i = 0; i < a.size(); i++)
        {
            model.addElement(a.get(i));
        }
        return q.get(1);
    }


    private Connection connect()
    {
        Connection connection = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/home/prem/IdeaProjects/dictGame/database/dict.db");


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return connection;

    }

    private ArrayList<String> getRandomWord(Connection connection )
    {
        ArrayList<String> item = new ArrayList<String>();
        if(connection instanceof Connection)
        {
            ResultSet resultSet = null;
            Statement statement = null;

            String sql = "SELECT * FROM GAME  ORDER BY RANDOM() LIMIT 1";
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                while (resultSet.next())
                {
                    item.add("Question : "+resultSet.getString("thai"));
                    //System.out.print(resultSet.getString("thai"));
                    item.add(resultSet.getString("english"));
                }

                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                resultSet.close();
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    private ArrayList<String> getChoice(Connection connection,String answer )
    {
        ArrayList<String> item = new ArrayList<String>();

        if(connection instanceof Connection)
        {
            ResultSet resultSet = null;
            Statement statement = null;
            String sql = "SELECT * FROM GAME WHERE english != '" + answer + "' ORDER BY RANDOM() LIMIT 4";
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                Random rn = new Random();
                int min =1;
                int max = 4;
                int ran = rn.nextInt(max - min + 1) + min;
                int count = 0;

                while (resultSet.next())
                {

                    if(count == ran)
                    {
                        item.add(answer);

                    }
                    item.add(resultSet.getString("english"));
                    count++;
                }

                if(count == ran)
                {
                    item.add(answer);

                }

                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                resultSet.close();
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.out.print("can't connect");
        }

        return item;
    }
}


class MyDialog extends JDialog {
    private JButton confirmBtn = new JButton("Close");

    public MyDialog(JFrame frame, String title) {
        super(frame, title, false);
        JPanel panel = new JPanel();

        panel.add(confirmBtn);
        getRootPane().setDefaultButton(confirmBtn);

        confirmBtn.registerKeyboardAction(confirmBtn.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                JComponent.WHEN_FOCUSED);

        confirmBtn.registerKeyboardAction(confirmBtn.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                JComponent.WHEN_FOCUSED);



        add(panel);
        pack();
        setLocationRelativeTo(frame);

        SwingUtilities.invokeLater(() -> confirmBtn.requestFocusInWindow());
    }



    public void addConfirmListener(ActionListener listener) {
        confirmBtn.addActionListener(listener);
    }
}

