package com.wtist.cristo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ce on 2015/10/8.
 */
public class CommandThread implements Runnable, ActionListener {
    private boolean flag = true;
    private ClockDisplay clockDisplay = null;
    private JLabel label = null;
    private Thread t = null;
    private boolean hasStart = false;
    private boolean start = false;

    public CommandThread(ClockDisplay clockDisplay, JLabel label) {
        this.clockDisplay = clockDisplay;
        this.label = label;
        t = new Thread(this);
    }

    @Override
    public void run() {
        while (flag) {
            try {
                start();
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void start() {
        synchronized (clockDisplay) {
            if (start) {
                label.setText(clockDisplay.refresh());
            } else {
                try {
                    clockDisplay.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void close() {
        this.flag = false;
    }

    private void command(String type) {
        synchronized (clockDisplay) {
            if (type.equalsIgnoreCase("start")) {
                start = true;
                if (!hasStart) {
                    t = new Thread(this);
                    hasStart = true;
                    t.start();
                }
                clockDisplay.notify();
            } else if (type.equalsIgnoreCase("clear")) {
                start = false;
                clockDisplay.clear();
                label.setText(clockDisplay.toString());
            } else {
                start = false;
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        command(e.getActionCommand());
    }
}
