package gasyidea.org.mobilereport.models;

public class SmsAlert {

    private Integer id;
    private String ip;
    private String date;
    private String attack;
    private Integer status;
    private String codeSoluce;

    public SmsAlert() {
    }

    public SmsAlert(String ip, String date, String attack, Integer status, String codeSoluce) {
        this.ip = ip;
        this.date = date;
        this.attack = attack;;
        this.status=status;
        this.codeSoluce = codeSoluce;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCodeSoluce() {
        return codeSoluce;
    }

    public void setCodeSoluce(String codeSoluce) {
        this.codeSoluce = codeSoluce;
    }
}
