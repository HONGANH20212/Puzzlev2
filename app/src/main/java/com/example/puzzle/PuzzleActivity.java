package com.example.puzzle;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import androidx.appcompat.app.AppCompatActivity;

public class PuzzleActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String TAG = "PuzzleActivity";

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detector = new GestureDetector(this, this);
        detector.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w(TAG, "Inside onTouchEvent");
        detector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onFling(MotionEvent el, MotionEvent e2, float velocityX, float velocityY) {
        Log.w(TAG, "Inside onFling");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.w(TAG, "Inside onDown");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.w(TAG, "Inside onLongPress");
    }

    @Override
    public boolean onScroll(MotionEvent el, MotionEvent e2, float distanceX, float distanceY) {
        Log.w(TAG, "Inside onScroll");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.w(TAG, "Inside onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.w(TAG, "Inside onSingleTapUp");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.w(TAG, "Inside onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.w(TAG, "Inside onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.w(TAG, "Inside onSingleTapConfirmed");
        return true;
    }
}