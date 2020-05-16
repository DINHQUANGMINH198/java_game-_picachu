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
    
    public void ListSys_config() {
        try {
            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM pokemon.Users;");
            while (result.next()) {
                System.out.println(result.getString("Name"));
            }
            result.close();
            stat.close();
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
    
    public void LogIn(int id, String pass, Users user) {
        try {
            PreparedStatement pre_state = conn.prepareStatement("SELECT * FROM pokemon.Users WHERE ID_Users = ? AND Password = ?;");
            pre_state.setInt(1, id);
            pre_state.setString(2, pass);
            ResultSet result = pre_state.executeQuery();
            
            if (!result.next()) {
                System.out.println("nullahihi");
                return;
            }
            user.setUser_name(result.getString("Name"));
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public void ListProduct(String mess, Product prod) {
//        try {
//            PreparedStatement pre_state = conn.prepareStatement("SELECT * FROM Sell_system.Product WHERE ID = ?;");
//            pre_state.setString(1, mess);
//            ResultSet result = pre_state.executeQuery();
//            result.next();
//            prod.setProduct_id(result.getString("ID"));
//            prod.setProduct_name(result.getString("Name"));
//            prod.setCost(result.getInt("Cost"));
//            prod.setAmount_product(result.getInt("Amount"));
//            result.close();
//            pre_state.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//            
//        }      
//    }
//    
//    public void Insert_into_product(String name, int cost, int amount) {
//        try {
//            PreparedStatement pre_state = conn.prepareStatement("SELECT * FROM Sell_system.Product WHERE Name = ?;");
//            pre_state.setString(1, name);
//            ResultSet result = pre_state.executeQuery();
//            if (result.next() == false) {
//                PreparedStatement pre_state1 = conn.prepareStatement("INSERT INTO Sell_system.Product (Name, Cost, Amount) VALUES(?, ?, ?);");
//                pre_state1.setString(1, name);
//                pre_state1.setInt(2, cost);
//                pre_state1.setInt(3, amount);
//                pre_state1.executeUpdate();
//
//                pre_state1.close();
//            } else {               
//                PreparedStatement pre_state2 = conn.prepareStatement("UPDATE Sell_system.Product SET Cost=?, Amount=? WHERE Name=?");
//                pre_state2.setInt(1, cost);
//                pre_state2.setInt(2, amount);
//                pre_state2.setString(3, name);
//                pre_state2.executeUpdate();
//                pre_state2.close();
//            }
//        }catch (SQLException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//
//    }
}
