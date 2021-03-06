package com.shenyuan.superapp.admission.ui.school;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcAdmissionSchoolsBinding;
import com.shenyuan.admission.databinding.PopSchoolMoreBinding;
import com.shenyuan.superapp.admission.adapter.school.SchoolListAdapter;
import com.shenyuan.superapp.admission.api.presenter.SchoolsPresenter;
import com.shenyuan.superapp.admission.api.view.SchoolsView;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.admission.bean.TemSchoolBean;
import com.shenyuan.superapp.admission.ui.AdmissionSearchActivity;
import com.shenyuan.superapp.admission.window.school.LevelWindow;
import com.shenyuan.superapp.admission.window.school.LocationWindow;
import com.shenyuan.superapp.admission.window.school.SearchWindow;
import com.shenyuan.superapp.admission.window.school.SortWindow;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimDialog;
import com.shenyuan.superapp.base.utils.PopUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2020/12/15-16:44
 * desc ?????????
 */
@Route(path = ARouterPath.Admission.ADMISSION_SCHOOLS)
public class SchoolListActivity extends BaseActivity<AcAdmissionSchoolsBinding, SchoolsPresenter> implements SchoolsView {
    private SchoolListAdapter schoolsAdapter;

    private boolean isSearchSubmit;

    public static final int REQUEST_CODE_SCHOOL_LIST = 100;
    /**
     * ????????????
     */
    private LocationWindow locationWindow;
    /**
     * ????????????
     */
    private LevelWindow levelWindow;
    /**
     * ??????
     */
    private SearchWindow searchWindow;
    /**
     * ??????
     */
    private SortWindow sortWindow;

    private int page = 1;
    /**
     * ????????????
     */
    private List<Integer> bachelorType;
    /**
     * ????????????
     */
    private List<Integer> vocationType;
    /**
     * ???????????????
     */
    private List<Integer> diplomaType;
    /**
     * ???
     */
    private String province;
    /**
     * ???
     */
    private String city;
    /**
     * ???
     */
    private String region;
    /**
     * ????????????
     */
    private List<Integer> areaId;
    /**
     * ???????????????
     */
    private List<Integer> areaStaffId;
    /**
     * ????????????
     */
    private int isListed = -1;
    /**
     * ?????????
     */
    private List<Integer> staffIds;
    /**
     * ???????????????
     */
    private List<Integer> creatorId;
    /**
     * ????????????
     */
    private int orderType = -1;


    @Override
    protected SchoolsPresenter createPresenter() {
        return new SchoolsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_admission_schools;
    }

    @Override
    protected void initView() {
        schoolsAdapter = new SchoolListAdapter();
        binding.rvSchools.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchools.setAdapter(schoolsAdapter);
        //???????????????
        getSchoolList();
    }

    /**
     * ???????????????
     */
    private void getSchoolList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", page);
        //??????
        if (bachelorType != null && bachelorType.size() > 0) {
            map.put("bachelorType", bachelorType);
        }
        //??????
        if (diplomaType != null && diplomaType.size() > 0) {
            map.put("diplomaType", diplomaType);
        }
        //?????????
        if (vocationType != null && vocationType.size() > 0) {
            map.put("vocationType", vocationType);
        }
        //???
        if (!TextUtils.isEmpty(province)) {
            map.put("province", province);
        }
        //???
        if (!TextUtils.isEmpty(city)) {
            map.put("city", city);
        }
        //???
        if (!TextUtils.isEmpty(region)) {
            map.put("region", region);
        }
        //????????????ID
        if (areaId != null && areaId.size() > 0) {
            map.put("areaId", areaId);
        }
        //???????????????Id
        if (areaStaffId != null && areaStaffId.size() > 0) {
            map.put("areaStaffId", areaStaffId);
        }
        //???????????????????????? (0-?????????1-???)
        if (isListed != -1) {
            map.put("isListed", isListed);
        }
        //?????????ID??????
        if (staffIds != null && staffIds.size() > 0) {
            map.put("staffIds", staffIds);
        }
        //???????????????
        if (creatorId != null && creatorId.size() > 0) {
            map.put("creatorId", creatorId);
        }
        //??????
        if (orderType != -1) {
            map.put("orderType", orderType);
        }

        presenter.getSchoolList(map);
    }

    @Override
    protected void addListener() {
        binding.title.addRightListener(v -> showMoreDialog());
        //??????
        binding.llSchoolsLevel.setOnClickListener(v -> {
            showUpArrow(binding.tvSchoolsLevel);
            showLevelDialog();
        });
        //????????????
        binding.llSchoolsLocation.setOnClickListener(v -> {
            showUpArrow(binding.tvSchoolLocation);
            showLocationDialog();
        });
        //??????
        binding.llSchoolsSort.setOnClickListener(v -> {
            showUpArrow(binding.tvSchoolSort);
            showSortDialog();
        });
        //??????
        binding.llSchoolsScreen.setOnClickListener(v -> {
            showUpArrow(binding.tvSchoolSearch);
            showSearchDialog();
        });
        //??????
        binding.cbDistribution.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (SchoolListBean b : schoolsAdapter.getData()) {
                b.setSelect(isChecked);
            }
            schoolsAdapter.notifyDataSetChanged();
        });
        //??????
        binding.tvDelete.setOnClickListener(v -> new SimDialog.Builder(context)
                .title(getString(R.string.is_delete_school))
                .submitText(getString(R.string.sure)).onBacklistener(new BaseDialog.BaseOnBack() {
                    @Override
                    public void onConfirm() {
                        StringBuilder ids = new StringBuilder();
                        for (SchoolListBean b : schoolsAdapter.getData()) {
                            if (b.isSelect()) {
                                if (ids.length() > 0) {
                                    ids.append(",");
                                }
                                ids.append(b.getId());
                            }
                        }
                        if (ids.length() == 0) {
                            showToast(getString(R.string.select_at_least_one));
                            return;
                        }
                        presenter.deleteSchool(ids.toString());
                    }
                }).show());

        schoolsAdapter.addChildClickViewIds(R.id.content, R.id.tv_edit);
        schoolsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_edit) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_ADD)
                        .withInt("schoolId", schoolsAdapter.getItem(position).getId())
                        .navigation(this, REQUEST_CODE_SCHOOL_LIST);
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_ADD)
                        .withInt("schoolId", schoolsAdapter.getItem(position).getId())
                        .withBoolean("showEdit", true)
                        .withBoolean("editAble", false)
                        .navigation(this, REQUEST_CODE_SCHOOL_LIST);
            }
        });
        //??????
        if (PermissionCommon.hasSchoolAdd()) {
            binding.btnSchoolAdd.setVisibility(View.VISIBLE);
        } else {
            binding.btnSchoolAdd.setVisibility(View.GONE);
        }
        binding.btnSchoolAdd.setOnClickListener(v ->
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_ADD).navigation(this, REQUEST_CODE_SCHOOL_LIST));

        binding.mrlSchools.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getSchoolList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getSchoolList();
            }
        });
        binding.llSchoolSearch.setOnClickListener(v ->
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_SEARCH)
                        .withInt("searchType", AdmissionSearchActivity.SEARCH_TYPE_SCHOOL)
                        .navigation());
    }


    /**
     * ??????
     */
    private void showSortDialog() {
        if (sortWindow == null) {
            sortWindow = new SortWindow(context);
        }
        sortWindow.showFullAsDropDown(SchoolListActivity.this, binding.llSchoolsLocation, address -> {
            orderType = address.getKey();
            binding.tvSchoolSort.setTextColor(getValuesColor(R.color.color_0077ff));
            Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
            binding.tvSchoolSort.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

            page = 1;
            getSchoolList();
        });
        sortWindow.setOnDismissListener(() -> {
            Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_down);
            binding.tvSchoolSort.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        });
    }

    /**
     * ??????
     */
    private void showSearchDialog() {
        if (searchWindow == null) {
            searchWindow = new SearchWindow(context);
        }

        searchWindow.showFullAsDropDown(SchoolListActivity.this, binding.llSchoolsScreen, (areaIds,
                                                                                           areaStaffIds, isListeds, staffId, createId) -> {
            isSearchSubmit = true;
            areaId = areaIds;
            areaStaffId = areaStaffIds;
            isListed = isListeds;
            staffIds = staffId;
            creatorId = createId;

            Drawable drawable = SchoolListActivity.this.getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
            binding.tvSchoolSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            binding.tvSchoolSearch.setTextColor(SchoolListActivity.this.getValuesColor(R.color.color_0077ff));

            page = 1;
            getSchoolList();
        });

        searchWindow.setOnDismissListener(() -> {
            if (!isSearchSubmit) {
                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_down);
                binding.tvSchoolSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
            isSearchSubmit = false;
        });
    }

    /**
     * ??????
     */
    private void showLocationDialog() {
        if (locationWindow == null) {
            locationWindow = new LocationWindow(context);
        }
        locationWindow.showFullAsDropDown(SchoolListActivity.this, binding.llSchoolsLocation, (provinceName, cityName, regionName) -> {

            province = provinceName;
            city = cityName;
            region = regionName;
            String address = province + "/" + city + "/" + region;
            if ("??????".equals(provinceName)) {
                province = "";
                city = "";
                region = "";
                address = provinceName;
            }
            if ("??????".equals(cityName)) {
                city = "";
                region = "";
                address = province;
            }
            if ("??????".equals(regionName)) {
                region = "";
                address = province + "/" + city;
            }

            binding.tvSchoolLocation.setText(address);
            binding.tvSchoolLocation.setTextColor(SchoolListActivity.this.getValuesColor(R.color.color_0077ff));
            Drawable drawable = SchoolListActivity.this.getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
            binding.tvSchoolLocation.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

            page = 1;
            getSchoolList();
        });
        locationWindow.setOnDismissListener(() -> showDownArrow(binding.tvSchoolLocation));
    }

    /**
     * ??????
     */
    private void showLevelDialog() {
        if (levelWindow == null) {
            levelWindow = new LevelWindow(context);
        }
        levelWindow.showFullAsDropDown(SchoolListActivity.this, binding.llSchoolsLocation, (text, bachelor, vocation, diploma) -> {
            bachelorType = bachelor;
            vocationType = vocation;
            diplomaType = diploma;

            binding.tvSchoolsLevel.setText(text);
            binding.tvSchoolsLevel.setTextColor(getValuesColor(R.color.color_0077ff));

            Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
            binding.tvSchoolsLevel.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

            page = 1;
            getSchoolList();
        });
        levelWindow.setOnDismissListener(() -> showDownArrow(binding.tvSchoolsLevel));
    }

    /**
     * ??????????????????
     */
    private void showMoreDialog() {
        PopSchoolMoreBinding popBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.pop_school_more, null, false);

        PopupWindow popupWindow = PopUtils.getTransparentWindow(context, popBinding.getRoot());
        popupWindow.showAsDropDown(binding.title, 0, 0, Gravity.END);

        if (PermissionCommon.hasSchoolDistribution()) {
            popBinding.tvSchoolDistribution.setVisibility(View.VISIBLE);
        } else {
            popBinding.tvSchoolDistribution.setVisibility(View.GONE);
        }

        if (PermissionCommon.hasSchoolRemove()) {
            popBinding.tvSchoolDelete.setVisibility(View.VISIBLE);
        } else {
            popBinding.tvSchoolDelete.setVisibility(View.GONE);
        }

        if (PermissionCommon.hasReturnSchoolDistribution()) {
            popBinding.tvSchoolBack.setVisibility(View.VISIBLE);
        } else {
            popBinding.tvSchoolBack.setVisibility(View.GONE);
        }

        //??????????????????
        popBinding.tvSchoolDistribution.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_DISTRITION).navigation();
            popupWindow.dismiss();
        });
        //??????
        popBinding.tvSchoolDelete.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_DELETE).navigation(this, REQUEST_CODE_SCHOOL_LIST);
            popupWindow.dismiss();
        });
        //?????????
        popBinding.tvSchoolBack.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_BACK).navigation();
            popupWindow.dismiss();
        });
    }

    private void showUpArrow(TextView tv) {
        if (tv == null) {
            return;
        }

        Drawable arrowGrayUp = getDrawableRes(R.mipmap.ic_school_arrow_up);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowGrayUp, null);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showDownArrow(TextView tv) {
        if (tv == null) {
            return;
        }

        Drawable arrowGrayUp = getResources().getDrawable(R.mipmap.ic_school_arrow_down);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowGrayUp, null);
    }


    @Override
    public void showError(String msg) {
        super.showError(msg);
        binding.mrlSchools.finishRefreshAndLoadMore();
    }

    @Override
    public void onSchoolList(List<SchoolListBean> o) {
        if (page == 1) {
            schoolsAdapter.setNewInstance(o);
        } else {
            schoolsAdapter.addData(o);
        }

        binding.mrlSchools.finishRefreshAndLoadMore();
    }


    @Override
    public void onAddSchool(String o) {

    }

    @Override
    public void onDeleteSchool(String o) {
    }

    @Override
    public void onSchoolInfo(SchoolInfoBean o) {

    }

    @Override
    public void onUpdateSchool(String o) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SCHOOL_LIST) {
            page = 1;
            getSchoolList();
        }
    }
}
