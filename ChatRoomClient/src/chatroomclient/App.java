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

/* Team Information

*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)

*/

package chatroomclient;



public class App extends Thread
{
    public String name="noname";
    public Chat chat;
    public ChatRoom chatroom;
    public Login login;
    public MessageChat msgchat;
    public NewChatRoom newchat;
    public Register register;
    public Client client;
    public DriverDatabase database;
    public String ipServer;
    public int port;
    public String dbname;
    public void run()
    {
        while(true)
        {
            if(client!=null&&client.client.isClosed()) return;
        }
        
    }
    
    public static void main(String args[])
    {
        /*The message is: name|command|IDroom|message
        =>to login send message: name|@login|0|username|password
        =>to register send message: name|@register|0|username|password
        =>to create room send message:name|@create|0|roomname
        =>to logout send message: name|@logout|0|logout
        =>to chat send message: name|@chat|idroom|message
        */
        App app=new App();
        app.ipServer="127.0.0.1";
        app.port=8080;
        app.dbname="chatroom";
        //Step 1 open login 
        app.login=new Login();
        app.login.setApp(app);
        app.login.show();
        app.start();
        System.out.println("Start client!");
        
    }
    
}
