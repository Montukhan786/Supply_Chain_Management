package com.example.supplychain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.function.BiConsumer;

public class login {
    DatabaseConnection dbConn = new DatabaseConnection();

    public static byte[] getSHA(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("SH-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    static String getEncryptedPassword(String password){
        String encryptedPassword = "";
        BigInteger number = new BigInteger(1,getSHA(password));
        StringBuilder haxString = new StringBuilder(number.toString(16));
        encryptedPassword = haxString.toString();
        return encryptedPassword;
    }
    public boolean customerLogin(String email,String password){
        try{
            String query = String.format("SELECT * FROM customer where email = '%s' and password = '%s'",
                    email,password);
            ResultSet rs = dbConn.getQueryTable(query);
            if(rs == null) return false;
            if(rs.next()){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
