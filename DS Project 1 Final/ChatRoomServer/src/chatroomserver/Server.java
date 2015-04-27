/*
References:


Socket Chat referred from :http://www.cn-java.com/download/data/book/socket_chat.pdf
                           https://www.youtube.com/watch?v=PDCAnKX2kkY
                           https://www.youtube.com/watch?v=qWYn1omeqqs(just got an idea of the GUI from here)
                           https://www.youtube.com/watch?v=fSQOhFYgE3s
                           https://www.youtube.com/watch?v=6G_W54zuadg&spfreload=10%20Message%3A%20Unexpected%20end%20of%20input%20(url%3A%20https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D6G_W54zuadg)

Login and Register        :https://www.youtube.com/watch?v=lJ-quz0uri4
                           https://www.youtube.com/watch?v=CPottYpf9ag

JList                     :https://www.youtube.com/watch?v=9Ejx6YCnYmk
Database                  :https://www.youtube.com/watch?v=fbYxThOFsLI
                           https://www.youtube.com/watch?v=BCqW5XwtJxY
Setup Appserv   `         :https://www.youtube.com/watch?v=NVgYOn5QvAE
                           https://www.youtube.com/watch?v=Nic73WekoFs&spfreload=10%20Message%3A%20Unexpected%20end%20of%20input%20(url%3A%20https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DNic73WekoFs)


*/
package chatroomserver;

//import yahoomessage.*;
import java.net.*;
import java.lang.Thread;
/* Team Information

*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)

*/
class Server extends Thread
{
    ServerProcessing [] listClient;
    public DriverDatabase database;
    public ListRoomChat listRoom;
    int index=0;
	public void run()
	{
            //loop and listren connnect from client
            listClient=new ServerProcessing[10];//the max number of client to connect
		try
		{
			ServerSocket server=new ServerSocket(8080);//sever listien on port 8080
			System.out.println("Server is running now....");
                        System.out.println("Go ahead and run the client App.java....");
			while(true)
			{
                            Socket s=server.accept();
                            System.out.println("Welcome Client");
                            //if has a connect from client then create a Severprocessing process it
                            ServerProcessing pro=new ServerProcessing(s,this);
                            if(index<listClient.length)
                            {
                                listClient[index]=pro;
                                listClient[index].start();
                                index++;
                            }
			}
			
		}
		catch(Exception ae){
			System.out.println(ae);
		}
	}
        public int getClient(String name)
        {
            //get processerserver to process client has name is name
            for(int i=0;i<index;i++)
            {
            	
                if(listClient[i].name.trim().toLowerCase().equals(name.trim().toLowerCase())) return i;
            }
            return -1;
        }
        public void sendMessage(String address,String msg)
        {
            // send a msg to client with address
            int i=getClient(address);
            if(i>=0)
            {
            	listClient[i].sendMessage(msg);
            }
        }
        public  void sendMessageAll(String msg)
        {
            // send message to all client
            for(int i=0;i<listClient.length;i++)
                listClient[i].sendMessage(msg);
        }
        public static void main(String[] args)
        {
            try
            {
                DriverDatabase database=new DriverDatabase("127.0.0.1","chatroom", "root", "admin");
                Server s=new Server();
                s.database=database;
                s.listRoom=new ListRoomChat();
                s.start();
            }
            catch(Exception ae){}
        }
        
}
