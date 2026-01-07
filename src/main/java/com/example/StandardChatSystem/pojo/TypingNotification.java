package com.example.StandardChatSystem.pojo;

public class TypingNotification {
    private String from;
    private String to;
    private boolean isTyping; // true = typing, false = stopped

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public boolean isTyping() {
        return isTyping;
    }

    @Override
    public String toString() {
        return "{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", isTyping=" + isTyping +
                '}';
    }

    public void setTyping(boolean typing) {
        isTyping = typing;

    }
}