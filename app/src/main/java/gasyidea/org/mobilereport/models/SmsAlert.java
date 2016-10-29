package gasyidea.org.mobilereport.models;

public class SmsAlert {

    private Integer id;
    private int maxScore;
    private int total;
    private Integer date;
    private String soluce;

    public SmsAlert() {
    }

    public SmsAlert(int maxScore, int total, Integer date, String soluce) {
        this.maxScore = maxScore;
        this.total = total;
        this.date = date;
        this.soluce = soluce;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getSoluce() {
        return soluce;
    }

    public void setSoluce(String soluce) {
        this.soluce = soluce;
    }
}
