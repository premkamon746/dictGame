package process;

import db.DictDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DictProcessAdd extends JPanel
{
    DictDatabase db = new DictDatabase();
    public DictProcessAdd()
    {

    }

    public void  process(DefaultTableModel model, String search)
    {
        String [][]result = db.getSearch(search);

        for(int i = 0; i < 20; i++)
        {
            model.setValueAt("1111",i,0);
            model.setValueAt("2222",i,1);
            model.setValueAt("3333",i,2);
        }
    }
}
