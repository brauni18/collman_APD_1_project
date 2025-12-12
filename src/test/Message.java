package test;

import java.util.Date;

public class Message {
    public final byte[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;

    public Message( String asText) {
        this.data = asText.getBytes();
        this.asText = asText;
        this.date = new Date();
        if((Double.parseDouble(asText)) != 0) {
            this.asDouble = Double.parseDouble(asText);
        }else{
            this.asDouble = 0;
        }
    }
    public Message(double asDouble) {
        this.asDouble = asDouble;
        this.date = new Date();
        this.asText = Double.toString(asDouble);
        this.data = this.asText.getBytes();
    }
    public Message(int asInt) {
        this.data = Integer.toString(asInt).getBytes();
        this.asText = Integer.toString(asInt);
        this.date = new Date();
        this.asDouble = asInt;
    }
    
}
