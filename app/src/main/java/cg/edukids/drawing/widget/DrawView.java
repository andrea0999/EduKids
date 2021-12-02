package cg.edukids.drawing.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import cg.edukids.drawing.general.General;
import cg.edukids.drawing.utils.FloodFill;

public class DrawView extends View {

    Bitmap bitmap;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        paint( (int)event.getX(), (int)event.getY());
        return true;
    }

    private void paint(int x, int y) {
        int targetColor = bitmap.getPixel(x,y);
        FloodFill.floodFill(bitmap,new Point(x,y),targetColor, General.COLOR_SELECTED);
        invalidate();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
