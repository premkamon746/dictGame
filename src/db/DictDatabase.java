package db;

import model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class DictDatabase
{

    private Question qs = new Question();

    public DictDatabase(){

    }

    public Question getData(){
        Connection connect = connect();
        getRandomWord(connect);
        getChoice(connect, qs.getAnswer());
        return qs;
    }

    private Connection connect()
    {
        Connection connection = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/javaDictGame/database/dict.db");


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return connection;

    }

    public String[][] getSearch(String search)
    {
        String [][]b = new String[20][3];
        Connection connect  = connect();
        if(connect instanceof Connection)
        {
            ResultSet resultSet = null;
            Statement statement = null;

            String where = "";
            if(!search.isEmpty()) {
                where = "and ( thai like '%" + search + "%' " +
                        "or english like '%" + search + "%' " +
                        "or group_name like '%" + search + "%' )";
            }

            String sql = "SELECT * FROM GAME  WHERE 1=1 "+ where +" limit 20";

            try {
                statement = connect.createStatement();
                resultSet = statement.executeQuery(sql);

                int i = 0;
                while (resultSet.next())
                {
                    b[i][0] = resultSet.getString("thai");
                    b[i][1] = resultSet.getString("english");
                    b[i][2] = resultSet.getString("group_name");
                }

                statement = connect.createStatement();
                resultSet = statement.executeQuery(sql);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

            try {
                resultSet.close();
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    private void getRandomWord(Connection connection )
    {

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
                    String question = resultSet.getString("thai");
                    qs.setQuestion(question);
                    String answer = resultSet.getString("english");
                    qs.setAnswer(answer);
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
    }


    private void getChoice(Connection connection,String answer )
    {
        ArrayList<String> item = qs.getItem();
        item.clear();
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
    }
}
