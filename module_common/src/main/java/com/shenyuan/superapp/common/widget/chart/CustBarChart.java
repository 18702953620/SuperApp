package com.shenyuan.superapp.common.widget.chart;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

/**
 * @author ch
 * @date 2020/4/7-18:04
 * @desc 自定义柱状图
 */
public class CustBarChart extends BarChart {
    public CustBarChart(Context context) {
        super(context);
        initView();
    }

    public CustBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    @Override
    protected void init() {
        super.init();
        mRenderer = new CustBarChartRenderer(this, mAnimator, mViewPortHandler);
        mXAxisRenderer = new CustXAxisRenderer(mViewPortHandler, mXAxis, mLeftAxisTransformer);
    }

    private void initView() {
        setNoDataText("暂无数据");

        getDescription().setEnabled(false);
        //背景
        setDrawGridBackground(false);


        XAxis xAxis = getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setLabelCount(8);

        YAxis leftAxis = getAxisLeft();
        leftAxis.setLabelCount(8);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawTopYLabelEntry(false);
        rightAxis.setLabelCount(8);


        Legend l = getLegend();
        l.setEnabled(false);
        //动画
        animateY(1000);
    }

    @Override
    public void setData(BarData data) {
        super.setData(data);
        configureChartYAxisLabels(this, getAxisLeft().getAxisDependency());
    }

    public static class IntValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            int val = (int) value;
            return super.getFormattedValue(val);
        }
    }

    public static class MyValueFormatter extends ValueFormatter {
        private List<String> list;
        private boolean needMultLine;

        public MyValueFormatter(List<String> list, boolean needMultLine) {
            this.list = list;
            this.needMultLine = needMultLine;
        }

        public MyValueFormatter(List<String> list) {
            this.list = list;
        }

        @Override
        public String getFormattedValue(float value) {
            if (list != null && list.size() > value) {
                if (needMultLine) {
                    String text = list.get((int) value);
                    if (!TextUtils.isEmpty(text) && text.length() > 0) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < text.length(); i++) {
                            builder.append(text.charAt(i)).append("\n");
                        }
                        return builder.toString();
                    }
                    return text;
                }
                return list.get((int) value);
            }
            return super.getFormattedValue(value);
        }
    }

    private void configureChartYAxisLabels(BarLineChartBase chart, YAxis.AxisDependency axisDependency) {
        if (chart.getData() == null) {
            return;
        }
        YAxis yAxis = chart.getAxis(axisDependency);

        // Minimum section is 0.25, could be 1.0f for only integer values
        float labelInterval = 0.25f / 2f;


        int sections;
        do {
            labelInterval *= 2;
            sections = ((int) Math.ceil(chart.getYMax() / labelInterval));
        } while (sections > 3);

        // If the ymax lies on one of the top interval, add another one for some spacing
        if (chart.getYMax() == sections * labelInterval) {
            sections++;
        }

        yAxis.setAxisMaxValue(labelInterval * sections);
        yAxis.setLabelCount(sections + 1, true);
    }
}
