/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sign_up;

/**
 *
 * @author kn
 */
public class SignUpProcess {
    
    private String phNumber, email;
    
    public int ProcessPhNumber(String strNumber) {
        try {
            strNumber = strNumber.trim();
            phNumber = strNumber;
            int number = Integer.parseInt(strNumber);
            if (strNumber.length() != 10 || strNumber.codePointAt(0) != 48) {
                //System.out.println("Invalid phone number");
                return -1;
            }       
        }
        catch(Exception e) {
            return -1;
            //System.out.println("Invalid phone number");
        }
        return 0;
    }
    
    public int ProcessEmail(String strEmail) {
        strEmail = strEmail.trim();
        String subStr;
        char[] charEmail = strEmail.toCharArray();
        if (charEmail[0] == '.') {
            //System.out.println("Invalid email");
            return -2;
        }
        if (strEmail.indexOf('&') != -1 || strEmail.indexOf('=') != -1 ||
            strEmail.indexOf('_') != -1 || strEmail.indexOf('\'') != -1 ||
            strEmail.indexOf('-') != -1 || strEmail.indexOf('+') != -1 ||
            strEmail.indexOf(',') != -1 || strEmail.indexOf('<') != -1 ||
            strEmail.indexOf('>') != -1) {
            //System.out.println("Invalid email");
            return -2;
        }
        if (strEmail.indexOf("..") != -1) {
            //System.out.println("Invalid email");
            return -2;
        }
        if (strEmail.indexOf('@') == -1) {
            //System.out.println("Invalid email");
            return -2;
        }
        subStr = strEmail.substring(0, strEmail.indexOf('@'));
        if (subStr.length() > 30 || subStr.length() < 6) {
            //System.out.println("Invalid email");
            return -2;
        }
        email = strEmail.trim();
        return 0;
    }
}
