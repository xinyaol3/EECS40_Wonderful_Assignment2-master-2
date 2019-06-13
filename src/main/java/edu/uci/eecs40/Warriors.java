package main.java.edu.uci.eecs40;

import java.util.*;

public class Warriors {
    private String name;
    private String headname;
    private int index;
    private Boolean Turn;
    private Boolean Alive;
    private Boolean Split;
    private Boolean Compare;
    private Warriors OriginalWarrior;
    private Warriors NewWarrior;
    private Warriors Head;

    public Warriors(String name, int index) {
        this.name = name;
        this.index = index;
        Turn = true;
        Alive = true;
        Split = false;
        Compare = false;
        OriginalWarrior = null;
        NewWarrior = null;
        Head = null;
    }

    public Boolean getCompare() {
        return Compare;
    }

    public void setCompare(Boolean compare) {
        Compare = compare;
    }

    public void setHeadname(String headname) {
        this.headname = headname;
    }

    public String getHeadname() {
        return headname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Boolean getTurn() {
        return Turn;
    }

    public void setTurn(Boolean turn) {
        Turn = turn;
    }

    public Boolean getAlive() {
        return Alive;
    }

    public void setAlive(Boolean alive) {
        Alive = alive;
    }

    public Boolean getSplit() {
        return Split;
    }

    public void setSplit(Boolean split) {
        Split = split;
    }

    public Warriors getOriginalWarrior() {
        return OriginalWarrior;
    }

    public void setOriginalWarrior(Warriors originalWarrior) {
        OriginalWarrior = originalWarrior;
    }

    public Warriors getNewWarrior() {
        return NewWarrior;
    }

    public void setNewWarrior(Warriors newWarrior) {
        NewWarrior = newWarrior;
    }

    public Warriors getHead() {
        return Head;
    }

    public void setHead(Warriors head) {
        Head = head;
    }
}



