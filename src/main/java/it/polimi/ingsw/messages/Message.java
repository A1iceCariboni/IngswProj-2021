package it.polimi.ingsw.messages;

public class Message {
      private  MessageType code;
      private  String payload;

      public Message(MessageType code, String payload){
          this.code = code;
          this.payload = payload;
      }

    public MessageType getCode() {
        return code;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload){
          this.payload = payload;
    }
}

