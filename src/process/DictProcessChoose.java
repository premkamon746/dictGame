package process;

import db.DictDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Vector;

public class DictProcessChoose
{
    DictDatabase db = new DictDatabase();
    JFrame jf;
    public DictProcessChoose(JFrame jf)
    {
        this.jf = jf;
    }

    public void  process(DefaultListModel model, String search)
    {
        Vector<Vector<String>>result = db.getSearchGroup(search);

        model.clear();
        for(int i = 0; i < result.size(); i++)
        {
            model.addElement(i+1+". "+result.get(i).get(0));
        }

    }


}
