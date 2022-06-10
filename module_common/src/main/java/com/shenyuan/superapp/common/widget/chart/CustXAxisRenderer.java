package com.shenyuan.superapp.common.widget.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import static com.github.mikephil.charting.utils.Utils.getSizeOfRotatedRectangleByRadians;

/**
 * @author ch
 * @date 2020/4/24-11:41
 * desc
 */
public class CustXAxisRenderer extends XAxisRenderer {
    private static final String CHAR_NEW_LINE = "\n";
    private int max;
    private Rect mDrawTextRectBuffer = new Rect();
    public final static float FDEG2RAD = ((float) Math.PI / 180.f);
    private Paint.FontMetrics mFontMetricsBuffer = new Paint.FontMetrics();

    public CustXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    @Override
    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
        if (isMultilineText(formattedLabel)) {
            TextPaint textPaint = new TextPaint(mAxisLabelPaint);
            textPaint.getTextBounds(formattedLabel, 0, formattedLabel.length(), mDrawTextRectBuffer);
            int yl = mDrawTextRectBuffer.bottom - mDrawTextRectBuffer.top;
            if (yl > max) {
                max = yl;
            }
            drawMultilineText(c, formattedLabel, x, y,
                    textPaint,
                    new FSize(c.getWidth(), c.getHeight()), new MPPointF(0f, 0f), 0);
        } else {
            Utils.drawXAxisValue(c, formattedLabel, x, y, mAxisLabelPaint, anchor, angleDegrees);
        }
    }

    private boolean isMultilineText(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return text.contains(CHAR_NEW_LINE);
    }

    public int getMax() {
        return max;
    }

    public void drawMultilineText(Canvas c, String text,
                                  float x, float y,
                                  TextPaint paint,
                                  FSize constrainedToSize,
                                  MPPointF anchor, float angleDegrees) {

        StaticLayout textLayout = new StaticLayout(
                text, 0, text.length(),
                paint,
                (int) Math.max(Math.ceil(constrainedToSize.width), 1.f),
                Layout.Alignment.ALIGN_NORMAL, 1.f, 0.f, false);


        drawMultilineText(c, textLayout, x, y, paint, anchor, angleDegrees);
    }

    public void drawMultilineText(Canvas c, StaticLayout textLayout,
                                  float x, float y,
                                  TextPaint paint,
                                  MPPointF anchor, float angleDegrees) {

        float drawOffsetX = 0.f;
        float drawOffsetY = 0.f;
        float drawWidth;
        float drawHeight;

        final float lineHeight = paint.getFontMetrics(mFontMetricsBuffer);

        drawWidth = textLayout.getWidth();
        drawHeight = textLayout.getLineCount() * lineHeight;

        // Android sometimes has pre-padding
        drawOffsetX -= mDrawTextRectBuffer.left;

        // Android does not snap the bounds to line boundaries,
        //  and draws from bottom to top.
        // And we want to normalize it.
//        drawOffsetY += drawHeight;

        // To have a consistent point of reference, we always draw left-aligned
        Paint.Align originalTextAlign = paint.getTextAlign();
        paint.setTextAlign(Paint.Align.CENTER);

        if (angleDegrees != 0.f) {

            // Move the text drawing rect in a way that it always rotates around its center
//            drawOffsetX -= drawWidth * 0.5f;
            drawOffsetX += x + (mDrawTextRectBuffer.width() / 2.0f);
            drawOffsetY -= drawHeight * 0.5f;

            float translateX = x;
            float translateY = y;

            // Move the "outer" rect relative to the anchor, assuming its centered
            if (anchor.x != 0.5f || anchor.y != 0.5f) {
                final FSize rotatedSize = getSizeOfRotatedRectangleByDegrees(
                        drawWidth,
                        drawHeight,
                        angleDegrees);

                translateX -= rotatedSize.width * (anchor.x - 0.5f);
                translateY -= rotatedSize.height * (anchor.y - 0.5f);
                FSize.recycleInstance(rotatedSize);
            }

            c.save();
            c.translate(translateX, translateY);
            c.rotate(angleDegrees);

            c.translate(drawOffsetX, drawOffsetY);
            textLayout.draw(c);

            c.restore();
        } else {
            if (anchor.x != 0.f || anchor.y != 0.f) {

                drawOffsetX -= drawWidth * anchor.x;
                drawOffsetY -= drawHeight * anchor.y;
            }

            drawOffsetX += x;
            drawOffsetY += y;

            c.save();

            c.translate(drawOffsetX, drawOffsetY);
            textLayout.draw(c);

            c.restore();
        }

        paint.setTextAlign(originalTextAlign);
    }

    /**
     * Returns a recyclable FSize instance.
     * Represents size of a rotated rectangle by degrees.
     *
     * @param rectangleWidth  rectangleWidth
     * @param rectangleHeight rectangleHeight
     * @param degrees         degrees
     * @return A Recyclable FSize instance
     */
    public static FSize getSizeOfRotatedRectangleByDegrees(float rectangleWidth, float
            rectangleHeight, float degrees) {
        final float radians = degrees * FDEG2RAD;
        return getSizeOfRotatedRectangleByRadians(rectangleWidth, rectangleHeight, radians);
    }

}
