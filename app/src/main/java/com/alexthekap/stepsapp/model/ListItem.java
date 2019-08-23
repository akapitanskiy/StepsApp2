package com.alexthekap.stepsapp.model;

// POJO for stepsList item
public class ListItem {
    private int aerobic;
    private long date;
    private int run;
    private int walk;

    public int getAerobic() {
        return aerobic;
    }
    public long getDate() {
        return date;
    }
    public int getRun() {
        return run;
    }
    public int getWalk() {
        return walk;
    }

    public void setAerobic(int aerobic) {
        this.aerobic = aerobic;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public void setRun(int run) {
        this.run = run;
    }
    public void setWalk(int walk) {
        this.walk = walk;
    }
}
