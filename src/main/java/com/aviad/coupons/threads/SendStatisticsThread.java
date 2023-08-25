package com.aviad.coupons.threads;

public class SendStatisticsThread extends Thread{
    private String text;

    public SendStatisticsThread(String text){
        this.text = text;
    }

    public void run(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.text);
    }
}
