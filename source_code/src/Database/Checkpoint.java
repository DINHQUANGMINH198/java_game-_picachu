/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author kn
 */
public class Checkpoint {
    private int checkpoint_id;
    private String score;
    private String time;

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
    private int[][] matrix;

    public int getCheckpoint_id() {
        return checkpoint_id;
    }

    public void setCheckpoint_id(int checkpoint_id) {
        this.checkpoint_id = checkpoint_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
