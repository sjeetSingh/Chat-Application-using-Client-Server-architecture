/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomserver;

/* Team Information

*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)

*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!
  public class DriverDatabase {
     
     String driver;
     String url;
     String dbname;
     public String user;
     public String password;
     public DriverDatabase(String url,String dbName,String userName,String password)
     {
         driver = "org.gjt.mm.mysql.Driver";
         this.url="jdbc:mysql://"+url+"/";
         this.dbname=dbName;
         this.user=userName;
         this.password=password;
     }
     public boolean checkLogin(String userName,String password)
     {
        Connection conn=null;
         try
         {
             Class.forName(driver).newInstance();
             conn = DriverManager.getConnection(url+dbname,this.user,this.password);
         }
         catch (Exception e) { e.printStackTrace(); } 
        userName=userName.trim();password=password.trim();
        try
         {
            Statement st = conn.createStatement(); 
            ResultSet res = st.executeQuery("SELECT * FROM users");
            while (res.next()) 
            {
                
                if(res.getString("username").trim().equalsIgnoreCase(userName)&&
                      res.getString("password").trim().equalsIgnoreCase(password)  )
                {
                    conn.close();
                    return true;
                }
            }
            conn.close();
         }
        catch (Exception e) { e.printStackTrace(); }
        
        return false;
     }
     private boolean  checkUser(String userName)
     {
         Connection conn=null;
         try
         {
             Class.forName(driver).newInstance();
             conn = DriverManager.getConnection(url+dbname,this.user,this.password);
         }
         catch (Exception e) { e.printStackTrace(); } 
         try
         {
            Statement st = conn.createStatement(); 
            ResultSet res = st.executeQuery("SELECT * FROM users where username = '"+userName.trim()+"'");
            
            if(res.next())
            {
                conn.close();
                return true;
            }
            conn.close();
         }
         catch (Exception e) { e.printStackTrace(); }
         
        return false;
     }
     public boolean  register(String userName,String password)
     {
         Connection conn=null;
         try
         {
             Class.forName(driver).newInstance();
             conn = DriverManager.getConnection(url+dbname,this.user,this.password);
         }
         catch (Exception e) { e.printStackTrace(); } 
        if(checkUser(userName.trim()))return false;
        try
        {
            
            Statement st = conn.createStatement(); 
            int val = st.executeUpdate("INSERT into users (username,password) VALUES("+"'"+userName.trim()+"'"+","+"'"+password.trim()+"'"+")"); 
            if(val==1)
            {
                conn.close();
                return true;
            }
            conn.close();
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return false;
     }
     
     public String getAllRoom()
     {
         String result="";
         Connection conn=null;
         try
         {
             Class.forName(driver).newInstance();
             conn = DriverManager.getConnection(url+dbname,this.user,this.password);
         }
         catch (Exception e) { e.printStackTrace(); } 
         try
         {
            Statement st = conn.createStatement(); 
            ResultSet res = st.executeQuery("SELECT * FROM chatrooms");
            
            while (res.next()) 
            {
                result=result+res.getString("name")+MessageChat.dotMsg;    
            }
            conn.close();
         }
        catch (Exception e) { e.printStackTrace(); }
        return result;
     }
     private boolean checkRoom(String roomname)
     {
         Connection conn=null;
         try
         {
             Class.forName(driver).newInstance();
             conn = DriverManager.getConnection(url+dbname,this.user,this.password);
         }
         catch (Exception e) { e.printStackTrace(); }
         try
         {
            Statement st = conn.createStatement(); 
            ResultSet res = st.executeQuery("SELECT * FROM chatrooms where name='"+roomname.trim()+"'");
            if(res.next())
            {
                conn.close();
                return true;
            }
            conn.close();
         }
         catch (Exception e) { e.printStackTrace(); }
        return false;
     }
     public boolean  CreateRoom(String roomname)
     {
         Connection conn=null;
         try
         {
             Class.forName(driver).newInstance();
             conn = DriverManager.getConnection(url+dbname,this.user,this.password);
         }
         catch (Exception e) { e.printStackTrace(); }
        if(checkRoom(roomname.trim()))return false;
        try
        {
            Statement st = conn.createStatement(); 
            int val = st.executeUpdate("INSERT into chatrooms (name) VALUES("+"'"+roomname.trim()+"'"+")"); 
            if(val==1)
            {
                conn.close();
                return true;
            }
            conn.close();
        }
        catch (Exception e) { e.printStackTrace(); }
        return false;
     }
  }



