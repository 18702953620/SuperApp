package com.shenyuan.superapp.admission.window.student;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.PopStudentSearchBinding;
import com.shenyuan.superapp.admission.adapter.school.AreaAdapter;
import com.shenyuan.superapp.admission.adapter.student.MajorAdapter;
import com.shenyuan.superapp.admission.adapter.school.SchoolStateAdapter;
import com.shenyuan.superapp.admission.adapter.search.SearchMultpleAdapter;
import com.shenyuan.superapp.admission.adapter.school.StaffAdapter;
import com.shenyuan.superapp.admission.adapter.school.UserAreaAdapter;
import com.shenyuan.superapp.admission.adapter.student.YearAdapter;
import com.shenyuan.superapp.admission.api.presenter.StudentCommonPresenter;
import com.shenyuan.superapp.admission.api.view.StudentCommonView;
import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.MajorBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.bean.YearBean;
import com.shenyuan.superapp.base.utils.DensityUtils;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.widget.recy.SpaceDecoration;
import com.shenyuan.superapp.common.bean.BaseChooseBean;
import com.shenyuan.superapp.common.popup.BasePopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/5 17:13
 * desc
 */
public class StudentSearchWindow extends BasePopupWindow<PopStudentSearchBinding, StudentCommonPresenter> implements StudentCommonView {
    private List<AreaBean> areaBeanList;
    private AreaAdapter areaAdapter;

    private List<AreaUserBean> userBeanList;
    private UserAreaAdapter searchLeaderAdapter;

    private YearAdapter searchYearAdapter;

    private List<MajorBean> majorBeanList;
    private MajorAdapter searchMajorAdapter;

    private List<StaffBean> staffBeanList;
    private StaffAdapter searchPropagandistAdapter;

    private SchoolStateAdapter searchSourseAdapter;

    private OnSearchBack onBack;

    public StudentSearchWindow(Context context) {
        super(context, true, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_student_search;
    }

    @Override
    protected void initView() {
        //区域列表
        presenter.getAreaList();

        areaAdapter = new AreaAdapter(null);
        binding.rvStudentArea.setLayoutManager(new GridLayoutManager(context, 4));
        binding.rvStudentArea.setAdapter(areaAdapter);
        binding.rvStudentArea.addItemDecoration(new SpaceDecoration(DensityUtils.dp2px(context, 10)), getValuesColor(R.color.color_ffffff));
        addMultpleMode(areaAdapter);

        //区域负责人
        presenter.getUserArea();

        searchLeaderAdapter = new UserAreaAdapter(null);
        binding.rvStudentLeader.setLayoutManager(new GridLayoutManager(context, 4));
        binding.rvStudentLeader.setAdapter(searchLeaderAdapter);
        binding.rvStudentLeader.addItemDecoration(new SpaceDecoration(DensityUtils.dp2px(context, 10)), getValuesColor(R.color.color_ffffff));
        addMultpleMode(searchLeaderAdapter);

        //毕业年份
        presenter.getStudentYearList();

        searchYearAdapter = new YearAdapter(null);
        binding.rvYear.setLayoutManager(new GridLayoutManager(context, 4));
        binding.rvYear.setAdapter(searchYearAdapter);
        binding.rvYear.addItemDecoration(new SpaceDecoration(DensityUtils.dp2px(context, 10)), getValuesColor(R.color.color_ffffff));
        addMultpleMode(searchYearAdapter);


        //宣传员
        presenter.getStaffList();

        searchPropagandistAdapter = new StaffAdapter(null);
        binding.rvStudentPropagandist.setLayoutManager(new GridLayoutManager(context, 4));
        binding.rvStudentPropagandist.setAdapter(searchPropagandistAdapter);
        binding.rvStudentPropagandist.addItemDecoration(new SpaceDecoration(DensityUtils.dp2px(context, 10)), getValuesColor(R.color.color_ffffff));
        addMultpleMode(searchPropagandistAdapter);

        //生源来源
        presenter.getStudentSourceList();

        searchSourseAdapter = new SchoolStateAdapter(null);
        binding.rvStudentSourse.setLayoutManager(new GridLayoutManager(context, 4));
        binding.rvStudentSourse.setAdapter(searchSourseAdapter);
        binding.rvStudentSourse.addItemDecoration(new SpaceDecoration(DensityUtils.dp2px(context, 10)), getValuesColor(R.color.color_ffffff));
        addMultpleMode(searchSourseAdapter);


        //意向专业
        presenter.getStudentMajorList();

        searchMajorAdapter = new MajorAdapter(null);
        binding.rvStudentMajor.setLayoutManager(new GridLayoutManager(context, 2));
        binding.rvStudentMajor.setAdapter(searchMajorAdapter);
        binding.rvStudentMajor.addItemDecoration(new SpaceDecoration(DensityUtils.dp2px(context, 10)), getValuesColor(R.color.color_ffffff));
        addMultpleMode(searchMajorAdapter);

        //确定
        binding.tvSubmit.setOnClickListener(v1 -> {
            //区域
            List<Integer> areaId = new ArrayList<>();
            for (AreaBean bean : areaAdapter.getData()) {
                if (bean.isSelect()) {
                    areaId.add(bean.getId());
                }
            }

            //区域负责人
            List<Integer> areaStaffId = new ArrayList<>();
            for (AreaUserBean bean : searchLeaderAdapter.getData()) {
                if (bean.isSelect()) {
                    areaStaffId.add(bean.getStaffId());
                }
            }
            //毕业年份
            List<Integer> years = new ArrayList<>();
            for (YearBean bean : searchYearAdapter.getData()) {
                if (bean.isSelect()) {
                    years.add(ParseUtils.parseInt(bean.getYear()));
                }
            }
            //宣传员
            List<Integer> staffId = new ArrayList<>();
            for (StaffBean bean : searchPropagandistAdapter.getData()) {
                if (bean.isSelect()) {
                    staffId.add(bean.getId());
                }
            }
            //来源
            List<Integer> sources = new ArrayList<>();
            for (SimpleBean bean : searchSourseAdapter.getData()) {
                if (bean.isSelect()) {
                    sources.add(bean.getKey());
                }
            }
            //宣传员
            List<Integer> majorId = new ArrayList<>();
            for (MajorBean bean : searchMajorAdapter.getData()) {
                if (bean.isSelect()) {
                    majorId.add(bean.getMajorId());
                }
            }
            double scoreStart = ParseUtils.parseDouble(binding.etScoreStart.getText().toString());
            double scoreEnd = ParseUtils.parseDouble(binding.etScoreEnd.getText().toString());
            if (scoreStart > 0 && !TextUtils.isEmpty(binding.etScoreEnd.getText().toString()) && scoreEnd < scoreStart) {
                showToast("结束分数必须大于开始分数");
                return;
            }

            dismiss();

            if (onBack != null) {
                onBack.onBack(areaId, areaStaffId, years, staffId, sources, majorId, scoreStart, scoreEnd);
            }
        });

        //初始化展开/收起状态
        cuttle(areaAdapter, binding.tvStudentAreaShow, areaBeanList);
        cuttle(searchLeaderAdapter, binding.tvStudentLeaderShow, userBeanList);
        cuttle(searchPropagandistAdapter, binding.tvStudentPropagandistShow, staffBeanList);
        cuttle4(searchMajorAdapter, binding.tvStudentMajorShow, majorBeanList);

        binding.tvStudentAreaShow.setOnClickListener(v12 -> cuttle(areaAdapter, binding.tvStudentAreaShow, areaBeanList));
        binding.tvStudentLeaderShow.setOnClickListener(v12 -> cuttle(searchLeaderAdapter, binding.tvStudentLeaderShow, userBeanList));
        binding.tvStudentPropagandistShow.setOnClickListener(v12 -> cuttle(searchPropagandistAdapter, binding.tvStudentPropagandistShow, staffBeanList));
        binding.tvStudentMajorShow.setOnClickListener(v12 -> cuttle4(searchMajorAdapter, binding.tvStudentMajorShow, majorBeanList));

        binding.tvRest.setOnClickListener(v13 -> {
            resetSearchState(binding);
        });
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected StudentCommonPresenter createPresenter() {
        return new StudentCommonPresenter(this);
    }


    @Override
    public void onAreaList(List<AreaBean> beans) {
        areaBeanList = beans;
        areaAdapter.setNewInstance(beans);

        if (areaAdapter.getData().size() > 8) {
            binding.tvStudentAreaShow.setVisibility(View.VISIBLE);
        } else {
            binding.tvStudentAreaShow.setVisibility(View.GONE);
        }
    }

    @Override
    public void ontUserArea(List<AreaUserBean> beans) {
        userBeanList = beans;
        searchLeaderAdapter.setNewInstance(beans);

        if (searchLeaderAdapter.getData().size() > 8) {
            binding.tvStudentLeaderShow.setVisibility(View.VISIBLE);
        } else {
            binding.tvStudentLeaderShow.setVisibility(View.GONE);
        }
    }

    @Override
    public void ontStaffList(List<StaffBean> o) {
        staffBeanList = o;
        searchPropagandistAdapter.setNewInstance(o);
        if (searchPropagandistAdapter.getData().size() > 8) {
            binding.tvStudentPropagandistShow.setVisibility(View.VISIBLE);
        } else {
            binding.tvStudentPropagandistShow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStudentYearList(List<YearBean> o) {
        searchYearAdapter.setNewInstance(o);
    }

    @Override
    public void onStudentSubjectList(List<SimpleBean> o) {

    }

    @Override
    public void onSchoolLevelList(List<SimpleBean> o) {

    }

    @Override
    public void onStudentTargetList(List<SimpleBean> o) {

    }

    @Override
    public void onStudentSexList(List<SimpleBean> o) {

    }

    @Override
    public void onStudentGenderList(List<SimpleBean> o) {

    }

    @Override
    public void onStudentSourceList(List<SimpleBean> o) {
        searchSourseAdapter.setNewInstance(o);
    }

    @Override
    public void onStudentMajorList(List<MajorBean> o) {
        majorBeanList = o;
        searchMajorAdapter.setNewInstance(majorBeanList);
        if (searchMajorAdapter.getData().size() > 4) {
            binding.tvStudentMajorShow.setVisibility(View.VISIBLE);
        } else {
            binding.tvStudentMajorShow.setVisibility(View.GONE);
        }
    }


    public void showFullAsDropDown(Activity activity, View anchor, OnSearchBack onBack) {
        super.showFullAsDropDown(activity, anchor);
        this.onBack = onBack;
    }


    /**
     * 单选
     *
     * @param multpleAdapter multpleAdapter
     */
    private void addSingleMode(SearchMultpleAdapter multpleAdapter) {
        if (multpleAdapter == null) {
            return;
        }
        multpleAdapter.setOnItemClickListener((adapter, view, position) -> {
            BaseChooseBean bean = (BaseChooseBean) multpleAdapter.getItem(position);
            if (bean == null) {
                return;
            }
            if (!bean.isSelect()) {
                List<BaseChooseBean> beanList = multpleAdapter.getData();
                for (BaseChooseBean b : beanList) {
                    b.setSelect(false);
                }
                bean.setSelect(true);
                multpleAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 多选
     *
     * @param multpleAdapter multpleAdapter
     */
    private void addMultpleMode(SearchMultpleAdapter multpleAdapter) {
        if (multpleAdapter == null) {
            return;
        }

        multpleAdapter.setOnItemClickListener((adapter, view, position) -> {
            BaseChooseBean bean = (BaseChooseBean) multpleAdapter.getItem(position);
            if (bean == null) {
                return;
            }
            if (bean.isSelect()) {
                bean.setSelect(false);
                multpleAdapter.notifyItemChanged(position);
            } else {
                bean.setSelect(true);
                multpleAdapter.notifyItemChanged(position);
            }
        });
    }

    /**
     * 处理展开/收起
     *
     * @param multpleAdapter multpleAdapter
     * @param tv             tv
     * @param list           list
     */
    private void cuttle(SearchMultpleAdapter multpleAdapter, TextView tv, List<?> list) {
        if (multpleAdapter == null) {
            return;
        }
        if (multpleAdapter.getData().size() > 8) {
            multpleAdapter.setNewInstance(new ArrayList<>(list.subList(0, 8)));
            tv.setText("展开");
            Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_down_gray_simple);
            tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            multpleAdapter.setNewInstance(list);
            tv.setText("收起");
            Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_gray_simple);
            tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
    }

    /**
     * 处理展开/收起
     *
     * @param multpleAdapter multpleAdapter
     * @param tv             tv
     * @param list           list
     */
    private void cuttle4(SearchMultpleAdapter multpleAdapter, TextView tv, List<?> list) {
        if (multpleAdapter == null) {
            return;
        }
        if (multpleAdapter.getData().size() > 4) {
            multpleAdapter.setNewInstance(new ArrayList<>(list.subList(0, 4)));
            tv.setText("展开");
            Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_down_gray_simple);
            tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            multpleAdapter.setNewInstance(list);
            tv.setText("收起");
            Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_gray_simple);
            tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
    }

    /**
     * 重置筛选状态
     *
     * @param popBinding popBinding
     */
    private void resetSearchState(PopStudentSearchBinding popBinding) {
        resetAdapterState(areaAdapter);
        resetAdapterState(searchLeaderAdapter);
        resetAdapterState(searchYearAdapter);
        resetAdapterState(searchMajorAdapter);
        resetAdapterState(searchPropagandistAdapter);
        resetAdapterState(searchSourseAdapter);

        //初始化展开/收起状态
        cuttle(areaAdapter, popBinding.tvStudentAreaShow, areaBeanList);
        cuttle(searchLeaderAdapter, popBinding.tvStudentLeaderShow, userBeanList);
        cuttle(searchPropagandistAdapter, popBinding.tvStudentPropagandistShow, staffBeanList);
        cuttle4(searchMajorAdapter, popBinding.tvStudentMajorShow, majorBeanList);

        binding.etScoreStart.getText().clear();
        binding.etScoreEnd.getText().clear();
    }

    /**
     * 初始化 multpleAdapter
     *
     * @param multpleAdapter multpleAdapter
     */
    private void resetAdapterState(SearchMultpleAdapter multpleAdapter) {
        if (multpleAdapter == null) {
            return;
        }
        List<BaseChooseBean> beanList = multpleAdapter.getData();
        for (BaseChooseBean b : beanList) {
            b.setSelect(false);
        }
        multpleAdapter.notifyDataSetChanged();
    }

    /**
     * 回调
     * 业务不同 回调的参数也不同
     */
    public interface OnSearchBack {
        /**
         * 回调
         */
        void onBack(List<Integer> areaId, List<Integer> areaStaffId, List<Integer> years,
                    List<Integer> staffId, List<Integer> sources, List<Integer> majorId, double scoreStart, double scoreEnd);
    }
}
