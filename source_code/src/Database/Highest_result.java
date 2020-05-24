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
public class Highest_result {
    private int result_id;
    private int result_score;
    private int result_time;
    private int result_user_id;
    private String result_name;

    public int getResult_user_id() {
        return result_user_id;
    }

    public void setResult_user_id(int result_user_id) {
        this.result_user_id = result_user_id;
    }

    public String getResult_name() {
        return result_name;
    }

    public void setResult_name(String result_name) {
        this.result_name = result_name;
    }
    
    public int getResult_id() {
        return result_id;
    }

    public void setResult_id(int result_id) {
        this.result_id = result_id;
    }

    public int getResult_score() {
        return result_score;
    }

    public void setResult_score(int result_score) {
        this.result_score = result_score;
    }

    public int getResult_time() {
        return result_time;
    }

    public void setResult_time(int result_time) {
        this.result_time = result_time;
    }

}