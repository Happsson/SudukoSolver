package nu.geeks.sodukosolver;

import java.util.ArrayList;

/**
 * Created by hannespa on 15-04-25.
 */
public class Position {

    private int x;
    private int y;
    private ArrayList<Integer> solutions;
    private boolean positionFilled;


    public Position(int[] xy){
        if(xy.length == 2){
            x = xy[0];
            y = xy[1];
            positionFilled = false;
            solutions = new ArrayList<Integer>();

        }else{
            //TODO - error
        }



    }

    public void setPositionFilled(){
        positionFilled = true;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        positionFilled = false;
        solutions = new ArrayList<Integer>();

    }

    public void addSolution(int value){
        solutions.add(value);
    }

    public ArrayList<Integer> getSolutions(){
        return solutions;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPositionFilledWith(int value) {
        solutions.clear();
        solutions.add(value);
        positionFilled = true;
    }
}
