package test;

import java.util.Date;

public class Message {
    public final byte[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;

   public Message(double value) {
        this(Double.toString(value));
    }

    public Message(byte[] data) {
        this(new String(data));
    }

    public Message(String text) {
        this.asText = text;
        this.data = text.getBytes();

        double parsed;
        try {
            parsed = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            parsed = Double.NaN;
        }
        this.asDouble = parsed;

        this.date = new Date();
    }
}

