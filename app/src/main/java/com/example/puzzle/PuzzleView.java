package com.example.puzzle;


import java.util.Random;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PuzzleView extends RelativeLayout {
    private TextView[] tvs;
    private RelativeLayout.LayoutParams[] params;
    private int[] colors;
    private int labelHeight;
    private int startY;
    private int startTouchY;
    private int emptyPosition;
    private int[] positions;
    private Puzzle puzzle;
    private int statusBarHeight;
    private int actionBarHeight;

    private int minFontSize;

    // Constructor để truyền vào đối tượng Puzzle
    public PuzzleView(Activity activity, int width, int height, int numberOfPieces, Puzzle puzzle, int statusBarHeight, int actionBarHeight) {
        super(activity);
        this.puzzle = puzzle; // Lưu đối tượng Puzzle
        this.statusBarHeight = statusBarHeight;
        this.actionBarHeight = actionBarHeight;
        buildGuiByCode(activity, width, height, numberOfPieces);
    }

    private void buildGuiByCode(Activity activity, int width, int height, int numberOfPieces) {
        positions = new int[numberOfPieces];
        tvs = new TextView[numberOfPieces];
        colors = new int[tvs.length];
        params = new RelativeLayout.LayoutParams[tvs.length];
        Random random = new Random();
        labelHeight = height / numberOfPieces;

        for (int i = 0; i < tvs.length; i++) {
            tvs[i] = new TextView(activity);
            tvs[i].setGravity(Gravity.CENTER);
            colors[i] =  Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            tvs[i].setBackgroundColor(colors[i]);
            params[i] = new RelativeLayout.LayoutParams(width, labelHeight);
            params[i].leftMargin = 0;
            params[i].topMargin = labelHeight * i;
            addView(tvs[i], params[i]);
            positions[i] = i;
        }
    }

    public void fillGui(String [] scrambledText) {
        minFontSize = DynamicSizing.MAX_FONT_SIZE; // Khởi tạo minFontSize
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setText(scrambledText[i]);
            positions[i] = i;

            tvs[i].setWidth(params[i].width);
            tvs[i].setPadding(20,5,20,5);

            //find font size
            int fontSize = DynamicSizing.setFontSizeToFitInView( tvs[i]);
            if(minFontSize > fontSize)
                minFontSize = fontSize;

            // Đặt chữ in đậm
            tvs[i].setTypeface(null, Typeface.BOLD);
        }
        for(int i = 0; i < tvs.length; i++)
            tvs[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, minFontSize);
    }

    public int indexOfTextView(View tv) {
        if (!(tv instanceof TextView))
            return -1;
        for (int i = 0; i < tvs.length; i++) {
            if (tv == tvs[i])
                return i;
        }
        return -1;
    }

    public void updateStartPositions(int index, int y) {
        startY = params[index].topMargin;
        startTouchY = y;
        emptyPosition = tvPosition(index);
    }

    public void moveTextViewVertically(int index, int y) {
        params[index].topMargin = startY + y - startTouchY;
        tvs[index].setLayoutParams(params[index]);
    }

    public void enableListener(View.OnTouchListener listener) {
        for (TextView textView : tvs) {
            textView.setOnTouchListener(listener);
        }
    }

    public void disableListener() {
        for (TextView textView : tvs) {
            textView.setOnTouchListener(null);
        }
    }

    public int tvPosition(int tvIndex) {
        return (params[tvIndex].topMargin + labelHeight / 2) / labelHeight;
    }

    public void placeTextViewAtPosition(int tvIndex, int toPosition) {
        params[tvIndex].topMargin = toPosition * labelHeight;
        tvs[tvIndex].setLayoutParams(params[tvIndex]);

        int index = positions[toPosition];
        params[index].topMargin = emptyPosition * labelHeight;
        tvs[index].setLayoutParams(params[index]);

        positions[emptyPosition] = index;
        positions[toPosition] = tvIndex;
    }

    public String[] currentSolution() {
        String[] current = new String[tvs.length];
        for (int i = 0; i < current.length; i++)
            current[i] = tvs[positions[i]].getText().toString();
        return current;
    }

    // Trả về chỉ số của TextView mà vị trí của nó bao gồm y
    public int indexOfTextView(int y) {
        int position = y / labelHeight;
        return positions[position];
    }

    // Trả về văn bản bên trong TextView có chỉ số tvIndex
    public String getTextViewText(int tvIndex) {
        return tvs[tvIndex].getText().toString();
    }

    // Thay thế văn bản bên trong TextView có chỉ số tvIndex bằng s
    public void setTextViewText(int tvIndex, String s) {
        tvs[tvIndex].setText(s);
    }

    // Xử lý sự kiện double tap

    public boolean onDoubleTapEvent(MotionEvent event) {
        int touchY = (int) event.getRawY();
        // Tìm TextView đã được chạm
        View touchedView = getChildAt(touchY - actionBarHeight - statusBarHeight);
        // Lấy chỉ số của TextView trong PuzzleView
        int index = indexOfTextView(touchedView);
        if (index != -1) {
            String currentText = puzzle.getTextViewText(index).toString();
            String replacementText = puzzle.replacementWord();
            if (currentText.equals(puzzle.wordToChange())) {
                setTextViewText(index, replacementText);
            }
        }
        return true;
    }
}
