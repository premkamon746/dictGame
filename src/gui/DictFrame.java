package gui;

import javax.swing.*;

public class DictFrame extends JFrame
{
    DictTab dictTab = new DictTab(this);

    public DictFrame()
    {
        super("DictGame");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        add();
    }

    private void add(){
        add(dictTab);
    }
}
