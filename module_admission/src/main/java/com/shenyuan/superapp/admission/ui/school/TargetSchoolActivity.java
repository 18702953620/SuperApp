package com.shenyuan.superapp.admission.ui.school;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcTargetSchoolBinding;
import com.shenyuan.superapp.admission.adapter.school.SchoolTargetAdapter;
import com.shenyuan.superapp.admission.api.presenter.TargetPresenter;
import com.shenyuan.superapp.admission.api.view.TargetView;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.admission.ui.AdmissionSearchActivity;
import com.shenyuan.superapp.admission.window.school.LevelWindow;
import com.shenyuan.superapp.admission.window.school.LocationWindow;
import com.shenyuan.superapp.admission.window.school.SearchWindow;
import com.shenyuan.superapp.admission.window.school.SortWindow;
import com.shenyuan.superapp.admission.window.school.TargetSearchWindow;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimEditDialog;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/5 11:25
 * desc 目标学校
 */
@Route(path = ARouterPath.Admission.ADMISSION_TARGET_SCHOOLS)
public class TargetSchoolActivity extends BaseActivity<AcTargetSchoolBinding, TargetPresenter> implements TargetView {
    private SchoolTargetAdapter schoolsAdapter;

    private boolean isSearchSubmit;

    /**
     * 学校位置
     */
    private LocationWindow locationWindow;
    /**
     * 学校层次
     */
    private LevelWindow levelWindow;
    /**
     * 筛选
     */
    private TargetSearchWindow searchWindow;
    /**
     * 排序
     */
    private SortWindow sortWindow;

    private int page = 1;
    /**
     * 本科层次
     */
    private List<Integer> bachelorType;
    /**
     * 高职层次
     */
    private List<Integer> vocationType;
    /**
     * 专升本层次
     */
    private List<Integer> diplomaType;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String region;
    /**
     * 所属区域
     */
    private List<Integer> areaId;
    /**
     * 区域负责人
     */
    private List<Integer> areaStaffId;
    /**
     * 是否挂牌
     */
    private int isListed = -1;
    /**
     * 宣传员
     */
    private List<Integer> staffIds;
    /**
     * 信息录入人
     */
    private List<Integer> creatorId;
    /**
     * 排序字段
     */
    private int schoolLevel = -1;

    @Override
    protected TargetPresenter createPresenter() {
        return new TargetPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_target_school;
    }

    @Override
    protected void initView() {
        schoolsAdapter = new SchoolTargetAdapter();
        binding.rvSchools.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchools.setAdapter(schoolsAdapter);

        getSchoolList();
    }

    /**
     * 学校库列表
     */
    private void getSchoolList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", page);
        //本科
        if (bachelorType != null && bachelorType.size() > 0) {
            map.put("bachelorType", bachelorType);
        }
        //高职
        if (diplomaType != null && diplomaType.size() > 0) {
            map.put("diplomaType", diplomaType);
        }
        //专升本
        if (vocationType != null && vocationType.size() > 0) {
            map.put("vocationType", vocationType);
        }
        //省
        if (!TextUtils.isEmpty(province)) {
            map.put("province", province);
        }
        //市
        if (!TextUtils.isEmpty(city)) {
            map.put("city", city);
        }
        //区
        if (!TextUtils.isEmpty(region)) {
            map.put("region", region);
        }
        //所属区域ID
        if (areaId != null && areaId.size() > 0) {
            map.put("areaId", areaId);
        }
        //区域负责人Id
        if (areaStaffId != null && areaStaffId.size() > 0) {
            map.put("areaStaffId", areaStaffId);
        }
        //是否挂牌生源基地 (0-不是，1-是)
        if (isListed != -1) {
            map.put("isListed", isListed);
        }
        //宣传员ID数组
//        if (staffIds != null && staffIds.size() > 0) {
//            map.put("staffIds", staffIds);
//        }
        //信息录入人
        if (creatorId != null && creatorId.size() > 0) {
            map.put("distributionId", creatorId);
        }
        //排序
        if (schoolLevel != -1) {
            map.put("schoolLevel", schoolLevel);
        }

        presenter.getTargetSchoolList(map);
    }

    @Override
    protected void addListener() {
        schoolsAdapter.addChildClickViewIds(R.id.tv_option, R.id.content);
        schoolsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_option) {
                EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) schoolsAdapter.getViewByPosition(position, R.id.esm_school);
                if (menuLayout != null) {
                    menuLayout.resetStatus();
                }
                SchoolListBean bean = schoolsAdapter.getItem(position);

                new SimEditDialog.Builder(context)
                        .submitText("确定")
                        .title("请填写退回原因")
                        .onBacklistener(new BaseDialog.BaseOnBack() {
                            @Override
                            public void onConfirm(Object object) {
                                presenter.returnBackSchool(String.valueOf(bean.getId()), object.toString());
                            }
                        }).show();
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_ADD)
                        .withInt("schoolId", schoolsAdapter.getItem(position).getId())
                        .withBoolean("editAble", false)
                        .withInt("formWay", AppConstant.FORM_WAY_TARGET_SCHOOL)
                        .navigation();
            }
        });

        binding.llSchoolsLevel.setOnClickListener(v -> {
            showUpArrow(binding.tvSchoolsLevel);

            if (levelWindow == null) {
                levelWindow = new LevelWindow(context);
            }
            levelWindow.showFullAsDropDown(TargetSchoolActivity.this, binding.llSchoolsLocation, (text, bachelor,
                                                                                                  vocation, diploma) -> {
                bachelorType = bachelor;
                vocationType = vocation;
                diplomaType = diploma;

                binding.tvSchoolsLevel.setText(text);
                binding.tvSchoolsLevel.setTextColor(TargetSchoolActivity.this.getValuesColor(R.color.color_0077ff));

                Drawable drawable = TargetSchoolActivity.this.getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvSchoolsLevel.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                page = 1;
                getSchoolList();
            });
            levelWindow.setOnDismissListener(() -> {
                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_down);
                binding.tvSchoolsLevel.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            });
        });

        binding.llSchoolsLocation.setOnClickListener(v -> {
            showUpArrow(binding.tvSchoolLocation);
            if (locationWindow == null) {
                locationWindow = new LocationWindow(context);
            }
            locationWindow.showFullAsDropDown(TargetSchoolActivity.this, binding.llSchoolsLocation, (provinceName, cityName, regionName) -> {
                province = provinceName;
                city = cityName;
                region = regionName;
                String address = province + "/" + city + "/" + region;
                if ("全国".equals(provinceName)) {
                    province = "";
                    city = "";
                    region = "";
                    address = provinceName;
                }
                if ("全省".equals(cityName)) {
                    city = "";
                    region = "";
                    address = province;
                }
                if ("全市".equals(regionName)) {
                    region = "";
                    address = province + "/" + city;
                }
                binding.tvSchoolLocation.setText(address);
                binding.tvSchoolLocation.setTextColor(TargetSchoolActivity.this.getValuesColor(R.color.color_0077ff));
                Drawable drawable = TargetSchoolActivity.this.getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvSchoolLocation.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                page = 1;
                getSchoolList();
            });
            locationWindow.setOnDismissListener(() -> {
                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_down);
                binding.tvSchoolLocation.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            });
        });

        binding.llSchoolsSort.setOnClickListener(v -> {
            showUpArrow(binding.tvSchoolSort);
            if (sortWindow == null) {
                sortWindow = new SortWindow(context);
            }
            sortWindow.showFullAsDropDown(TargetSchoolActivity.this, binding.llSchoolsLocation, address -> {
                schoolLevel = address.getKey();
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
        });

        binding.llSchoolsScreen.setOnClickListener(v -> {
            showUpArrow(binding.tvSchoolSearch);
            if (searchWindow == null) {
                searchWindow = new TargetSearchWindow(context);
            }

            searchWindow.showFullAsDropDown(TargetSchoolActivity.this, binding.llSchoolsScreen,
                    (areaIds, areaStaffIds, isListeds, staffId, createId) -> {
                        isSearchSubmit = true;
                        areaId = areaIds;
                        areaStaffId = areaStaffIds;
                        isListed = isListeds;
                        staffIds = staffId;
                        creatorId = createId;
                        Drawable drawable = TargetSchoolActivity.this.getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                        binding.tvSchoolSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                        binding.tvSchoolSearch.setTextColor(TargetSchoolActivity.this.getValuesColor(R.color.color_0077ff));

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
        });
        binding.llSchoolSearch.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_SEARCH)
                .withInt("searchType", AdmissionSearchActivity.SEARCH_TYPE_TARGET_SCHOOL)
                .navigation());

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
    }

    private void showUpArrow(TextView tv) {
        if (tv == null) {
            return;
        }

        Drawable arrowGrayUp = getDrawableRes(R.mipmap.ic_school_arrow_up);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowGrayUp, null);
    }


    @Override
    public void onTargetSchoolList(List<SchoolListBean> o) {
        if (page == 1) {
            schoolsAdapter.setNewInstance(o);
        } else {
            schoolsAdapter.addData(o);
        }

        binding.mrlSchools.finishRefreshAndLoadMore();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        binding.mrlSchools.finishRefreshAndLoadMore();
    }

    @Override
    public void onBackSchoolList(List<SchoolListBean> o) {

    }

    @Override
    public void onBackSchool(String o) {
        showToast(o);
        page = 1;
        getSchoolList();
    }
}
