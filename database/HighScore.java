
package database;


public class HighScore {
    
    private final String name;
    private final int score;
    private final int level;

    public HighScore(String name, int score, int level) {
        this.name = name;
        this.score = score;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "HighScore{" + "name=" + name + ", score=" + score + '}';
    }
    

}
