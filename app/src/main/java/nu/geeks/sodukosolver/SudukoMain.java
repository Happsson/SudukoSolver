package nu.geeks.sodukosolver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 *
 *      Soduko solver.
 *
 *  Design of the grid:
 *
 *  [0:0][0:1][0:2] [0:3][0:4][0:5] [0:6][0:7][0:8]
 *  [1:0][1:1][1:2] [1:3][1:4][1:5] [1:6][1:7][1:8]
 *  [2:0][2:1][2:2] [2:3][2:4][2:5] [2:6][2:7][2:8]
 *
 *  [3:0][3:1][3:2] [3:3][3:4][3:5] [3:6][3:7][3:8]
 *  [4:0][4:1][4:2] [4:3][4:4][4:5] [4:6][4:7][4:8]
 *  [5:0][5:1][5:2] [5:3][5:4][5:5] [5:6][5:7][5:8]
 *
 *  [6:0][6:1][6:2] [6:3][6:4][6:5] [6:6][6:7][6:8]
 *  [7:0][7:1][7:2] [7:3][7:4][7:5] [7:6][7:7][7:8]
 *  [8:0][8:1][8:2] [8:3][8:4][8:5] [8:6][8:7][8:8]
 *
 */


public class SudukoMain extends Activity {

    Button solve;

    int[][] grid = new int[9][9];

    TextView tGrid;

    Sudoku sudoku;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suduko_main);



        // O(N^2)
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                grid[i][j] = 0;
            }
        }


        int[][] gN  = {
                {6,0,0, 0,2,0, 0,0,7},
                {0,0,0, 3,0,8, 0,1,0},
                {0,0,5, 0,0,0, 8,0,0},

                {0,1,0, 8,0,0, 0,4,0},
                {0,0,0, 0,5,0, 0,0,0},
                {0,7,0, 0,0,9, 0,2,0},

                {0,0,1, 0,0,0, 3,0,0},
                {0,2,0, 6,0,4, 0,0,0},
                {8,0,0, 0,7,0, 0,0,9},
        };

        grid = gN;




        tGrid = (TextView) findViewById(R.id.tGrid);

        tGrid.setText(updateGrid());

        solve = (Button) findViewById(R.id.bSolve);


        sudoku = new Sudoku(grid);

        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grid = sudoku.findSolution();
                //Update the view
                tGrid.setText(updateGrid());
            }
        });


    }


    private String updateGrid(){
        String st = "";
        for(int x = 0; x < 9; x++){
            st += "\n";
            if(x == 3 || x == 6) st += "\n";
            for(int y = 0; y < 9; y++){
                if(y == 3 || y == 6) st += "\t\t";

                if(grid[x][y] != 0) st += "" + grid[x][y];
                else st += 'X';
             }

        }
        return st;
    }
}
