package cg.edukids.drawing.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.Nullable;

import cg.edukids.drawing.general.General;
import cg.edukids.drawing.utils.FloodFill;

public class DrawView extends View {

    private Bitmap bitmap;
    private float mPositionX, mPositionY;
    private float refX, refY;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.0f;
    private final static float mMinZoom = 1.0f;
    private final static float mMaxZoom = 5.0f;

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{ //Zoom
        @Override
        public boolean onScale(ScaleGestureDetector detector){
            mScaleFactor = detector.getScaleFactor();
            mScaleFactor = Math.max(mScaleFactor, Math.min(mScaleFactor,mMaxZoom));
            invalidate();

            return true;
        }
    }

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //mScaleDetector = new ScaleGestureDetector(context, new ScaleListener()); //Zoom
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), General.PICTURE_SELECTED);
        bitmap = Bitmap.createScaledBitmap(srcBitmap,w,h, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0,0,null);
        //drawBitmap(canvas); //folosit pentru Zoom
    }

    private void drawBitmap(Canvas canvas) { //Zoom
        canvas.save();
        canvas.translate(mPositionX, mPositionY);
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        paint( (int)event.getX(), (int)event.getY());
        /*mScaleDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                refX = event.getX();
                refY = event.getY();
                paint((int)((refX - mPositionX)/mScaleFactor), (int)((refY - mPositionY)/mScaleFactor));
                break;
            case MotionEvent.ACTION_MOVE:
                float nX = event.getX();
                float nY = event.getY();
                mPositionX += nX - refX;
                mPositionY += nY - refY;
                refX = nX;
                refY = nY;

                invalidate();
        }*/ // folosit pentro Zoom
        return true;
    }

    private void paint(int x, int y) {

        if(x<0 || y<0 || x>=bitmap.getWidth() || y>=getHeight()){
            return;
        }

        int targetColor = bitmap.getPixel(x,y);
        FloodFill.floodFill(bitmap,new Point(x,y),targetColor, General.COLOR_SELECTED);
        invalidate();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
