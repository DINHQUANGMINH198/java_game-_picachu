/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kn
 */
public class Database {
    public Connection conn;
    
    public String cvtMatrix_String(int[][] matrix) {
        String str_matrix = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                str_matrix += Integer.toString(matrix[i][j]);
                str_matrix += " ";
            }
        }
        System.out.println(str_matrix.length());
        return str_matrix;
    }
    
    public int[][] cvtString_matrix(String str_matrix) {
        int[][] matrix = new int[10][10];
        String temp = "";
        int j = 0;
        int k = 0;
        temp += str_matrix.charAt(0);
        for (int i = 0; i < str_matrix.length(); i++) {
            char ch = str_matrix.charAt(i);
            if (ch != 32) {
                temp += ch;
            } else {
                if (k > 9) {
                    j++;
                    k = 0;
                    matrix[j][k] = Integer.parseInt(temp);
                    temp = "";
                } else {
                    matrix[j][k] = Integer.parseInt(temp);
                    temp = "";
                    k++;
                }
                
            }
        }
        return matrix;
    }
    
    public boolean CheckConn(String user_name, String pass) {
        boolean check = false;
        try {    
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pokemon", user_name, pass);
            if (conn != null) {
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
    
    public void checkSignUp(Users user, String name) {
        try {
            PreparedStatement pre_state = conn.prepareStatement("SELECT * FROM pokemon.Users WHERE Name = ?;");
            pre_state.setString(1, name);
            ResultSet result = pre_state.executeQuery();
            if (result.next()) {
                System.out.println("get another user name");
                user.setUser_name(name);
                return;
            }
            result.close();
            pre_state.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    public void SignUp(String name, String email, String phone_nr, String pass) {
        try {
            PreparedStatement pre_state = conn.prepareStatement("INSERT INTO pokemon.Users (Name, Email, Phone_number, Password) VALUES(?, ?, ?, ?);");
            pre_state.setString(1, name);
            pre_state.setString(2, email);
            pre_state.setString(3, phone_nr);
            pre_state.setString(4, pass);
            pre_state.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            
        }      
    }
    
    public void LogIn(String user_name, String pass, Users user) {
        try {
            PreparedStatement pre_state = conn.prepareStatement("SELECT * FROM pokemon.Users WHERE Name = ? AND Password = ?;");
            pre_state.setString(1, user_name);
            pre_state.setString(2, pass);
            ResultSet result = pre_state.executeQuery();
            
            if (!result.next()) {
                System.out.println("nullahihi");
                return;
            }
            user.setUser_name(result.getString("Name"));
            user.setUser_id(result.getInt("ID_Users"));
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void SavingGame(int[][] matrix, int time, int score, int user_id, String signal) {
        try {
            PreparedStatement pre_state;
            String str_matrix = cvtMatrix_String(matrix);
            
            if (signal.equals("0")) {         
                pre_state = conn.prepareStatement("INSERT INTO pokemon.Checkpoint (Matrix, Score, Time, ID_Users) VALUES(?, ?, ?, ?);");
            } else {
                pre_state = conn.prepareStatement("UPDATE pokemon.Checkpoint SET Matrix = ?, Score = ?, Time = ? WHERE ID = ?");
            }
            pre_state.setString(1, str_matrix);
            pre_state.setString(2, Integer.toString(score));
            pre_state.setString(3, Integer.toString(time));
            pre_state.setInt(4, user_id);
            pre_state.executeUpdate();
            pre_state.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            
        }    
    }
    
    public boolean continuePlay(int user_id) {
        try {
            PreparedStatement pre_state = conn.prepareStatement("SELECT * FROM pokemon.Checkpoint WHERE ID_Users = ?;");
            pre_state.setInt(1, user_id);
            ResultSet result = pre_state.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public int[][] getMatrix(int user_id) {
        int[][] matrix = null;
        try {
            PreparedStatement pre_state = conn.prepareStatement("SELECT * FROM pokemon.Checkpoint WHERE ID_Users = ?;");
            pre_state.setInt(1, user_id);
            ResultSet result = pre_state.executeQuery();
            if (result.next()) {
                matrix = cvtString_matrix(result.getString("Matrix"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return matrix;
    }
    
    public int getTime(int user_id) {
        int time = 0;
        try {
            PreparedStatement pre_state = conn.prepareStatement("SELECT * FROM pokemon.Checkpoint WHERE ID_Users = ?;");
            pre_state.setInt(1, user_id);
            ResultSet result = pre_state.executeQuery();
            if (result.next()) {
                time = Integer.parseInt(result.getString("Time"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return time;
    }
    
    public int getScore(int user_id) {
        int score = 0;
        try {
            PreparedStatement pre_state = conn.prepareStatement("SELECT * FROM pokemon.Checkpoint WHERE ID_Users = ?;");
            pre_state.setInt(1, user_id);
            ResultSet result = pre_state.executeQuery();
            if (result.next()) {
                score = Integer.parseInt(result.getString("Score"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return score;
    }
    
    public void setHightResult(int id_user, int time, int score) {
        try {
            PreparedStatement pre_state;
            PreparedStatement pre_state1;

            pre_state = conn.prepareStatement("SELECT * FROM pokemon.Highest_result WHERE ID_Users = ?;");
            pre_state.setInt(1, id_user);
            ResultSet result = pre_state.executeQuery();

            if (result.next()) {
                pre_state1 = conn.prepareStatement("UPDATE pokemon.Highest_result SET Highest_score = ?, Time_play = ? WHERE ID_Users = ?");
            } else {
                pre_state1 = conn.prepareStatement("INSERT INTO pokemon.Highest_result (Highest_score, Time_play, ID_Users) VALUES(?, ?, ?);");
            }
            pre_state1.setInt(1, score);
            pre_state1.setInt(2, time);
            pre_state1.setInt(3, id_user);
            pre_state1.executeUpdate();
            pre_state1.close();
            pre_state.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            
        }    
    }
    
    public void getTop3(Highest_result highest_result1, Highest_result highest_result2, Highest_result highest_result3) {
        PreparedStatement pre_state;
        try {
            pre_state = conn.prepareStatement("select Users.Name, Highest_result.Highest_score, Highest_result.Time_play\n" +
                                            "from pokemon.Highest_result\n" +
                                            "inner join Users on Highest_result.ID_Users = Users.ID_Users\n" +
                                            "order by Time_play\n" +
                                            "limit 3;");
            ResultSet result = pre_state.executeQuery();
            
            result.next();
            highest_result1.setResult_name(result.getString("Name"));
            highest_result1.setResult_score(result.getInt("Highest_score"));
            highest_result1.setResult_time(result.getInt("Time_play"));
            
            result.next();
            highest_result2.setResult_name(result.getString("Name"));
            highest_result2.setResult_score(result.getInt("Highest_score"));
            highest_result2.setResult_time(result.getInt("Time_play"));
            
            result.next();
            highest_result3.setResult_name(result.getString("Name"));
            highest_result3.setResult_score(result.getInt("Highest_score"));
            highest_result3.setResult_time(result.getInt("Time_play"));
            
            result.close();
            pre_state.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
