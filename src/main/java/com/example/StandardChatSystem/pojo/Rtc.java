package com.example.StandardChatSystem.pojo;
class SdpData {
    public String type;
    public String sdp;

    @Override
    public String toString() {
        return "{" +
                "type='" + type + '\'' +
                ", sdp='" + sdp + '\'' +
                '}';
    }

    public String getSdp() {
        return sdp;
    }

    public void setSdp(String sdp) {
        this.sdp = sdp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
public class Rtc {

    private String type;
    public SdpData sdp;
    private String from;
    private String to;
    private String candidate;

    @Override
    public String toString() {
        System.out.println("{" +
                "type='" + type + '\'' +
                ", sdp=" + sdp +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", candidate='" + candidate + '\'' +
                '}');
        return "{" +
                "type='" + type + '\'' +
                ", sdp=" + sdp +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", candidate='" + candidate + '\'' +
                '}';

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SdpData getSdp() {
        return sdp;
    }

    public void setSdp(SdpData sdp) {
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

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }
}
