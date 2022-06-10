package com.shenyuan.superapp.ui.data;

import android.graphics.Color;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.api.presenter.BigDataPresenter;
import com.shenyuan.superapp.api.view.BigDataView;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseFragment;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.bean.ProvinceDataBean;
import com.shenyuan.superapp.common.widget.chart.CustBarChart;
import com.shenyuan.superapp.databinding.FmProvinceBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author ch
 * @date 2021/5/26 17:26
 * @
 */
public class ProvinceFragment extends BaseFragment<FmProvinceBinding, BigDataPresenter> implements BigDataView {

    private ArrayList<Integer> allColors = new ArrayList<>();

    @Override
    protected void loadData() {
        presenter.getSchoolProvince();
        presenter.getSchoolTypePer();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fm_province;
    }

    @Override
    protected void initView() {
        allColors.add(Color.parseColor("#ff5555"));
        allColors.add(Color.parseColor("#ffb955"));
        allColors.add(Color.parseColor("#3cbf7f"));
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected BigDataPresenter createPresenter() {
        return new BigDataPresenter(this);
    }

    @Override
    public void onSchoolTypePer(HashMap<String, String> o) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        entries.add(new PieEntry(ParseUtils.parseFloat(o.get("mid")), "中学学校    " + o.get("mid") ));
        entries.add(new PieEntry(ParseUtils.parseFloat(o.get("spe")), "专科学校    " + o.get("spe")));

        colors.add(Color.parseColor("#ff5555"));
        colors.add(Color.parseColor("#ffb955"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        binding.pcSchool.setData(data);

        binding.pcSchool.setHoleColor(Color.TRANSPARENT);
        binding.pcSchool.setCenterTextColor(Color.WHITE);


        binding.pcSchool.setCenterText("学校库类别占比");
        binding.pcSchool.invalidate();
    }

    @Override
    public void onProvinceList(List<ProvinceDataBean> o) {
        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        for (int i = 0; i < o.size(); i++) {
            values.add(new BarEntry(i, ParseUtils.parseInt(o.get(i).getSum())));
            titles.add(o.get(i).getProvince());
            colors.add(allColors.get(i % 3));
        }

        XAxis xAxis = binding.bcSchool.getXAxis();
        xAxis.setValueFormatter(new CustBarChart.MyValueFormatter(titles, true));

        BarDataSet set1 = new BarDataSet(values, "");
        set1.setColors(colors);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextColor(Color.WHITE);

        binding.bcSchool.setExtraBottomOffset(90);
        binding.bcSchool.setData(data);
        binding.bcSchool.invalidate();
    }
}
