/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomserver;
import java.util.*;
/* Team Information

*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)

*/
public class ListRoomChat {
    public List<RoomChat> listRoom;
    public ListRoomChat()
    {
        listRoom=new ArrayList<>();
    }
    
    public void joinInRoom(String user,RoomChat room)
    {
        // the fuction add a user to roomchat has name is room
        RoomChat lroom=checkRoom(room);
        if(lroom==null)
        {
            room.addUser(user);
            listRoom.add(room);
        }
        else lroom.addUser(user);
        
    }
    public void joinOutRoom(String user,RoomChat room)
    {
        // the fuction remove a user to roomchat has name is room
        RoomChat lroom=checkRoom(room);
        if(lroom!=null)
        {
            lroom.removeUser(user);
        }
        
        
    }
    public int getUserCount(RoomChat room)
    {
        // the fuction get number of user in roomchat has name is room
        RoomChat lroom=checkRoom(room);
        if(lroom!=null)
        {
            return lroom.listUsers.size();
        }
        return 0;
    }
    public String getListUser(RoomChat room)
    {
        // get list user in roomchat
        RoomChat lroom=checkRoom(room);
        if(lroom!=null)
        {
            return lroom.getAllUser();
        }
        return null;
    }
    private RoomChat checkRoom(RoomChat room)
    {
        for(int i=0;i<listRoom.size();i++)
            if(listRoom.get(i).RoomId.equalsIgnoreCase(room.RoomId))return listRoom.get(i);
        return null;
    }
}
