package cg.edukids.learn.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.mlkit.vision.digitalink.Ink;

public class CustomDrawingSurfaceAlphabet extends View {
    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private Bitmap canvasBitmap;
    private Canvas drawCanvas;
    private Ink.Builder inkBuilder = new Ink.Builder();
    private Ink.Stroke.Builder currentStroke = Ink.Stroke.builder();

    public CustomDrawingSurfaceAlphabet(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (inkBuilder == null) {
                    inkBuilder = Ink.builder();
                }
                currentStroke = Ink.Stroke.builder();
                currentStroke.addPoint(Ink.Point.create(x, y, System.currentTimeMillis()));
                drawPath.moveTo(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                if (currentStroke != null) {
                    currentStroke.addPoint(Ink.Point.create(x, y, System.currentTimeMillis()));
                    drawPath.lineTo(x, y);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (currentStroke != null) {
                    inkBuilder.addStroke(currentStroke.build());
                    currentStroke = null;
                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath.reset();
                }
                break;
        }

        invalidate();
        return true;
    }

    public Ink getInk() {
        Ink ink = inkBuilder.build();
        if (ink.getStrokes().isEmpty()) {
            Log.e("CustomDrawingSurface", "Ink is empty! Make sure strokes are added.");
        } else {
            Log.i("CustomDrawingSurface", "Ink captured with " + ink.getStrokes().size() + " strokes.");
        }
        return ink;
    }

    public void setInk(Ink newInk) {
        inkBuilder = Ink.builder();
        for (Ink.Stroke stroke : newInk.getStrokes()) {
            inkBuilder.addStroke(stroke);
        }
        invalidate();
    }

    public void clearCanvas() {
        drawCanvas.drawColor(Color.LTGRAY);
        inkBuilder = Ink.builder(); // RESET complet la Ink
        invalidate();
    }
}
