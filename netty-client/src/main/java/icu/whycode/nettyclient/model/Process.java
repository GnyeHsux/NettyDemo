package icu.whycode.nettyclient.model;

public class Process {

    /**
     * 总量
     */
    private int amount;

    /**
     * 进度
     */
    private String rate;

    /**
     * 速度
     */
    private String speed;

    public Process() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Process{" +
                "amount=" + amount +
                ", rate='" + rate + '\'' +
                ", speed='" + speed + '\'' +
                '}';
    }
}
