package com.shenyuan.superapp.common.widget.chart;

import android.graphics.Typeface;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.Collections;
import java.util.List;

/**
 * @author ch
 * @date 2020/4/23-9:57
 * desc
 */
public class MyLegendRenderer extends LegendRenderer {
    public MyLegendRenderer(ViewPortHandler viewPortHandler, Legend legend) {
        super(viewPortHandler, legend);
    }

    public void setLegendEable(boolean eable) {
        this.mLegend.setEnabled(eable);
    }


    @Override
    public void computeLegend(ChartData<?> data) {
        if (!mLegend.isLegendCustom()) {

            computedEntries.clear();

            // loop for building up the colors and labels used in the legend
            for (int i = 0; i < data.getDataSetCount(); i++) {

                IDataSet dataSet = data.getDataSetByIndex(i);

                List<Integer> clrs = dataSet.getColors();
                int entryCount = dataSet.getEntryCount();

                // if we have a barchart with stacked bars
                if (dataSet instanceof IBarDataSet && ((IBarDataSet) dataSet).isStacked()) {

                    IBarDataSet bds = (IBarDataSet) dataSet;
                    String[] sLabels = bds.getStackLabels();

                    for (int j = 0; j < clrs.size() && j < bds.getStackSize(); j++) {

                        computedEntries.add(new LegendEntry(
                                sLabels[j % sLabels.length],
                                dataSet.getForm(),
                                dataSet.getFormSize(),
                                dataSet.getFormLineWidth(),
                                dataSet.getFormLineDashEffect(),
                                clrs.get(j)
                        ));
                    }

                    if (bds.getLabel() != null) {
                        // add the legend description label
                        computedEntries.add(new LegendEntry(
                                dataSet.getLabel(),
                                Legend.LegendForm.NONE,
                                Float.NaN,
                                Float.NaN,
                                null,
                                ColorTemplate.COLOR_NONE
                        ));
                    }

                } else if (dataSet instanceof IPieDataSet) {

                    IPieDataSet pds = (IPieDataSet) dataSet;

                    for (int j = 0; j < clrs.size() && j < entryCount; j++) {
                        if (!"empty".equals(pds.getEntryForIndex(j).getLabel())) {
                            computedEntries.add(new LegendEntry(
                                    pds.getEntryForIndex(j).getLabel(),
                                    dataSet.getForm(),
                                    dataSet.getFormSize(),
                                    dataSet.getFormLineWidth(),
                                    dataSet.getFormLineDashEffect(),
                                    clrs.get(j)
                            ));
                        }
                    }

                    if (pds.getLabel() != null) {
                        // add the legend description label
                        computedEntries.add(new LegendEntry(
                                dataSet.getLabel(),
                                Legend.LegendForm.NONE,
                                Float.NaN,
                                Float.NaN,
                                null,
                                ColorTemplate.COLOR_NONE
                        ));
                    }

                } else if (dataSet instanceof ICandleDataSet && ((ICandleDataSet) dataSet).getDecreasingColor() !=
                        ColorTemplate.COLOR_NONE) {

                    int decreasingColor = ((ICandleDataSet) dataSet).getDecreasingColor();
                    int increasingColor = ((ICandleDataSet) dataSet).getIncreasingColor();

                    computedEntries.add(new LegendEntry(
                            null,
                            dataSet.getForm(),
                            dataSet.getFormSize(),
                            dataSet.getFormLineWidth(),
                            dataSet.getFormLineDashEffect(),
                            decreasingColor
                    ));

                    computedEntries.add(new LegendEntry(
                            dataSet.getLabel(),
                            dataSet.getForm(),
                            dataSet.getFormSize(),
                            dataSet.getFormLineWidth(),
                            dataSet.getFormLineDashEffect(),
                            increasingColor
                    ));

                } else { // all others

                    for (int j = 0; j < clrs.size() && j < entryCount; j++) {

                        String label;

                        // if multiple colors are set for a DataSet, group them
                        if (j < clrs.size() - 1 && j < entryCount - 1) {
                            label = null;
                        } else { // add label to the last entry
                            label = data.getDataSetByIndex(i).getLabel();
                        }

                        computedEntries.add(new LegendEntry(
                                label,
                                dataSet.getForm(),
                                dataSet.getFormSize(),
                                dataSet.getFormLineWidth(),
                                dataSet.getFormLineDashEffect(),
                                clrs.get(j)
                        ));
                    }
                }
            }

            if (mLegend.getExtraEntries() != null) {
                Collections.addAll(computedEntries, mLegend.getExtraEntries());
            }

            mLegend.setEntries(computedEntries);
        }

        Typeface tf = mLegend.getTypeface();

        if (tf != null) {
            mLegendLabelPaint.setTypeface(tf);
        }

        mLegendLabelPaint.setTextSize(mLegend.getTextSize());
        mLegendLabelPaint.setColor(mLegend.getTextColor());

        // calculate all dimensions of the mLegend
        mLegend.calculateDimensions(mLegendLabelPaint, mViewPortHandler);
    }
}
