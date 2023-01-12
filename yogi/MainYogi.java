package yogi;

import database.HighScores;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class MainYogi {
    private final int SCREEN_WIDTH = 1920;
    private final int SCREEN_HEIGHT = 900;

    MenuPanel panel = new MenuPanel(new GridBagLayout());
   
    private JMenuBar menuBar;
    private JMenu menu;
    
    private JFrame highscoresFrame;
    public JFrame gameFrame;
    private JFrame menuFrame;
    
    HighScores scoresDb = new HighScores();    
    JButton gameButton = new JButton("Start the game");
    JButton DbButton = new JButton("Scores menu");
    
    public MainYogi() throws HeadlessException, SQLException, SQLException {  
        this.menuFrame = new JFrame("Menu");
        updateHighscoresFrame();
       
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        this.gameButton.addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                updateGamesFrame(); 
                gameFrame.setVisible(true);
            }           
        });
        
        this.DbButton.addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                if(gameFrame != null)
                gameFrame.dispose();
            try {
                updateHighscoresFrame();
                highscoresFrame.setVisible(true);
            } catch (HeadlessException ex) {
                Logger.getLogger(MainYogi.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
        });
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0,75, 0 , 75);        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panel.add(gameButton,gridBagConstraints);        
        gridBagConstraints.gridx = 5;
        panel.add(DbButton,gridBagConstraints);

        menuFrame.add(panel);
        
        menuFrame.setSize(510,390);
        menuFrame.setResizable(false);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
    }
    
    private void updateHighscoresFrame(){
        try {
            highscoresFrame = new JFrame("Highscores");             
            ArrayList<database.HighScore> result = scoresDb.getHighScores();
            JTable table = new JTable(scoresDb.getDataMatrix(),scoresDb.getColumnNamesArray());
            table.setEnabled(false);
            table.setRowHeight(50);
            JScrollPane sp = new JScrollPane(table);
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(50);
            columnModel.getColumn(1).setPreferredWidth(230);
            columnModel.getColumn(2).setPreferredWidth(120);
            columnModel.getColumn(3).setPreferredWidth(120);
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            columnModel.getColumn(0).setCellRenderer(cellRenderer);
            columnModel.getColumn(1).setCellRenderer(cellRenderer);
            columnModel.getColumn(2).setCellRenderer(cellRenderer);
            columnModel.getColumn(3).setCellRenderer(cellRenderer);
            highscoresFrame.add(sp);
          
            highscoresFrame.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
            highscoresFrame.setResizable(false);
            highscoresFrame.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(MainYogi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateGamesFrame(){
        gameFrame = new JFrame("Yogi Game");
        gameFrame.add(new GamePanel(this));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        menuBar = new JMenuBar();
        gameFrame.setJMenuBar(menuBar);
        menu = new JMenu("Menu");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
            }
        });
        menu.add(exit);
        
        JMenuItem restart = new JMenuItem("Restart");
        restart.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               gameFrame.dispose();
               updateGamesFrame();
               gameFrame.setVisible(true);
            }
        });
        menu.add(restart);

 

        menuBar.add(menu);
        
        gameFrame.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
    }

   
    
}
