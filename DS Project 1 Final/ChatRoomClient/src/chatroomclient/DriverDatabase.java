/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomclient;
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
import java.util.Date;
import java.util.*;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!
  public class DriverDatabase {
     
     String driver;
     String url;
     String dbname;
     String user;
     String password;
     
     // create database connect to mysql
     public DriverDatabase(String url,String dbName,String userName,String password)
     {
         driver = "org.gjt.mm.mysql.Driver";
         this.url="jdbc:mysql://"+url+"/";
         this.dbname=dbName;
         this.user=userName;
         this.password=password;
     }
     
     //check a session from database
     public boolean checkSession(String session)
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
            ResultSet res = st.executeQuery("SELECT * FROM chatlogs where session = '"+session.trim()+"'");
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
     
     //save a session to database
     public boolean  saveSesion(String userName,String session,String roomid,String value)
     {
         Connection conn=null;
         
         try
         {
             Class.forName(driver).newInstance();
             conn = DriverManager.getConnection(url+dbname,this.user,this.password);
             
         }
         catch (Exception e) { e.printStackTrace(); } 
        Date date = new Date();   
        String valDate=date.toString();
       
        //if(checkSession(session))return false;
        try
        {
            
            Statement st = conn.createStatement(); 
            int val = st.executeUpdate("INSERT into chatlogs  VALUES("+"'"+userName.trim()+"','"+session.trim()+"','"+valDate+"','"
                    +roomid+"','"+value+"')"); 
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
     
     //view session
     public List<HistoryMessage>  viewSesion(String userName,String session)
     {
         
        List<HistoryMessage> result=new ArrayList<HistoryMessage>();
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
            ResultSet res = st.executeQuery("SELECT * FROM chatlogs where username='"+userName.trim() +"' and  session = '"+session.trim()+"'");
            while(res.next())
            {
                HistoryMessage h=new HistoryMessage();
                h.seesion=session;
                h.roomid=res.getString("roomname");
                h.date=res.getString("date");
                h.chatlog=res.getString("chatlog");
                result.add(h);
            }
            conn.close();
         }
        catch (Exception e) { e.printStackTrace(); }
        
        return result;
     }
  }
