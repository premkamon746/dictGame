package db;

import model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class DictDatabase
{

    private Question qs = new Question();
    String lang = "thai";

    public DictDatabase(){

    }

    public void setLang(String lang){
        this.lang = lang;
    }

    public Question getData(Vector<String>  vSelectList){
        Connection connect = connect();
        getRandomWord(connect, vSelectList);
        getChoice(connect, qs.getAnswer(), vSelectList);
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

    public  Vector<Vector<String>> getSearch(String search)
    {
        //String [][]b = new String[20][3];
        Vector<Vector<String>> b = new Vector<Vector<String>>();
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

            String sql = "SELECT * FROM GAME  WHERE 1=1 "+ where +"  order by id desc limit 20";

            try {
                statement = connect.createStatement();
                resultSet = statement.executeQuery(sql);

                int i = 0;
                while (resultSet.next())
                {
                    Vector<String> v = new Vector<String>();
                    v.add(resultSet.getString("thai"));
                    v.add(resultSet.getString("english"));
                    v.add(resultSet.getString("group_name"));
                    b.add(v);

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


    public  Vector<Vector<String>> getSearchGroup(String search)
    {
        //String [][]b = new String[20][3];
        Vector<Vector<String>> b = new Vector<Vector<String>>();
        Connection connect  = connect();
        if(connect instanceof Connection)
        {
            ResultSet resultSet = null;
            Statement statement = null;

            String where = "";
            if(!search.isEmpty()) {
                where = "and group_name like '%" + search + "%' ";
            }

            String sql = "SELECT * FROM GAME  WHERE 1=1 "+ where +" and group_name != '' group by group_name order by id desc limit 20";

            try {
                statement = connect.createStatement();
                resultSet = statement.executeQuery(sql);

                int i = 0;
                while (resultSet.next())
                {
                    Vector<String> v = new Vector<String>();
                    v.add(resultSet.getString("group_name"));
                    b.add(v);

                }

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

    public  void saveDict(String thai, String english, String group, String ex_sentence)
    {
        Connection connect  = connect();
        if(connect instanceof Connection)
        {
            ResultSet resultSet = null;
            Statement statement = null;

            String sql = "INSERT INTO GAME (thai, english, group_name, ex_sentence) VALUES ('"+thai+"','"+english+"','"+group+"','"+ex_sentence+"')";

            try {
                statement = connect.createStatement();
                statement.executeQuery(sql);

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
    }

    private void getRandomWord(Connection connection, Vector<String>  vSelectList)
    {

        if(connection instanceof Connection)
        {
            ResultSet resultSet = null;
            Statement statement = null;

            String groupSearch = "";
            if(vSelectList.size() > 0)
            {
                groupSearch = "where group_name='";
                groupSearch += String.join("' or group_name= '", vSelectList);
                groupSearch += "'";
            }


            String sql = "SELECT * FROM GAME   "+groupSearch+" ORDER BY RANDOM() LIMIT 1";
            //String sql = "SELECT * FROM GAME ORDER BY RANDOM() LIMIT 1";
            //System.out.println(sql);
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                while (resultSet.next())
                {
                    String[] selectLang = {"thai", "english"};
                    if(lang == "english") {
                        selectLang[0] = "english";
                        selectLang[1] = "thai";
                    }
                    String question = resultSet.getString(selectLang[0]);
                    qs.setQuestion(question);
                    String answer = resultSet.getString(selectLang[1]);
                    qs.setAnswer(answer);
                }

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


    private void getChoice(Connection connection,String answer, Vector<String>  vSelectList )
    {
        ArrayList<String> item = qs.getItem();
        item.clear();



        if(connection instanceof Connection)
        {
            ResultSet resultSet = null;
            Statement statement = null;

            String groupSearch = "";
            if(vSelectList.size() > 0) {
                groupSearch = "and (group_name='";
                groupSearch += String.join("' or  group_name = '", vSelectList);
                groupSearch += "')";
            }



            String sql = "SELECT * FROM GAME WHERE english != '" + answer + "' "+groupSearch+" ORDER BY RANDOM() LIMIT 4";
            //System.out.println(sql);
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

                    if(lang == "thai") //select answer in english
                    {
                        item.add(resultSet.getString("english"));
                    }
                    else //select answer in thai
                    {
                        item.add(resultSet.getString("thai"));
                    }
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
