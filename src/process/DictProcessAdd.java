package process;

import db.DictDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class DictProcessAdd extends JPanel
{
    DictDatabase db = new DictDatabase();
    JFrame jf;
    public DictProcessAdd(JFrame jf)
    {
        this.jf = jf;
    }

    public void  process(DefaultTableModel model, String search)
    {
        Vector<Vector<String>>result = db.getSearch(search);


        model.addColumn("Thai");
        model.addColumn("English");
        model.addColumn("Group");
        for(int i = 0; i < result.size(); i++)
        {
            model.addRow(new Object[0]);
            model.setValueAt(result.get(i).get(0), i, 0);
            model.setValueAt(result.get(i).get(1), i,1);
            model.setValueAt(result.get(i).get(2), i,2);
        }
    }
}
