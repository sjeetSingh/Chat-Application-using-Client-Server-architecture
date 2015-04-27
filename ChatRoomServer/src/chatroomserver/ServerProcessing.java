
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatroomserver;

/* Team Information

*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)

*/
import java.net.*;
import java.io.*;
import java.util.*;
class ServerProcessing extends Thread
{
    Socket server;
    public String name;
    DataOutputStream dos;
	DataInputStream dis;
        Server myserver;
        
	public ServerProcessing(Socket s,Server ss)
	{
		server=s;
                myserver=ss;
	}
       
	public void run()
	{
		try
		{
			dos=new DataOutputStream(server.getOutputStream());
			dis=new DataInputStream(server.getInputStream());
			
			while(true)
			{
                            try
                            {
                                String st= dis.readUTF();
                                System.out.println("from client:"+st);
                                processMessage(st);
                            }
                            catch(Exception ae)
                            {
                                continue;
                            }
				
			}
		}
		catch(Exception ae){
                System.out.println("error message:"+ae );}
	}
        public void sendMessage(String msg)
        {
            try
            {
            	
                dos.writeUTF(msg);
                
            }
           catch(IOException ae)
           {
                System.out.println("Error message:"+ae );
           }
        }
        
    public void processMessage(String msg)
    {
        StringTokenizer strTokToken =
            new StringTokenizer(msg, MessageChat.dotMsg, false);
        String clientName=strTokToken.nextToken();
        String command=strTokToken.nextToken();
        String roomID=strTokToken.nextToken();
        String strmsg=strTokToken.nextToken();
        
        if(this.name==null) this.name=clientName;
        if(command.equalsIgnoreCase(MessageChat.Login))
        {
            //check login from client
            String pass=strTokToken.nextToken();
            // check user and pass from database
            boolean check=myserver.database.checkLogin(strmsg, pass);
            
            //send result to client
            String message=clientName+MessageChat.dotMsg+MessageChat.Login+MessageChat.dotMsg+
                    "0"+MessageChat.dotMsg+check+MessageChat.dotMsg+myserver.database.user+MessageChat.dotMsg
                    +myserver.database.password;
            myserver.sendMessage(clientName, message);
            
            //send all room chat if login is ok
            if(check)
            {
                message=clientName+MessageChat.dotMsg+MessageChat.AllRoom+MessageChat.dotMsg+
                    "0"+MessageChat.dotMsg+myserver.database.getAllRoom();;
                myserver.sendMessage(clientName, message);
            }
            
        }
        if(command.equalsIgnoreCase(MessageChat.Logout))
        {
            String message=clientName+MessageChat.dotMsg+MessageChat.Logout+MessageChat.dotMsg+
                    "0"+MessageChat.dotMsg+clientName;
            myserver.sendMessageAll(message);
        }
        if(command.equalsIgnoreCase(MessageChat.Register))
        {
            // if client request is register
           String pass=strTokToken.nextToken();
           //check user and pass
            boolean check=myserver.database.register(strmsg, pass);
            //send result
            String message=clientName+MessageChat.dotMsg+MessageChat.Register+MessageChat.dotMsg+
                    "0"+MessageChat.dotMsg+check;
            myserver.sendMessage(clientName, message);
        }
        if(command.equalsIgnoreCase(MessageChat.JoininRoom))
        {
            RoomChat room=new RoomChat(roomID);
            
            // if client joinin then insert user in listroom
            myserver.listRoom.joinInRoom(clientName, room);
            //send all user in room to client
            String message=clientName+MessageChat.dotMsg+MessageChat.JoininRoom+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+myserver.listRoom.getListUser(room);
            myserver.sendMessageAll( message);
        }
        if(command.equalsIgnoreCase(MessageChat.JoinoutRoom))
        {
           RoomChat room=new RoomChat(roomID);
           //if join out then update to listroom
            myserver.listRoom.joinOutRoom(clientName, room);
            //send to client
            String message=clientName+MessageChat.dotMsg+MessageChat.JoinoutRoom+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+clientName;
            myserver.sendMessageAll( message);
        }
        if(command.equalsIgnoreCase(MessageChat.CreateRoom))
        {
            boolean check=myserver.database.CreateRoom(roomID);
            if(check)
            {
                // if create room then create room in data and send to client
                String message=clientName+MessageChat.dotMsg+MessageChat.CreateRoom+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+check;
                myserver.sendMessageAll( message);
            }
            else
            {
                String message=clientName+MessageChat.dotMsg+MessageChat.CreateRoom+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+check;
                myserver.sendMessage(clientName, message);
            }
        }
        if(command.equalsIgnoreCase(MessageChat.Chat))
        {
            // if command is chat then send chat to all client in room
            String message=clientName+MessageChat.dotMsg+MessageChat.Chat+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+strmsg;
                myserver.sendMessageAll(message);
        }
        if(command.equalsIgnoreCase(MessageChat.RoomCount))
        {
            // send get room count and send to client
            RoomChat room=new RoomChat(roomID);
            String message=clientName+MessageChat.dotMsg+MessageChat.RoomCount+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+myserver.listRoom.getUserCount(room);
                myserver.sendMessage(clientName, message);
        }
    }
}