package com.sololearnmessage.sololearn.solomessage;

public class Message {
    private String userName;
    private String userMessage;

    public Message(String userName, String userMessage){
        this.userName = userName;
        this.userMessage = userMessage;
    }

    public String GetUserName(){
        return this.GetUserName(true);
    }

    public String GetUserName(boolean formatName){
        return userName + (formatName == true ? ": " : "");
    }

    public String GetUserMessage(){
        return userMessage;
    }
}
