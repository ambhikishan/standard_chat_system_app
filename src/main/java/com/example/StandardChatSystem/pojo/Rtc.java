package com.example.StandardChatSystem.pojo;

public class Rtc {

    private String type;
    private String sdp;
    private String from;
    private String to;

    @Override
    public String toString() {
        return "{" +
                "type='" + type + '\'' +
                ", sdp='" + sdp + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSdp() {
        return sdp;
    }

    public void setSdp(String sdp) {
        this.sdp = sdp;
    }

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
}
