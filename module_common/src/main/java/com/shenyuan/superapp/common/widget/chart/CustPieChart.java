package com.shenyuan.superapp.common.widget.chart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.shenyuan.superapp.common.R;

/**
 * @author ch
 * @date 2020/4/7-17:07
 * @desc 自定义属性饼状图
 */
public class CustPieChart extends PieChart {
    public CustPieChart(Context context) {
        super(context);
        initView();
    }

    public CustPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    @Override
    protected void init() {
        super.init();
        mLegendRenderer = new MyLegendRenderer(mViewPortHandler, mLegend);
    }

    public void setLegendEable(boolean eable) {
        this.mLegend.setEnabled(eable);
    }

    private void initView() {
        setNoDataText("暂无数据");
        //中间描述
        setDrawCenterText(true);
        //中心文字大小
        setCenterTextSize(18f);
        //图表描述
        getDescription().setEnabled(false);
        //设置中间空白的百分比
        setHoleRadius(80f);
        //不可触摸
        setTouchEnabled(false);
        //指示
        Legend l = getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTextSize(13f);
        l.setXEntrySpace(20f);
        l.setYEntrySpace(15f);
        l.setYOffset(10f);
        //饼状图上描述
        setDrawEntryLabels(false);
        //左边边距
        setExtraLeftOffset(0f);
        l.setTextColor(Color.WHITE);

        //动画
        animateY(1000, Easing.EaseInOutQuad);
    }


    @Override
    public void setData(PieData data) {
        for (IDataSet<?> set : data.getDataSets()) {
            set.setDrawValues(false);
            set.setDrawIcons(false);
            if (set instanceof PieDataSet) {
                PieDataSet pieDataSet = (PieDataSet) set;
                if (pieDataSet.getValues() != null && pieDataSet.getValues().size() > 0) {
                    boolean isEmpty = true;
                    for (PieEntry p : pieDataSet.getValues()) {
                        if (p.getValue() != 0) {
                            isEmpty = false;
                        }
                    }
                    if (isEmpty) {
                        pieDataSet.getValues().add(new PieEntry(100, "empty"));
                        pieDataSet.getColors().add(Color.parseColor("#eeeeee"));
                    }
                }
            }

        }
        super.setData(data);
    }
}
