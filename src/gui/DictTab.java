package gui;


import javax.swing.*;

public class DictTab extends JTabbedPane
{
    JFrame jf;
    DictTabPanelPlay play;
    DictTabPanelChoose choose;
    DictTabPanelAdd add;


    public DictTab(JFrame jf)
    {
        this.jf = jf;
        play        = new DictTabPanelPlay(jf);
        choose      = new DictTabPanelChoose(jf);
        add         = new DictTabPanelAdd(jf);

        addTab("Play", null, play, "play dict game.");
        addTab("Choose Group", null, choose, "choose from group of word or category.");
        addTab("Add New", null, add, "add new word or sentence.");
    }
}
