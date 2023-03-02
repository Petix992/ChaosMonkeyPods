package com.myproject;


import java.util.Timer;
import java.util.TimerTask;


public class App 
{
    public static void main( String[] args ){
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ChaosMonkeyPod chaosMonkeyPod = new ChaosMonkeyPod();
                chaosMonkeyPod.killPod();
            }
        }, 0, 10000);            
        }
    }

