package com.shenyuan.superapp.admission.ui.school;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcAddSchoolBinding;
import com.shenyuan.superapp.admission.api.presenter.SchoolCommonPresenter;
import com.shenyuan.superapp.admission.api.presenter.SchoolsPresenter;
import com.shenyuan.superapp.admission.api.view.CommonView;
import com.shenyuan.superapp.admission.api.view.SchoolsView;
import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.CreaterBean;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.admission.bean.SchoolTypeBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.bean.TemSchoolBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.DialogUtils;
import com.shenyuan.superapp.base.utils.DateUtils;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.utils.StrUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/2 15:19
 * desc 添加/编辑学校
 */
@Route(path = ARouterPath.Admission.ADMISSION_SCHOOLS_ADD)
public class AddSchoolActivity extends BaseActivity<AcAddSchoolBinding, SchoolsPresenter> implements SchoolsView, CommonView {

    private SchoolCommonPresenter commonPresenter;
    /**
     * 本科学校类型 (字典值，1-一类重点，2-二类机会，3-三类开发)
     */
    private int bachelorType = -1;
    /**
     * 高职学校类型 (字典值，1-一类重点，2-二类机会，3-三类开发)
     */
    private int vocationType = -1;
    /**
     * 专升本学校类型 (字典值，1-一类重点，2-二类机会，3-三类开发)
     */
    private int diplomaType = -1;
    /**
     * 区域
     */
    private int areaId = -1;
    /**
     * 区域名称
     */
    private String areaName;
    /**
     * 宣传员
     */
    private List<Integer> staffIds;

    /**
     * 本科
     */
    private String currentBYear;
    /**
     * 高职
     */
    private String currentGYear;

    private int schoolType = -1;

    @Autowired
    public int schoolId = -1;


    public static final int REQUEST_CODE_ADD_SCHOOL = 100;

    private SchoolInfoBean schoolInfoBean;

    private boolean isAdd = true;
    /**
     * 是否可以编辑
     */
    @Autowired
    public boolean editAble = true;
    /**
     * 是否展示编辑按钮
     */
    @Autowired
    public boolean showEdit;

    /**
     * 0-学校库
     * 1-目标学校
     * 2-退回库
     */
    @Autowired
    public int formWay;

    @Override
    protected SchoolsPresenter createPresenter() {
        return new SchoolsPresenter(this);
    }

    @Override
    protected void setStatusBar() {
        setNoStatusBarByLight();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_add_school;
    }

    @Override
    protected void initView() {
        commonPresenter = new SchoolCommonPresenter(this);

        if (schoolId != -1) {
            //详情
            if (formWay == AppConstant.FORM_WAY_TARGET_SCHOOL) {
                presenter.getTargetSchoolById(String.valueOf(schoolId));
            } else if (formWay == AppConstant.FORM_WAY_BACK_SCHOOL) {
                presenter.getReturnSchoolById(String.valueOf(schoolId));
            } else {
                presenter.getSchoolById(String.valueOf(schoolId));
            }

            binding.title.setTitle(getString(R.string.edit_school));

            isAdd = false;
        } else {
            //宣传员
            commonPresenter.getStaffList();
        }


        currentGYear = currentBYear = String.valueOf(DateUtils.getCurrentYear());

        binding.etAdmissionUndergraduateNumber.setLabel(currentBYear + getString(R.string.undergradate_number));
        binding.etAdmissionSpecialtyNumber.setLabel(currentGYear + getString(R.string.speciaty_number));


        setAble();
    }

    @Override
    protected void addListener() {
        binding.llTel.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.etContactTel.getText())) {
                if (ParseUtils.isMobileNo(binding.etContactTel.getText())) {
                    DialogUtils.showTelDialog(context, v, binding.etContactTel.getText());
                } else {
                    showToast(getString(R.string.please_input_right_tel));
                }
            } else {
                showToast(getString(R.string.please_input_tel));
            }
        });

        binding.pickSchoolName.setTextClickListener(v ->
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_ADD_SEARCH)
                        .withInt("state", 0).navigation(this, REQUEST_CODE_ADD_SCHOOL));


        binding.etAdmissionUndergraduateNumber.addTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int total = ParseUtils.parseInt(s.toString()) + ParseUtils.parseInt(binding.etAdmissionSpecialtyNumber.getText());
                binding.tvAddmissionTotal.setText(String.valueOf(total));
            }
        });

        binding.etAdmissionSpecialtyNumber.addTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int total = ParseUtils.parseInt(s.toString()) + ParseUtils.parseInt(binding.etAdmissionUndergraduateNumber.getText());
                binding.tvAddmissionTotal.setText(String.valueOf(total));
            }
        });


        binding.tvSubmit.setOnClickListener(v -> {
            if (checkValue()) {
                return;
            }

            HashMap<String, Object> map = new HashMap<>();
            //详细地址
            map.put("address", binding.etAddressInfo.getText());
            //联系人
            map.put("contact", binding.etContact.getText());
            //联系人职务
            map.put("contactDuty", binding.etContactDuties.getText());
            //联系人电话
            map.put("contactPhone", binding.etContactTel.getText());
            //微信/qq
            map.put("socialNum", binding.etWx.getText());
            //是否挂牌生源基地 (0-不是，1-是)
            map.put("isListed", binding.rbListingYes.isChecked() ? 1 : 0);
            //本科学校类型
            if (bachelorType != -1) {
                map.put("bachelorType", bachelorType);
            }
            //学校类型
            if (schoolType != -1) {
                map.put("schoolType", schoolType);
            }
            //高职学校类型
            if (vocationType != -1) {
                map.put("vocationType", vocationType);
            }
            //专升本学校类型
            if (diplomaType != -1) {
                map.put("diplomaType", diplomaType);
            }
            if (areaId != -1 && !TextUtils.isEmpty(areaName)) {
                map.put("areaId", areaId);
                map.put("areaName", areaName);
            }
            //宣传员
            if (staffIds != null && staffIds.size() > 0) {
                map.put("staffIds", staffIds);
            }
            map.put("id", schoolId);

            if (isAdd) {
                presenter.addSchool(map);
            } else {
                presenter.updateSchool(map);
            }
        });

        binding.tvRest.setOnClickListener(v -> {
            reset();
        });

        binding.tvEdit.setOnClickListener(v -> {
            editAble = true;
            setAble();
        });
    }


    private void setAble() {
        if (editAble) {
            //区域负责人
            commonPresenter.getAddUserArea();

            //学校级别
            commonPresenter.getSchoolLevel();
            if (schoolId != -1) {
                binding.title.setTitle(getString(R.string.edit_school));
            } else {
                binding.title.setTitle(getString(R.string.school_add));
            }

            binding.llSearchTools.setVisibility(View.VISIBLE);
            binding.tvEdit.setVisibility(View.GONE);
            binding.tvRest.setVisibility(View.VISIBLE);
            binding.tvSubmit.setVisibility(View.VISIBLE);

        } else {
            binding.title.setTitle(getString(R.string.school_info));

            if (PermissionCommon.hasSchoolUpdate() && showEdit) {
                binding.llSearchTools.setVisibility(View.VISIBLE);
                binding.tvEdit.setVisibility(View.VISIBLE);
                binding.tvRest.setVisibility(View.GONE);
                binding.tvSubmit.setVisibility(View.GONE);
            } else {
                binding.llSearchTools.setVisibility(View.GONE);
            }
        }
        //学校名称
        binding.pickSchoolName.setPickAble(editAble);
        //详细地址
        binding.etAddressInfo.setEditAble(editAble);
        //联系人
        binding.etContact.setEditAble(editAble);
        //联系人职务
        binding.etContactDuties.setEditAble(editAble);
        //联系人电话
        binding.etContactTel.setEditAble(editAble);
        //wx
        binding.etWx.setEditAble(editAble);
        //本科录取人数
        binding.etAdmissionUndergraduateNumber.setEditAble(editAble);
        binding.etAdmissionUndergraduateNumber.setEditAble(editAble);
        //高职录取人数
        binding.etAdmissionSpecialtyNumber.setEditAble(editAble);
        binding.etAdmissionSpecialtyNumber.setEditAble(editAble);
        binding.rbListingYes.setEnabled(editAble);
        binding.rbListingNo.setEnabled(editAble);
        binding.pickLevelBachelor.setPickAble(editAble);
        binding.pickLevelVocation.setPickAble(editAble);
        binding.pickLevelDiploma.setPickAble(editAble);

        //区域/负责人
        binding.pickArea.setPickAble(editAble);
        binding.pickPropaganidst.setPickAble(editAble);


    }

    private void reset() {
        schoolId = -1;
        //学校名称
        binding.pickSchoolName.clear();
        //学校类别
        binding.pickClass.clear();
        //省市区
        binding.pickAddress.clear();
        //详细地址
        binding.etAddressInfo.clear();
        //联系人
        binding.etContact.clear();
        //联系人职务
        binding.etContactDuties.clear();
        //联系人电话
        binding.etContactTel.clear();
        //wx
        binding.etWx.setText("");
        //本科录取人数
        binding.etAdmissionUndergraduateNumber.setVisibility(View.GONE);
        //高职录取人数
        binding.etAdmissionSpecialtyNumber.setVisibility(View.GONE);

        binding.tvAddmissionTotal.setText("");
        binding.llTotal.setVisibility(View.GONE);
        //是否挂牌
        binding.rbListingYes.setChecked(true);
        areaId = -1;
        areaName = null;
        //区域/负责人
        binding.pickArea.clear();
        //宣传员
        staffIds = null;
        binding.pickPropaganidst.clearStaff();

        binding.pickLevelBachelor.setVisibility(View.GONE);
        binding.pickLevelVocation.setVisibility(View.GONE);
        binding.pickLevelDiploma.setVisibility(View.GONE);
        binding.pickLevelBachelor.clear();
        binding.pickLevelVocation.clear();
        binding.pickLevelDiploma.clear();
    }

    private boolean checkValue() {
        if (schoolId == -1) {
            showToast(getString(R.string.please_select_school));
            return true;
        }
        if (TextUtils.isEmpty(binding.pickAddress.getText())) {
            showToast(getString(R.string.please_select_address));
            return true;
        }
        if (TextUtils.isEmpty(binding.etContact.getText())) {
            showToast(getString(R.string.please_input_school_contact));
            return true;
        }
        if (TextUtils.isEmpty(binding.etContactDuties.getText())) {
            showToast(getString(R.string.please_input_school_contact_duty));
            return true;
        }
        if (TextUtils.isEmpty(binding.etContactTel.getText())) {
            showToast(getString(R.string.please_input_school_contact_tel));
            return true;
        }
        if (!ParseUtils.isMobileNo(binding.etContactTel.getText())) {
            showToast(getString(R.string.please_input_right_tel));
            return true;
        }
        if (areaId == -1 || TextUtils.isEmpty(areaName)) {
            showToast(getString(R.string.please_select_area_and_leader));
            return true;
        }

        return false;
    }

    @Override
    public void onSchoolList(List<SchoolListBean> o) {

    }


    @Override
    public void onAddSchool(String o) {
        showToast(o);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onDeleteSchool(String o) {

    }

    @Override
    public void onSchoolInfo(SchoolInfoBean o) {
        schoolInfoBean = o;
        if (o == null) {
            return;
        }

        //宣传员
        commonPresenter.getStaffList();
        //学校名称
        binding.pickSchoolName.setText(o.getSchoolName());
        //学校类别
        binding.pickClass.setText(o.getSchoolTypeName());

        schoolType = o.getSchoolType();
        if (o.getSchoolType() == 0) {
            binding.pickLevelBachelor.setVisibility(View.VISIBLE);
            binding.pickLevelVocation.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(o.getBachelorTypeName())) {
                bachelorType = o.getBachelorType();
                binding.pickLevelBachelor.setText(o.getBachelorTypeName());

            }
            if (!TextUtils.isEmpty(o.getVocationTypeName())) {
                vocationType = o.getVocationType();
                binding.pickLevelVocation.setText(o.getVocationTypeName());

            }
        } else {
            binding.pickLevelDiploma.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(o.getDiplomaTypeName())) {
                diplomaType = o.getDiplomaType();
                binding.pickLevelDiploma.setText(o.getDiplomaTypeName());

            }
        }

        //省市区
        binding.pickAddress.setText(StrUtils.isEmpty(o.getProvince()) + " " + StrUtils.isEmpty(o.getCity()) + " " + StrUtils.isEmpty(o.getRegion()));
        //详细地址
        binding.etAddressInfo.setText(StrUtils.isEmpty(o.getAddress()));
        //联系人
        binding.etContact.setText(StrUtils.isEmpty(o.getContact()));
        //联系人职务
        binding.etContactDuties.setText(StrUtils.isEmpty(o.getContactDuty()));
        //联系人电话
        binding.etContactTel.setText(StrUtils.isEmpty(o.getContactPhone()));
        //wx
        binding.etWx.setText(StrUtils.isEmpty(o.getSocialNum()));
        //本科录取人数
        if (o.getBachelorEnrollList() != null && o.getBachelorEnrollList().size() > 0) {
            SchoolInfoBean.BachelorEnrollListDTO dto = o.getBachelorEnrollList().get(0);
            currentBYear = String.valueOf(dto.getYear());
            binding.etAdmissionUndergraduateNumber.setLabel(dto.getYear() + getString(R.string.undergradate_number));
            binding.etAdmissionUndergraduateNumber.setText(String.valueOf(dto.getEnrollNumber()));
        } else {
            binding.etAdmissionUndergraduateNumber.setText("0");
        }
        binding.etAdmissionUndergraduateNumber.setVisibility(View.VISIBLE);

        //高职录取人数
        if (o.getVocationEnrollList() != null && o.getVocationEnrollList().size() > 0) {
            SchoolInfoBean.BachelorEnrollListDTO dto = o.getVocationEnrollList().get(0);
            currentGYear = String.valueOf(dto.getYear());
            binding.etAdmissionSpecialtyNumber.setLabel(dto.getYear() + getString(R.string.speciaty_number));
            binding.etAdmissionSpecialtyNumber.setText(String.valueOf(dto.getEnrollNumber()));
        } else {
            binding.etAdmissionSpecialtyNumber.setText("0");
        }
        binding.etAdmissionSpecialtyNumber.setVisibility(View.VISIBLE);

        int total = ParseUtils.parseInt(binding.etAdmissionUndergraduateNumber.getText())
                + ParseUtils.parseInt(binding.etAdmissionSpecialtyNumber.getText());

        binding.tvAddmissionTotal.setText(String.valueOf(total));
        binding.llTotal.setVisibility(View.VISIBLE);


        //是否挂牌
        if (o.getIsListed() == 1) {
            binding.rbListingYes.setChecked(true);
        } else {
            binding.rbListingNo.setChecked(true);
        }

        areaId = o.getAreaId();
        areaName = o.getAreaName();

        //区域/负责人
        binding.pickArea.setText(StrUtils.isEmpty(o.getAreaStaffName()) + " " + StrUtils.isEmpty(o.getAreaName()));

        //宣传员
        staffIds = new ArrayList<>();
        if (o.getPropagandistVoList() != null && o.getPropagandistVoList().size() > 0) {
            StringBuilder name = new StringBuilder();
            for (StaffBean bean : o.getPropagandistVoList()) {
                if (name.length() > 0) {
                    name.append(",");
                }
                name.append(bean.getStaffName());
                staffIds.add(bean.getId());
            }
            binding.pickPropaganidst.setText(name.toString());
        }


    }

    @Override
    public void onUpdateSchool(String o) {
        showToast(o);
        setResult(RESULT_OK);
        finish();
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
        if (beans == null) {
            return;
        }
        binding.pickLevelBachelor.setData(beans.getBachelorType(), "levelName", type -> bachelorType = type.getSchoolLevel());
        binding.pickLevelVocation.setData(beans.getVocationType(), "levelName", type -> vocationType = type.getSchoolLevel());
        binding.pickLevelDiploma.setData(beans.getDiplomaType(), "levelName", type -> diplomaType = type.getSchoolLevel());
    }

    @Override
    public void ontUserArea(List<AreaUserBean> beans) {
        List<String> list = new ArrayList<>();
        if (beans != null && beans.size() > 0) {
            for (AreaUserBean bean : beans) {
                list.add(bean.getStaffName() + " " + bean.getAreaName());
            }
        }
        binding.pickArea.setData(list, s -> {
            int index = list.indexOf(s);
            AreaUserBean areaUserBean = null;
            if (beans != null) {
                areaUserBean = beans.get(index);
            }
            if (areaUserBean != null) {
                areaId = areaUserBean.getId();
                areaName = areaUserBean.getAreaName();
            }
        });
    }

    @Override
    public void ontStaffList(List<StaffBean> o) {
        List<StaffBean> old = new ArrayList<>();
        if (schoolInfoBean != null) {
            old = schoolInfoBean.getPropagandistVoList();
        }

        binding.pickPropaganidst.setPersonData(o, old, staffList -> {
            staffIds = new ArrayList<>();
            if (staffList != null && staffList.size() > 0) {
                for (StaffBean bean : staffList) {
                    staffIds.add(bean.getId());
                }
            }
        });
    }

    @Override
    public void onSortList(List<SimpleBean> o) {

    }

    @Override
    protected void onDestroy() {
        commonPresenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_SCHOOL && resultCode == RESULT_OK) {
            if (data != null) {
                SchoolInfoBean listBean = (SchoolInfoBean) data.getSerializableExtra("school");
                if (listBean != null) {
                    schoolId = listBean.getId();
                    binding.pickSchoolName.setText(listBean.getSchoolName());
                    binding.pickClass.setText(listBean.getSchoolTypeName());
                    schoolType = listBean.getSchoolType();
                    binding.pickAddress.setText(StrUtils.isEmpty(listBean.getProvince()) + " " + StrUtils.isEmpty(listBean.getCity())
                            + " " + StrUtils.isEmpty(listBean.getRegion()));

                    if (listBean.getSchoolType() == 0) {
                        binding.pickLevelBachelor.setVisibility(View.VISIBLE);
                        binding.pickLevelVocation.setVisibility(View.VISIBLE);
                    } else {
                        binding.pickLevelDiploma.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
}
