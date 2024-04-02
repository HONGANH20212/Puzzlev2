package com.example.puzzle;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class DoubleTapHandler extends GestureDetector.SimpleOnGestureListener {

    private MainActivity activity;
    private PuzzleView puzzleView;
    private Puzzle puzzle;
    private int statusBarHeight;
    private int actionBarHeight;

    public DoubleTapHandler(MainActivity activity, PuzzleView puzzleView, Puzzle puzzle, int statusBarHeight, int actionBarHeight) {
        this.activity = activity;
        this.puzzleView = puzzleView;
        this.puzzle = puzzle;
        this.statusBarHeight = statusBarHeight;
        this.actionBarHeight = actionBarHeight;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        int touchY = (int) event.getRawY();
        // Find the TextView that was touched
        View touchedView = puzzleView.getChildAt(touchY - actionBarHeight - statusBarHeight);
        // Get its index in the PuzzleView
        int index = puzzleView.indexOfTextView(touchedView);
        if (index != -1 && puzzle.getTextViewText(index).equals(puzzle.wordToChange())) {
            puzzle.setTextViewText(index, puzzle.replacementWord());
        }
        return true;
    }
}
