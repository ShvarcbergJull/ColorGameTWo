package com.example.pc.colortilestwo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class TilesView extends View {
    int[][] tiles = new int[4][4];
    int darkColor = Color.GRAY;
    int lightColor = Color.YELLOW;
    int width;
    int height;
    MainActivity activity;

    public TilesView(Context context) {
        super(context);
        activity = (MainActivity) context;
        fillArrayData();
    }

    private void fillArrayData() {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int color = random.nextBoolean() ? darkColor : lightColor;
                tiles[i][j] = color;
            }
        }
    }

    public TilesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = (int) canvas.getWidth();
        height = (int) canvas.getHeight();
        Paint p = new Paint();
        int ic = 0;
        int jc = 0;
        for (int i = 0; i < height - height / 4; i += height / 4) {
            for (int j = 0; j < width; j += width / 4) {
                int color = tiles[ic][jc];
                p.setColor(color);
                canvas.drawRect(j, i, j + width / 4, i + height / 4, p);
                jc++;
            }
            ic++;
            jc = 0;
        }
    }

    private boolean is_win() {
        int color = tiles[0][0];
        for (int i = 0;i < 4;i++) {
            for (int j = 1; j < 4; j++) {
                if (tiles[i][j] != color)
                    return false;
            }
        }
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int)event.getX();
            int y = (int)event.getY();
            x /= (width / 4);
            y /= (height / 4);
            Log.d("lovecx", String.valueOf(x));
            Log.d("lovecy", String.valueOf(y));
            for (int i = 0;i < 4;i++)
            {
                tiles[i][y] = tiles[i][y]==darkColor?lightColor:darkColor;
                tiles[x][i] = tiles[x][i]==darkColor?lightColor:darkColor;
            }
            invalidate();
            if (is_win()) {
                Toast ending = Toast.makeText(activity, "Вы победили!", Toast.LENGTH_LONG);
                ending.show();
            }
        }
        return true;
    }
}
