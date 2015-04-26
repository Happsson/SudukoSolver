package nu.geeks.sodukosolver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hannespa on 15-04-26.
 */
public class Sudoku {

    int[][] grid;


    public Sudoku(int[][] grid) {
        this.grid = grid;
    }

    public int[][] findSolution() {

        Position[][] possibleSolutionsMatrix = new Position[9][9];


        //First, we just make one position matrix for alla the possible values, so we can add to these later
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                possibleSolutionsMatrix[i][j] = new Position(i, j);
            }
        }

        mainLoop:
        while (!isItSolvetYet() ) {

            //We need to know it if we have found any solutions with this algorithm. If not, we need to use another approach.
            int addedValuesThisRound = 0;


            //For all nine squares
            for (int squareToCheck = 0; squareToCheck < 9; squareToCheck++) {

                //Get the world coordinates for this square
                int[] thisSquareWorldCoordinates = getXYfromSquare(squareToCheck);

                //Check if there are any numbers missing
                ArrayList<Integer> missingNumbersInSquare = missingInSquare(squareToCheck);

                //If there are any numbers missing
                if (missingNumbersInSquare.size() > 0) {

                    //Get the possible positions for those missing numbers
                    boolean positionsAvailableInSquare[][] = freePositionsOnThisSquare(squareToCheck);


                    //For all the missing numbers
                    for (Integer missing : missingNumbersInSquare) {


                        //Keep track of how many solutions is found for this number in this square
                        int numberOfPossibleSolutions = 0;

                        //Keep track of the last of those found solutions.
                        int XYcoordinatesOfSolution[] = new int[2];


                        //Check each position
                        for (int x = 0; x < 3; x++) {
                            for (int y = 0; y < 3; y++) {

                                //Calculate the actual world coordinate for this position
                                int[] currentWorldCoordinate = {
                                        thisSquareWorldCoordinates[0] + x,
                                        thisSquareWorldCoordinates[1] + y
                                };

                                //If position is avalible
                                if (positionsAvailableInSquare[x][y] == true) {



                                    //To make sure the solutions for this position in the position-matrix is up to date, clear it.
                                    Position thisPosition = possibleSolutionsMatrix[currentWorldCoordinate[0]][currentWorldCoordinate[1]];
                                    thisPosition.getSolutions().clear();

                                    //Check if the missing number can be on this position
                                    if (canValueBeOnTheseRows(missing, currentWorldCoordinate)) {
                                        //Add this as a possible solution.
                                        thisPosition.addSolution(missing);

                                        numberOfPossibleSolutions++;
                                        XYcoordinatesOfSolution = currentWorldCoordinate;


                                    }

                                }else{
                                    //This position is already set. Make sure the position matrix is up to date.
                                    Position current = possibleSolutionsMatrix[currentWorldCoordinate[0]][currentWorldCoordinate[1]];

                                    //mark this position as filled, and fill it with the value found in the grid at this position.
                                    current.setPositionFilledWith(
                                            grid[
                                                    currentWorldCoordinate[0]]
                                                    [currentWorldCoordinate[1]]
                                    );
                                }

                            }
                        }

                        //If only one possible solution for this number was found, add value to this position
                        if (numberOfPossibleSolutions == 1) {
                            addedValuesThisRound++;
                            grid[XYcoordinatesOfSolution[0]][XYcoordinatesOfSolution[1]] = missing;

                            //Mark this position as filled.
                            possibleSolutionsMatrix[XYcoordinatesOfSolution[0]][XYcoordinatesOfSolution[1]].setPositionFilled();

                        }

                    }


                }




                if (addedValuesThisRound == 0) {
                    //No new values was added this round.


                }
            }
        }

        return grid;


    }

    private void inDepthAnalysis(int square) {

        Position[][] solutionsInThisSquare = new Position[3][3];



    }

    private int doesLineXhaveNumber(int number, int lineXCoord){

        int ret = -1;

        for(int i = 0; i < 9; i++){
            if(grid[lineXCoord][i] == number) return i;
        }
        return ret;
    }

    private int doesLineYhaveNumber(int number, int lineYCoord){

        int ret = -1;

        for(int i = 0; i < 9; i++){
            if(grid[lineYCoord][i] == number) return i;
        }
        return ret;
    }

    private boolean isRowOfValueSet(int square){



        return false;
    }


    /**
     * Checks a row on the X-axis.
     * Returns an list with all unused values in this X-row
     *
     *  O(N)
     *  (where N is 9*3)
     *
     * @param lineXcoord
     * @return
     */
    private ArrayList<Integer> missingOnXLine(int lineXcoord){

        //New arraylist, will hold what values ar NOT used on this line.
        ArrayList<Integer> returnValues = new ArrayList<Integer>();

        int[] values = new int[9];


        //Array value holds the number 1-9 in it's indecies +1.
        for(int i = 0; i < 9; i++) values[i] = i+1;


        for(int i = 0; i < 9; i++){

            //If the position has a value, mark that this value is used in the value-array by setting it to 0.
            if(grid[lineXcoord][i] != 0) values[grid[lineXcoord][i]-1] = 0;
        }

        for(int i = 0; i < 9; i++){
            if(values[i] != 0){
                //if value is 0 at this point, this value was not used on the row
                returnValues.add(i+1);
            }
        }

        return returnValues;
    }

    /**
     * Checks a row on the Y-axis.
     * Returns an list with all unused values in this X-row
     *
     *  O(N)
     *  (where N is 9*3)
     *
     * @param lineYcoord
     * @return
     */
    private ArrayList<Integer> missingOnYLine(int lineYcoord){


        //New arraylist, will hold what values ar NOT used on this line.
        ArrayList<Integer> returnValues = new ArrayList<Integer>();

        int[] values = new int[9];


        //Array value holds the number 1-9 in it's indecies +1.
        for(int i = 0; i < 9; i++) values[i] = i+1;


        for(int i = 0; i < 9; i++){

            //If the position has a value, mark that this value is used in the value-array by setting it to 0.
            if(grid[i][lineYcoord] != 0) values[grid[i][lineYcoord]-1] = 0;
        }

        for(int i = 0; i < 9; i++){
            if(values[i] != 0){
                //if value is 0 at this point, this value was not used on the row
                returnValues.add(i+1);
            }
        }

        return returnValues;
    }

    /**
     * Checks a square.
     * Returns what numbers aren't used in this square.
     *
     *  7   8   9
     *  4   5   6
     *  1   2   3
     *
     * @param square
     * @return
     */
    private ArrayList<Integer> missingInSquare(int square){

        //ReturnValues, will hold the numbers NOT in this square.
        ArrayList<Integer> returnValues = new ArrayList<Integer>();

        //Get world coordinates for this square.
        int[] xy = getXYfromSquare(square);

        //each index is representing the corresponding value
        boolean[] hasValue = new boolean[9];
        for(int i = 0; i < 9; i++) hasValue[i] = false;

        for(int x = xy[0]; x < xy[0]+3; x++){
            for(int y = xy[1]; y < xy[1]+3; y++){

                int valueAtPosition = grid[x][y];
                if(valueAtPosition != 0){
                    hasValue[valueAtPosition-1] = true;
                }

            }
        }

        for(int i = 0; i < 9; i++ ) {
            if(!hasValue[i]) returnValues.add(i+1);

        }

        return returnValues;

    }



    private int[][] getSquareFromXY(Position xy){
        int[][] square = new int[3][3];
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                square[x][y] = grid[xy.getX()+x][xy.getY()+y];
            }
        }
        return square;
    }

    /**
     * Checks if there are any zeros left in the grid. If so, return false.
     * No zeros means we have solved the puzzle, return true.
     *
     * @return
     */
    private boolean isItSolvetYet(){
        for(int i = 0 ; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(grid[i][j] == 0) return false;
            }

        }
        return true;

    }

    /**
     *  0   1   2
     *  3   4   5
     *  6   7   8
     *
     * @param square
     * @return
     */
    private int[] getXYfromSquare(int square){
        int x = -1;
        int y = -1;
        int[] xy = new int[2];
        switch(square){
            case 0:
                x = 0;
                y = 0;
                break;
            case 1:
                x = 0;
                y = 3;
                break;
            case 2:
                x = 0;
                y = 6;
                break;
            case 3:
                x = 3;
                y = 0;
                break;
            case 4:
                x = 3;
                y = 3;
                break;
            case 5:
                x = 3;
                y = 6;
                break;
            case 6:
                x = 6;
                y = 0;
                break;
            case 7:
                x = 6;
                y = 3;
                break;
            case 8:
                x = 6;
                y = 6;
                break;
            default:
                x = -1;
                y = -1;
                break;
        }
        xy[0] = x;
        xy[1] = y;

        return xy;
    }

    /**
     * Returns a boolean matrix corresponding to the given square.
     * the indices that are true does not yet contain a value.
     *
     *
     * @param square
     * @return
     */
    private boolean[][] freePositionsOnThisSquare(int square){

        Position xy = new Position(getXYfromSquare(square));

        int sq[][] = getSquareFromXY(xy);

        boolean[][] freePositions = new boolean[3][3];

        for(int x = 0 ; x < 3 ; x++){
            for(int y = 0 ; y < 3 ; y++){
                freePositions[x][y] = (sq[x][y] == 0);
            }
        }
        return freePositions;
    }

    /**
     * Checks if the rows/column allows for this value
     * to be in this position.
     *
     * @param value
     * @param pos
     * @return true if the value is missing on row and column
     */
    private boolean canValueBeOnTheseRows(int value, int[] pos){

        boolean trueForX = false;
        boolean trueForY = false;
        ArrayList<Integer> xValues = missingOnXLine(pos[0]);
        ArrayList<Integer> yValues = missingOnYLine(pos[1]);

        for(Integer i : xValues){
            if(i == value) trueForX = true;
        }
        for(Integer i : yValues){
            if(i == value ) trueForY = true;
        }

        return trueForX && trueForY;

    }
}
