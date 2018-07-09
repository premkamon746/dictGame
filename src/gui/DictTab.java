package gui;

import process.DictProcessAdd;
import process.DictProcessPlay;

import javax.swing.*;

public class DictTab extends JTabbedPane
{
    DictTabPanelPlay play       = new DictTabPanelPlay();
    DictTabPanelChoose choose   = new DictTabPanelChoose();
    DictTabPanelAdd add         = new DictTabPanelAdd();
    JFrame jf;

    public DictTab(JFrame jf)
    {
        this.jf = jf;
        play.doBusinessLogic(new DictProcessPlay(jf));
        add.doBusinessLogic(new DictProcessAdd());
        addTab("Play", null, play, "play dict game.");
        addTab("Choose Group", null, choose, "choose from group of word or category.");
        addTab("Add New", null, add, "add new word or sentence.");
    }
}
