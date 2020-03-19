package app.game2048;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * @author revijanoca
 * June 2019
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener
{
    private final int max_columns = 4;
    private final int max_rows = 4;
    private boolean winner;
    private Board board;
    private android.support.v7.widget.GridLayout layout;
    private TextView[][] textViewArray;
    private TextView text_score;
    private Button restart_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        winner = false;
        initializate();
        layout = findViewById(R.id.grid);
        text_score = findViewById(R.id.score);
        restart_button = findViewById(R.id.restart);
        restart_button.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View view)
            {
                board = new Board(max_rows,max_columns);
                updateBoard();
                winner = false;
            }
        });
        setOnSwipe();
        updateBoard();




    }

    private void updateBoard()
    {
        //pasar los datos de una matriz de tipo int
        // a una matriz de tipo TextView
       int [][] matrix = board.getMatrix();
        for(int i =0;i<max_rows;i++)
            for(int u=0;u<max_columns;u++) {
                if(matrix[i][u] == 0)
                    textViewArray[i][u].setText("");
                else
                    textViewArray[i][u].setText(Integer.toString(matrix[i][u]));
            }

            text_score.setText("Marcador:  +"+Integer.toString(board.getScore()));
            updateColors(matrix);

        //mostrar mensaje de has ganado mientras no haya ganador


        if(! winner)
        {
            for(int i =0;i<max_rows;i++)
                for(int u=0;u<max_columns;u++)
                {
                    if(matrix[i][u] == 2048 )
                    {
                        winner = true;
                        Toast.makeText(getApplicationContext(), "¡Has ganado! Puedes seguir jugando...", Toast.LENGTH_LONG).show();
                    }
                }
        }





    }

    private void updateColors(int [][] matrix)
    {

        for(int i =0;i<max_rows;i++)
            for(int u=0;u<max_columns;u++)
            {

                switch(matrix[i][u])
                {
                    case 2:
                        textViewArray[i][u].setBackgroundColor(0xFFE6E5D9);
			            textViewArray[i][u].setTextColor(0xFF948A80);
                        break;

                    case 4:
                        textViewArray[i][u].setBackgroundColor(0xFFF8F5D1);
			            textViewArray[i][u].setTextColor(0xFF948A80);
                        break;

                    case 8:
                        textViewArray[i][u].setBackgroundColor(0xFFFFC694);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;

                    case 16:
                        textViewArray[i][u].setBackgroundColor(0xFFFDB575);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;

                    case 32:
                        textViewArray[i][u].setBackgroundColor(0xFFFE7263);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;

                    case 64:
                        textViewArray[i][u].setBackgroundColor(0xFFFD4531);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;

                    case 128:
                        textViewArray[i][u].setBackgroundColor(0xFFFDE68C);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;

                    case 256:
                        textViewArray[i][u].setBackgroundColor(0xFFFADF71);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;

                    case 512:
                        textViewArray[i][u].setBackgroundColor(0xFFFCDA55);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;

                    case 1024:
                        textViewArray[i][u].setBackgroundColor(0xFFF9D441);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;


                    case 2048:
                        textViewArray[i][u].setBackgroundColor(0xFFFFD52B);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;

                    case 4098:
                        textViewArray[i][u].setBackgroundColor(0xFF101010);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;

                    case 8196:
                        textViewArray[i][u].setBackgroundColor(0xFF6C068A);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;

                    case 16392:
                        textViewArray[i][u].setBackgroundColor(0xFF409305);
                        textViewArray[i][u].setTextColor(Color.WHITE);
                        break;


                    default:
                        textViewArray[i][u].setBackgroundColor(0xFFACA094);
                        break;
                }
            }
    }

    private void setOnSwipe()
    {
        layout.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext())
        {
            @Override
            public void onSwipeRight()
            {
                boolean right_movement = board.moveToRight();

                if(!board.isGameOver() & right_movement)
                {
                    board.setNumber();
                    updateBoard();
                }


            }

            @Override
            public void onSwipeLeft()
            {
                boolean left_movement = board.moveToLeft();

                if(!board.isGameOver() & left_movement)
                {
                    board.setNumber();
                    updateBoard();
                }

            }

            @Override
            public void onSwipeUp()
            {
                boolean up_movement = board.moveToUp();

                if(!board.isGameOver() & up_movement)
                {
                    board.setNumber();
                    updateBoard();
                }


            }

            @Override
            public void onSwipeDown()
            {
                boolean down_movement = board.moveToDown();

                if(!board.isGameOver() & down_movement)
                {
                    board.setNumber();
                    updateBoard();
                }

            }


        });
    }

    private void initializate()
    {
        initializateTextView();
        initializateMatrix();
    }

    private void initializateMatrix()
    {
        board = new Board(max_rows,max_columns);
    }

    private void initializateTextView()
    {
        textViewArray = new TextView[max_rows][max_columns];

        textViewArray [0][0] = findViewById(R.id.t11);
        textViewArray [0][1] = findViewById(R.id.t12);
        textViewArray [0][2] = findViewById(R.id.t13);
        textViewArray [0][3] = findViewById(R.id.t14);

        textViewArray [1][0] = findViewById(R.id.t21);
        textViewArray [1][1] = findViewById(R.id.t22);
        textViewArray [1][2] = findViewById(R.id.t23);
        textViewArray [1][3] = findViewById(R.id.t24);;

        textViewArray [2][0] = findViewById(R.id.t31);
        textViewArray [2][1] = findViewById(R.id.t32);
        textViewArray [2][2] = findViewById(R.id.t33);
        textViewArray [2][3] = findViewById(R.id.t34);

        textViewArray [3][0] = findViewById(R.id.t41);
        textViewArray [3][1] = findViewById(R.id.t42);
        textViewArray [3][2] = findViewById(R.id.t43);
        textViewArray [3][3] = findViewById(R.id.t44);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        /*
        new OnSwipeTouchListener(getApplicationContext())
        {

            @Override
            public void onSwipeRight()
            {
                Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeLeft()
            {
                Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeUp()
            {
                Toast.makeText(getApplicationContext(), "up", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeDown()
            {
                Toast.makeText(getApplicationContext(), "down", Toast.LENGTH_SHORT).show();
            }
        };*/


        return true;
    }
}
