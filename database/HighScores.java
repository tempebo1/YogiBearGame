package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScores {
    int maxScores;
    PreparedStatement insertStatement;
    PreparedStatement deleteStatement;
    Connection connection;

    public HighScores() throws SQLException {

            this.maxScores=15;

        String dbURL = "jdbc:derby://localhost:1527/HIGHSCORES;";
        connection = DriverManager.getConnection(dbURL);
        String insertQuery = "INSERT INTO HIGHSCORES (NAME, SCORE , LEVEL) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
        String deleteQuery = "DELETE FROM HIGHSCORES WHERE SCORE=?";
        deleteStatement = connection.prepareStatement(deleteQuery);
    }

    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            int level = results.getInt("LEVEL");
            highScores.add(new HighScore(name, score , level));
        }
        sortHighScores(highScores);
        return highScores;
    }

    public void putHighScore(String name, int score , int level) throws SQLException {
        ArrayList<HighScore> highScores = getHighScores();
        if (highScores.size() < maxScores) {
            insertScore(name, score, level);
        } else {
            int leastScore = highScores.get(highScores.size() - 1).getScore();
            int leastLevel = highScores.get(highScores.size() - 1).getLevel();
            if (leastScore < score) {
                deleteScores(leastScore, leastLevel);
                insertScore(name, score, level);
            }
        }
    }

    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                return t1.getScore() - t.getScore();
            }
        });
    }

    private void insertScore(String name, int score, int level) throws SQLException {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        //insertStatement.setTimestamp(1, ts);
        insertStatement.setString(1, name);
        insertStatement.setInt(2, score);
         insertStatement.setInt(3, level);
        insertStatement.executeUpdate();
    }

    private void deleteScores(int score, int level) throws SQLException {
        deleteStatement.setInt(1, score);
        deleteStatement.setInt(2, level);
        deleteStatement.executeUpdate();
    }
    
        
        public String[][] getDataMatrix () throws SQLException{
        String[][] columnNames = new String[10][4];
        ArrayList<HighScore> highscores = getHighScores();
        int cnt = 0;
        for(HighScore hs : highscores){
            columnNames[cnt][0] = String.valueOf(cnt+1);
            columnNames[cnt][1] = hs.getName();
            columnNames[cnt][2] = String.valueOf(hs.getScore());
            columnNames[cnt][3] = String.valueOf(hs.getLevel());
            cnt++;
        }
        for(;cnt < 10; cnt++){
            columnNames[cnt][0] = String.valueOf(cnt+1);
            columnNames[cnt][1] = "";
            columnNames[cnt][2] = "";
            columnNames[cnt][3] = "";

        }
        return columnNames;
    }
    

    public String[] getColumnNamesArray (){
        String[] columnNames = {"Ranking", "Name", "Baskets collected", "Level"};
        return columnNames;
    }
}
