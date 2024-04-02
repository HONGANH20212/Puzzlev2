package com.example.puzzle;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    public static int STATUS_BAR_HEIGHT = 24; // in dp
    public static int ACTION_BAR_HEIGHT = 56; // in dp

    private PuzzleView puzzleView;
    private Puzzle puzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        puzzle = new Puzzle();

        // Lấy kích thước màn hình
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        // Tính toán kích thước của câu đố và chiều cao của ActionBar và StatusBar
        int actionBarHeight = getActionBarHeight();
        int statusBarHeight = getStatusBarHeight();
        int puzzleWidth = screenWidth;
        int puzzleHeight = screenHeight - statusBarHeight - actionBarHeight;

        // Số lượng phần của câu đố
        int numberOfPieces = puzzle.getNumberOfParts();

        // Tạo PuzzleView và hiển thị câu đố
        puzzleView = new PuzzleView(this, puzzleWidth, puzzleHeight, numberOfPieces, puzzle, statusBarHeight, actionBarHeight);
        String[] scrambled = puzzle.scramble();
        puzzleView.fillGui(scrambled);
        puzzleView.enableListener(this);

        setContentView(puzzleView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int index = puzzleView.indexOfTextView(v);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Initialize data before move
                puzzleView.updateStartPositions(index, (int) event.getY());
                // Bring v to front
                puzzleView.bringChildToFront(v);
                break;
            case MotionEvent.ACTION_MOVE:
                // Update y position of TextView being moved
                puzzleView.moveTextViewVertically(index, (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                // Move is complete: swap the 2 TextViews
                int newPosition = puzzleView.tvPosition(index);
                puzzleView.placeTextViewAtPosition(index, newPosition);
                // If the user just won, disable listener to stop the game
                if (puzzle.solved(puzzleView.currentSolution())) {
                    puzzleView.disableListener();
                }
                break;
        }
        return true;
    }

    // Lấy kích thước của ActionBar từ hệ thống
    private int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        int actionBarHeight = (int) (56 * getResources().getDisplayMetrics().density);
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    // Lấy kích thước của StatusBar từ hệ thống
    private int getStatusBarHeight() {
        int statusBarHeight = (int) (24 * getResources().getDisplayMetrics().density);
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
