package com.shenyuan.superapp.admission.window.school;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.PopSchoolSearchBinding;
import com.shenyuan.superapp.admission.adapter.school.AreaAdapter;
import com.shenyuan.superapp.admission.adapter.school.CreaterAdapter;
import com.shenyuan.superapp.admission.adapter.school.SchoolStateAdapter;
import com.shenyuan.superapp.admission.adapter.search.SearchMultpleAdapter;
import com.shenyuan.superapp.admission.adapter.school.StaffAdapter;
import com.shenyuan.superapp.admission.adapter.school.UserAreaAdapter;
import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.CreaterBean;
import com.shenyuan.superapp.admission.bean.SchoolTypeBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.api.presenter.SchoolCommonPresenter;
import com.shenyuan.superapp.admission.api.view.CommonView;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.utils.DensityUtils;
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
public class SearchWindow extends BasePopupWindow<PopSchoolSearchBinding, SchoolCommonPresenter> implements CommonView {
    private AreaAdapter areaAdapter;
    private List<AreaBean> areaBeanList;

    private List<AreaUserBean> userBeanList;
    private UserAreaAdapter searchLeaderAdapter;

    private SchoolStateAdapter searchLisingAdapter;

    private StaffAdapter searchPropagandistAdapter;
    private List<StaffBean> staffBeanList;

    private List<CreaterBean> createrBeanList;
    private CreaterAdapter createrAdapter;

    private OnSearchBack onBack;

    public SearchWindow(Context context) {
        super(context, true, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_school_search;
    }

    @Override
    protected void initView() {
        //????????????
        presenter.getAreaList();

        areaAdapter = new AreaAdapter(null);
        binding.rvSchoolsArea.setLayoutManager(new GridLayoutManager(context, 4));
        binding.rvSchoolsArea.setAdapter(areaAdapter);
        binding.rvSchoolsArea.addItemDecoration(new SpaceDecoration(DensityUtils.dp2px(context, 10)), getValuesColor(R.color.color_ffffff));
        addMultpleMode(areaAdapter);

        //???????????????
        presenter.getUserArea();
        searchLeaderAdapter = new UserAreaAdapter(null);
        binding.rvSchoolsLeader.setLayoutManager(new GridLayoutManager(context, 4));
        binding.rvSchoolsLeader.setAdapter(searchLeaderAdapter);
        binding.rvSchoolsLeader.addItemDecoration(new SpaceDecoration(DensityUtils.dp2px(context, 10)), getValuesColor(R.color.color_ffffff));
        addMultpleMode(searchLeaderAdapter);

        //????????????
        presenter.isLising();

        searchLisingAdapter = new SchoolStateAdapter(null);
        binding.rvListing.setLayoutManager(new GridLayoutManager(context, 4));
        binding.rvListing.setAdapter(searchLisingAdapter);
        binding.rvListing.addItemDecoration(new SpaceDecoration(DensityUtils.dp2px(context, 10)), getValuesColor(R.color.color_ffffff));
        addSingleMode(searchLisingAdapter);

        //?????????
        presenter.getStaffList();

        searchPropagandistAdapter = new StaffAdapter(null);
        binding.rvSchoolsPropagandist.setLayoutManager(new GridLayoutManager(context, 4));
        binding.rvSchoolsPropagandist.setAdapter(searchPropagandistAdapter);
        binding.rvSchoolsPropagandist.addItemDecoration(new SpaceDecoration(DensityUtils.dp2px(context, 10)), getValuesColor(R.color.color_ffffff));
        addMultpleMode(searchPropagandistAdapter);


        //???????????????
        presenter.getCreatNameList();

        createrAdapter = new CreaterAdapter(null);
        binding.rvSchoolsAdder.setLayoutManager(new GridLayoutManager(context, 4));
        binding.rvSchoolsAdder.setAdapter(createrAdapter);
        binding.rvSchoolsAdder.addItemDecoration(new SpaceDecoration(DensityUtils.dp2px(context, 10)), getValuesColor(R.color.color_ffffff));
        addMultpleMode(createrAdapter);

        //??????
        binding.tvSubmit.setOnClickListener(v1 -> {
            //??????
            List<Integer> areaId = new ArrayList<>();
            for (AreaBean bean : areaAdapter.getData()) {
                if (bean.isSelect()) {
                    areaId.add(bean.getId());
                }
            }
            //???????????????
            List<Integer> areaStaffId = new ArrayList<>();
            for (AreaUserBean bean : searchLeaderAdapter.getData()) {
                if (bean.isSelect()) {
                    areaStaffId.add(bean.getStaffId());
                }
            }
            //????????????
            int isListed = -1;
            for (SimpleBean bean : searchLisingAdapter.getData()) {
                if (bean.isSelect()) {
                    isListed = bean.getKey();
                }
            }
            //?????????
            List<Integer> staffId = new ArrayList<>();
            for (StaffBean bean : searchPropagandistAdapter.getData()) {
                if (bean.isSelect()) {
                    staffId.add(bean.getId());
                }
            }
            //???????????????
            List<Integer> createId = new ArrayList<>();
            for (CreaterBean bean : createrAdapter.getData()) {
                if (bean.isSelect()) {
                    createId.add(bean.getId());
                }
            }

            dismiss();

            if (onBack != null) {
                onBack.onBack(areaId, areaStaffId, isListed, staffId, createId);
            }
        });

        //???????????????/????????????
        cuttle(areaAdapter, binding.tvSchoolAreaShow, areaBeanList);
        cuttle(searchLeaderAdapter, binding.tvSchoolLeaderShow, userBeanList);
        cuttle(searchPropagandistAdapter, binding.tvSchoolPropagandistShow, staffBeanList);
        cuttle(createrAdapter, binding.tvSchoolAdderShow, createrBeanList);

        binding.tvSchoolAreaShow.setOnClickListener(v12 -> cuttle(areaAdapter, binding.tvSchoolAreaShow, areaBeanList));
        binding.tvSchoolLeaderShow.setOnClickListener(v12 -> cuttle(searchLeaderAdapter, binding.tvSchoolLeaderShow, userBeanList));
        binding.tvSchoolPropagandistShow.setOnClickListener(v12 -> cuttle(searchPropagandistAdapter, binding.tvSchoolPropagandistShow, staffBeanList));
        binding.tvSchoolAdderShow.setOnClickListener(v12 -> cuttle(createrAdapter, binding.tvSchoolAdderShow, createrBeanList));

        binding.tvRest.setOnClickListener(v13 -> {
            resetSearchState(binding);
        });
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected SchoolCommonPresenter createPresenter() {
        return new SchoolCommonPresenter(this);
    }


    @Override
    public void onAreaList(List<AreaBean> beans) {
        areaBeanList = beans;
        areaAdapter.setNewInstance(beans);

        if (areaAdapter.getData().size() > 8) {
            binding.tvSchoolAreaShow.setVisibility(View.VISIBLE);
        } else {
            binding.tvSchoolAreaShow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreaterList(List<CreaterBean> beans) {
        createrBeanList = beans;
        createrAdapter.setNewInstance(beans);

        if (createrAdapter.getData().size() > 8) {
            binding.tvSchoolAdderShow.setVisibility(View.VISIBLE);
        } else {
            binding.tvSchoolAdderShow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLising(List<SimpleBean> beans) {
        searchLisingAdapter.setNewInstance(beans);
    }

    @Override
    public void onSchoolState(List<SimpleBean> beans) {
    }

    @Override
    public void onSchoolLevel(SchoolTypeBean beans) {

    }

    @Override
    public void ontUserArea(List<AreaUserBean> beans) {
        userBeanList = beans;
        searchLeaderAdapter.setNewInstance(beans);

        if (searchLeaderAdapter.getData().size() > 8) {
            binding.tvSchoolLeaderShow.setVisibility(View.VISIBLE);
        } else {
            binding.tvSchoolLeaderShow.setVisibility(View.GONE);
        }
    }

    @Override
    public void ontStaffList(List<StaffBean> o) {
        staffBeanList = o;
        searchPropagandistAdapter.setNewInstance(o);
        if (searchPropagandistAdapter.getData().size() > 8) {
            binding.tvSchoolPropagandistShow.setVisibility(View.VISIBLE);
        } else {
            binding.tvSchoolPropagandistShow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSortList(List<SimpleBean> o) {

    }



    /**
     * ??????
     * ???????????? ????????????????????????
     */
    public interface OnSearchBack {
        /**
         * ??????
         */
        void onBack(List<Integer> areaId, List<Integer> areaStaffId, int isListed, List<Integer> staffId, List<Integer> createId);
    }


    public void showFullAsDropDown(Activity activity, View anchor, OnSearchBack onBack) {
        super.showFullAsDropDown(activity, anchor);
        this.onBack = onBack;
    }


    /**
     * ??????
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
     * ??????
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
     * ????????????/??????
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
            tv.setText("??????");
            Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_school_arrow_down_gray_simple);
            tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            multpleAdapter.setNewInstance(list);
            tv.setText("??????");
            Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_school_arrow_up_gray_simple);
            tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
    }

    /**
     * ??????????????????
     *
     * @param popBinding popBinding
     */
    private void resetSearchState(PopSchoolSearchBinding popBinding) {
        resetAdapterState(areaAdapter);
        resetAdapterState(searchLeaderAdapter);
        resetAdapterState(searchLisingAdapter);
        resetAdapterState(searchPropagandistAdapter);
        resetAdapterState(createrAdapter);

        //???????????????/????????????
        cuttle(areaAdapter, popBinding.tvSchoolAreaShow, areaBeanList);
        cuttle(searchLeaderAdapter, popBinding.tvSchoolLeaderShow, userBeanList);
        cuttle(searchPropagandistAdapter, popBinding.tvSchoolPropagandistShow, staffBeanList);
        cuttle(createrAdapter, popBinding.tvSchoolAdderShow, createrBeanList);
    }

    /**
     * ????????? multpleAdapter
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
}
