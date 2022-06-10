package com.shenyuan.superapp.admission.window.school;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemSchoolLevelBinding;
import com.shenyuan.admission.databinding.PopSchoolLevelBinding;
import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.CreaterBean;
import com.shenyuan.superapp.admission.bean.SchoolTypeBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.api.presenter.SchoolCommonPresenter;
import com.shenyuan.superapp.admission.api.view.CommonView;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.popup.BasePopupWindow;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/5 16:55
 * desc 学校库-学校层次
 */
public class LevelWindow extends BasePopupWindow<PopSchoolLevelBinding, SchoolCommonPresenter> implements CommonView {
    private BaseQuickAdapter<String, BaseDataBindingHolder> levelAdapter;
    private BaseQuickAdapter<SchoolTypeBean.DiplomaTypeDTO, BaseDataBindingHolder> levelTypeAdapter;
    private int levelPosition = -1;
    private int levelTypePosition = -1;

    private OnLevelBack onBack;
    private SchoolTypeBean schoolTypeBeans;

    public LevelWindow(Context context) {
        super(context, true, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_school_level;
    }

    @Override
    protected void initView() {
        presenter.getSchoolLevel();

        levelAdapter = new BaseVBAdapter<String, BaseDataBindingHolder>(R.layout.item_school_level) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, String s) {
                ItemSchoolLevelBinding schoolLevelBinding = (ItemSchoolLevelBinding) baseDataBindingHolder.getDataBinding();
                if (schoolLevelBinding == null) {
                    return;
                }
                schoolLevelBinding.tvSchoolLevel.setText(s);

                if (levelPosition == baseDataBindingHolder.getAdapterPosition()) {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_0077ff));
                    schoolLevelBinding.tvSchoolLevel.setBackgroundColor(getValuesColor(R.color.color_f5f5f5));
                } else {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_333333));
                    schoolLevelBinding.tvSchoolLevel.setBackgroundColor(getValuesColor(R.color.color_ffffff));
                }

            }
        };

        binding.rvSchoolsLevel.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchoolsLevel.setAdapter(levelAdapter);


        levelTypeAdapter = new BaseQuickAdapter<SchoolTypeBean.DiplomaTypeDTO, BaseDataBindingHolder>(R.layout.item_school_level) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, SchoolTypeBean.DiplomaTypeDTO s) {
                ItemSchoolLevelBinding schoolLevelBinding = (ItemSchoolLevelBinding) baseDataBindingHolder.getDataBinding();
                if (schoolLevelBinding == null) {
                    return;
                }
                schoolLevelBinding.tvSchoolLevel.setText(s.getLevelName());
                if (levelTypePosition == baseDataBindingHolder.getAdapterPosition()) {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_0077ff));
                } else {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_333333));
                }
            }
        };

        binding.rvSchoolsLevelType.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchoolsLevelType.setAdapter(levelTypeAdapter);

    }

    @Override
    protected void addListener() {
        levelAdapter.setOnItemClickListener((adapter, view, position) -> {
            levelPosition = position;
            levelAdapter.notifyDataSetChanged();

            levelTypePosition = -1;
            levelTypeAdapter.notifyDataSetChanged();

            if (levelPosition == 0) {
                levelTypeAdapter.setNewInstance(null);
                dismiss();
                if (onBack != null) {
                    onBack.onBack("全部", null, null, null);
                }
            } else {
                List<SchoolTypeBean.DiplomaTypeDTO> list = new ArrayList<>();
                SchoolTypeBean.DiplomaTypeDTO dip = new SchoolTypeBean.DiplomaTypeDTO();
                dip.setLevelName("全部");
                list.add(dip);
                if ("本科".equals(levelAdapter.getItem(position))) {
                    if (schoolTypeBeans != null) {
                        list.addAll(schoolTypeBeans.getBachelorType());
                    }
                } else if ("专科".equals(levelAdapter.getItem(position))) {
                    if (schoolTypeBeans != null) {
                        list.addAll(schoolTypeBeans.getDiplomaType());
                    }
                } else {
                    if (schoolTypeBeans != null) {
                        list.addAll(schoolTypeBeans.getVocationType());
                    }
                }
                levelTypeAdapter.setNewInstance(list);
            }
        });


        levelTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            levelTypePosition = position;
            levelTypeAdapter.notifyDataSetChanged();
            dismiss();
            String address = levelAdapter.getData().get(levelPosition) + "/"
                    + levelTypeAdapter.getData().get(levelTypePosition).getLevelName();

            List<Integer> bachelorType = new ArrayList<>();
            List<Integer> vocationType = new ArrayList<>();
            List<Integer> diplomaType = new ArrayList<>();

            if ("本科".equals(levelAdapter.getItem(levelPosition))) {
                if ("全部".equals(levelTypeAdapter.getItem(position).getLevelName())) {
                    for (SchoolTypeBean.DiplomaTypeDTO dto : levelTypeAdapter.getData()) {
                        if (dto.getSchoolLevel() != null) {
                            bachelorType.add(dto.getSchoolLevel());
                        }
                    }
                } else {
                    bachelorType.add(levelTypeAdapter.getItem(position).getSchoolLevel());
                }

            } else if ("专科".equals(levelAdapter.getItem(levelPosition))) {
                if ("全部".equals(levelTypeAdapter.getItem(position).getLevelName())) {
                    for (SchoolTypeBean.DiplomaTypeDTO dto : levelTypeAdapter.getData()) {
                        if (dto.getSchoolLevel() != null) {
                            vocationType.add(dto.getSchoolLevel());
                        }
                    }
                } else {
                    vocationType.add(levelTypeAdapter.getItem(position).getSchoolLevel());
                }
            } else {
                if ("全部".equals(levelTypeAdapter.getItem(position).getLevelName())) {
                    for (SchoolTypeBean.DiplomaTypeDTO dto : levelTypeAdapter.getData()) {
                        if (dto.getSchoolLevel() != null) {
                            diplomaType.add(dto.getSchoolLevel());
                        }
                    }
                } else {
                    diplomaType.add(levelTypeAdapter.getItem(position).getSchoolLevel());
                }
            }

            if (onBack != null) {
                onBack.onBack(address, bachelorType, vocationType, diplomaType);
            }


        });
    }

    @Override
    protected SchoolCommonPresenter createPresenter() {
        return new SchoolCommonPresenter(this);
    }


    public void showFullAsDropDown(Activity activity, View anchor, OnLevelBack onBack) {
        super.showFullAsDropDown(activity, anchor);
        this.onBack = onBack;
    }

    @Override
    public void onAreaList(List<AreaBean> beans) {

    }

    @Override
    public void onCreaterList(List<CreaterBean> beans) {

    }

    @Override
    public void onLising(List<SimpleBean> beans) {

    }

    @Override
    public void onSchoolState(List<SimpleBean> beans) {

    }

    @Override
    public void onSchoolLevel(SchoolTypeBean beans) {
        schoolTypeBeans = beans;
        if (schoolTypeBeans == null) {
            return;
        }

        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add(schoolTypeBeans.getBachelorName());
        list.add(schoolTypeBeans.getDiplomaName());
        levelAdapter.setNewInstance(list);
    }

    @Override
    public void ontUserArea(List<AreaUserBean> beans) {

    }

    @Override
    public void ontStaffList(List<StaffBean> o) {

    }

    @Override
    public void onSortList(List<SimpleBean> o) {

    }


    /**
     * 回调
     * 业务不同 回调的参数也不同
     */
    public interface OnLevelBack {
        /**
         * 回调
         */
        void onBack(String text, List<Integer> bachelorType, List<Integer> vocationType, List<Integer> diplomaType);
    }
}
