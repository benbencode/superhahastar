package com.superhahastar.benben.pojo;

/**
 * @author lijie.zhang
 * @date 2021/10/25 14:49
 */
public class PayLog {

    public long accountId;
    public double amount;
    public long buessTime;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getBuessTime() {
        return buessTime;
    }

    public void setBuessTime(long buessTime) {
        this.buessTime = buessTime;
    }

    @Override
    public String toString() {
        return "PayLog{" +
                "accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", buessTime=" + buessTime +
                '}';
    }
}
