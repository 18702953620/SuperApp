package com.shenyuan.superapp.admission.ui.claim;

import android.annotation.SuppressLint;
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
import com.shenyuan.admission.databinding.AcClaimAddBinding;
import com.shenyuan.superapp.admission.api.presenter.ClaimPresenter;
import com.shenyuan.superapp.admission.api.view.ClaimView;
import com.shenyuan.superapp.admission.bean.ClaimInfoBean;
import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.widget.datepicker.DatePickerUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/17 11:04
 * desc 添加报账
 */
@Route(path = ARouterPath.Admission.ADMISSION_REIMBURSE_ADD)
public class AddClaimActivity extends BaseActivity<AcClaimAddBinding, ClaimPresenter> implements ClaimView {

    public static final int REQUEST_CODE_CLAIM = 100;
    private List<Integer> planIds;
    @Autowired
    public int claimId = -1;

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

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            getResult();
        }
    };

    @Autowired
    public int feedId;

    @Override
    protected ClaimPresenter createPresenter() {
        return new ClaimPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_claim_add;
    }

    @Override
    protected void initView() {
        if (claimId != -1) {
            presenter.getClaimById(claimId);
        }

        setAble();
    }

    @Override
    protected void addListener() {
        binding.tvStart.setOnClickListener(v -> {
            DatePickerUtils.showYYMMDDDialog(context, binding.tvStart);
        });
        binding.tvEnd.setOnClickListener(v -> {

            if (TextUtils.isEmpty(getTv(binding.tvStart))) {
                showToast(getString(R.string.please_select_start_time));
                return;
            }

            DatePickerUtils.showYYMMDDDialog(context, binding.tvEnd);
        });

        binding.tvRest.setOnClickListener(v -> {
            if ("拒绝".equals(getTv(binding.tvRest))) {
                aduitPlan(3);
            } else {
                saveOrUpdate(0);
            }
        });
        binding.tvSubmit.setOnClickListener(v -> {
            if ("通过".equals(getTv(binding.tvSubmit))) {
                aduitPlan(2);
            } else {
                saveOrUpdate(1);
            }
        });

        binding.tvEdit.setOnClickListener(v -> {
            editAble = true;
            setAble();
        });

        binding.pickFundType.setTextClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_REIMBURSE_PLAN).navigation(this, REQUEST_CODE_CLAIM);
        });


        binding.stPublicGoBack.getEditText().addTextChangedListener(textWatcher);
        binding.stSectionPublicGoBack.getEditText().addTextChangedListener(textWatcher);
        binding.stRemTrain.getEditText().addTextChangedListener(textWatcher);
        binding.stRoadToll.getEditText().addTextChangedListener(textWatcher);
        binding.stFuelCost.getEditText().addTextChangedListener(textWatcher);
        binding.stOher.getEditText().addTextChangedListener(textWatcher);
        binding.stRemPark.getEditText().addTextChangedListener(textWatcher);
        binding.stRemEntertain.getEditText().addTextChangedListener(textWatcher);
        binding.stRemRoom.getEditText().addTextChangedListener(textWatcher);
        binding.stRemSubsidy.getEditText().addTextChangedListener(textWatcher);
        binding.stRemMail.getEditText().addTextChangedListener(textWatcher);


        binding.etCarDay.addTextChangedListener(textWatcher);
        binding.etCarPrice.addTextChangedListener(textWatcher);
        binding.etCarUnit.addTextChangedListener(textWatcher);
        binding.etPeDay.addTextChangedListener(textWatcher);
        binding.etPePrice.addTextChangedListener(textWatcher);
        binding.etPeUnit.addTextChangedListener(textWatcher);
    }

    /**
     * 审核
     *
     * @param status status
     */
    private void aduitPlan(int status) {
        HashMap<String, Object> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(claimId);
        map.put("ids", list);
        map.put("status", status);
        presenter.aduitClaim(map);
    }

    private void saveOrUpdate(int state) {
        if (TextUtils.isEmpty(binding.stRemName.getText())) {
            showToast(getString(R.string.please_input_claim_name));
            return;
        }
        if (TextUtils.isEmpty(binding.tvStart.getText())) {
            showToast(getString(R.string.please_select_claim_start_time));
            return;
        }
        if (TextUtils.isEmpty(binding.tvEnd.getText())) {
            showToast(getString(R.string.please_select_claim_end_time));
            return;
        }
        if (TextUtils.isEmpty(binding.stRemNumber.getText())) {
            showToast(getString(R.string.please_input_business_count));
            return;
        }
        if (planIds == null || planIds.size() == 0) {
            showToast(getString(R.string.please_select_plan));
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        //报账名称
        map.put("claimName", binding.stRemName.getText());
        //费用发生开始时间
        map.put("startTime", getTv(binding.tvStart));
        //费用发生结束时间
        map.put("endTime", getTv(binding.tvEnd));
        //出差人数
        map.put("travelPersonNum", binding.stRemNumber.getText());
        //火车票费用
        map.put("trainFee", ParseUtils.parseDouble(binding.stRemTrain.getText()));
        //往返公共交通费用
        map.put("roundTripFee", ParseUtils.parseDouble(binding.stPublicGoBack.getText()));
        //区间公共交通费用
        map.put("intervalTripFee", ParseUtils.parseDouble(binding.stSectionPublicGoBack.getText()));
        //汽车租赁费
        map.put("carRentFee", ParseUtils.parseDouble(getTv(binding.etCarPrice)));
        //租赁车辆数
        map.put("carRentNum", ParseUtils.parseInt(getTv(binding.etCarUnit)));
        //租赁天数
        map.put("carRentDays", ParseUtils.parseInt(getTv(binding.etCarDay)));
        //过路费
        map.put("tollFee", ParseUtils.parseDouble(binding.stRoadToll.getText()));
        //停车费
        map.put("parkFee", ParseUtils.parseDouble(binding.stRemPark.getText()));
        //燃油费
        map.put("fuelFee", ParseUtils.parseDouble(binding.stFuelCost.getText()));
        //招待费
        map.put("entertainmentFee", ParseUtils.parseDouble(binding.stRemEntertain.getText()));
        //住宿费
        map.put("roomFee", ParseUtils.parseDouble(binding.stRemRoom.getText()));
        //人员补助费用
        map.put("subsidyFee", ParseUtils.parseDouble(binding.stRemSubsidy.getText()));
        //私家车日费单价
        map.put("privateCarFee", ParseUtils.parseDouble(getTv(binding.etPePrice)));
        //私家车辆数
        map.put("privateCarNum", ParseUtils.parseInt(getTv(binding.etPeUnit)));
        //私家车使用天数
        map.put("privateCarDays", ParseUtils.parseInt(getTv(binding.etPeDay)));
        //邮寄费用
        map.put("mailFee", ParseUtils.parseDouble(binding.stRemMail.getText()));
        //其他费用
        map.put("otherFee", ParseUtils.parseDouble(binding.stOher.getText()));
        //关联经费预算
        if (planIds != null && planIds.size() > 0) {
            map.put("planIdList", planIds);
        }
        map.put("status", state);
        if (claimId != -1) {
            map.put("id", claimId);
            presenter.updateClaim(map);
        } else {
            presenter.addClaim(map);
        }


    }


    @SuppressLint("SetTextI18n")
    private void getResult() {
        double hc = ParseUtils.parseDouble(binding.stRemTrain.getText());
        double wf = ParseUtils.parseDouble(binding.stPublicGoBack.getText());
        double qj = ParseUtils.parseDouble(binding.stSectionPublicGoBack.getText());
        double gl = ParseUtils.parseDouble(binding.stRoadToll.getText());
        double ry = ParseUtils.parseDouble(binding.stFuelCost.getText());
        double qt = ParseUtils.parseDouble(binding.stOher.getText());
        double tc = ParseUtils.parseDouble(binding.stRemPark.getText());
        double zd = ParseUtils.parseDouble(binding.stRemEntertain.getText());
        double zs = ParseUtils.parseDouble(binding.stRemRoom.getText());
        double bz = ParseUtils.parseDouble(binding.stRemSubsidy.getText());
        double yj = ParseUtils.parseDouble(binding.stRemMail.getText());


        double zlf = ParseUtils.parseDouble(getTv(binding.etCarPrice));
        double zlj = ParseUtils.parseDouble(getTv(binding.etCarUnit));
        double zlt = ParseUtils.parseDouble(getTv(binding.etCarDay));


        double sjcf = ParseUtils.parseDouble(getTv(binding.etPePrice));
        double sjcl = ParseUtils.parseDouble(getTv(binding.etPeDay));
        double sjct = ParseUtils.parseDouble(getTv(binding.etPeUnit));


        BigDecimal zl = BigDecimal.valueOf(zlf).multiply(BigDecimal.valueOf(zlj)).multiply(BigDecimal.valueOf(zlt));
        BigDecimal sjc = BigDecimal.valueOf(sjcf).multiply(BigDecimal.valueOf(sjcl)).multiply(BigDecimal.valueOf(sjct));

        binding.tvCarTotal.setText(zl.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        binding.tvPeTotal.setText(sjc.setScale(2, BigDecimal.ROUND_HALF_UP).toString());


        BigDecimal total = BigDecimal.valueOf(wf).add(BigDecimal.valueOf(qj)).add(BigDecimal.valueOf(hc)).add(BigDecimal.valueOf(gl)).add(BigDecimal.valueOf(ry))
                .add(BigDecimal.valueOf(qt)).add(BigDecimal.valueOf(zs)).add(BigDecimal.valueOf(bz))
                .add(BigDecimal.valueOf(tc))
                .add(BigDecimal.valueOf(zd))
                .add(BigDecimal.valueOf(yj)).add(zl).add(sjc);

        binding.stReal.setText(total.subtract(BigDecimal.valueOf(bz)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        binding.stTotal.setText(total.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }

    @Override
    public void onAddClaim(String o) {
        showToast(getString(R.string.succ_add));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onPlanList(List<PlanListBean> o) {

    }

    @Override
    public void onClaimList(List<ClaimListBean> o) {

    }

    @Override
    public void onClaimInfo(ClaimInfoBean o) {
        if (o == null) {
            return;
        }

        //报账名称
        binding.stRemName.setText(o.getClaimName());
        //费用发生开始时间
        binding.tvStart.setText(o.getStartTime());
        //费用发生结束时间
        binding.tvEnd.setText(o.getEndTime());
        //出差人数
        binding.stRemNumber.setText(String.valueOf(o.getTravelPersonNum()));
        //火车票费用
        binding.stRemTrain.setText(StrUtils.valueOf(o.getTrainFee()));
        //往返公共交通费用
        binding.stPublicGoBack.setText(StrUtils.valueOf(o.getRoundTripFee()));
        //区间公共交通费用
        binding.stSectionPublicGoBack.setText(StrUtils.valueOf(o.getIntervalTripFee()));
        //汽车租赁费
        binding.etCarPrice.setText(StrUtils.valueOf(o.getCarRentFee()));
        //租赁车辆数
        binding.etCarUnit.setText(String.valueOf(o.getCarRentNum()));
        //租赁天数
        binding.etCarDay.setText(String.valueOf(o.getCarRentDays()));

        //过路费
        binding.stRoadToll.setText(StrUtils.valueOf(o.getTollFee()));
        //停车费
        binding.stRemPark.setText(StrUtils.valueOf(o.getParkFee()));
        //燃油费
        binding.stFuelCost.setText(StrUtils.valueOf(o.getFuelFee()));
        //招待费
        binding.stRemEntertain.setText(StrUtils.valueOf(o.getEntertainmentFee()));
        //住宿费
        binding.stRemRoom.setText(StrUtils.valueOf(o.getRoomFee()));
        //人员补助费用
        binding.stRemSubsidy.setText(StrUtils.valueOf(o.getSubsidyFee()));
        //私家车日费单价
        binding.etPePrice.setText(StrUtils.valueOf(o.getPrivateCarFee()));
        //私家车辆数
        binding.etPeUnit.setText(String.valueOf(o.getPrivateCarNum()));
        //私家车使用天数
        binding.etPeDay.setText(String.valueOf(o.getPrivateCarDays()));
        //邮寄费用
        binding.stRemMail.setText(StrUtils.valueOf(o.getMailFee()));
        //其他费用
        binding.stOher.setText(StrUtils.valueOf(o.getOtherFee()));

        binding.stTotal.setText(StrUtils.valueOf(o.getTotalFeeSubsidy()));

        binding.stReal.setText(StrUtils.valueOf(o.getTotalFeeNosubsidy()));

        binding.pickFundType.setText(StrUtils.valueOf(o.getPlanTotalCostMoney()));

        planIds = o.getPlanIdList();

        if (o.getStatus() == 0 || o.getStatus() == 3) {
            binding.llBottom.setVisibility(View.VISIBLE);
            binding.tvRest.setText(getString(R.string.save_draft));
            binding.tvSubmit.setText(getString(R.string.submit_for_review));
        } else if (o.getStatus() == 1) {
            if (PermissionCommon.hasClaimAduit()) {
                binding.llBottom.setVisibility(View.VISIBLE);
                binding.tvRest.setText(getString(R.string.refuse));
                binding.tvSubmit.setText(getString(R.string.pass));
            } else {
                binding.llBottom.setVisibility(View.GONE);
            }
        } else if (o.getStatus() == 2) {
            binding.llBottom.setVisibility(View.GONE);
        }

    }

    private void setAble() {
        //报账名称
        binding.stRemName.setEditAble(editAble);

        if (editAble) {
            binding.llBottom.setVisibility(View.VISIBLE);
            binding.tvEdit.setVisibility(View.GONE);
            binding.tvRest.setVisibility(View.VISIBLE);
            binding.tvSubmit.setVisibility(View.VISIBLE);
        } else {
            binding.tvStart.setEnabled(editAble);
            binding.tvEnd.setEnabled(editAble);

            if (PermissionCommon.hasClaimUpdate() && showEdit) {
                binding.llBottom.setVisibility(View.VISIBLE);
                binding.tvEdit.setVisibility(View.VISIBLE);
                binding.tvRest.setVisibility(View.GONE);
                binding.tvSubmit.setVisibility(View.GONE);
            } else {
                binding.llBottom.setVisibility(View.GONE);
            }
        }

        //出差人数
        binding.stRemNumber.setEditAble(editAble);
        //火车票费用
        binding.stRemTrain.setEditAble(editAble);
        //往返公共交通费用
        binding.stPublicGoBack.setEditAble(editAble);
        //区间公共交通费用
        binding.stSectionPublicGoBack.setEditAble(editAble);
        //汽车租赁费
        binding.etCarPrice.setEnabled(editAble);
        //租赁车辆数
        binding.etCarUnit.setEnabled(editAble);
        //租赁天数
        binding.etCarDay.setEnabled(editAble);

        //过路费
        binding.stRoadToll.setEditAble(editAble);
        //停车费
        binding.stRemPark.setEditAble(editAble);
        //燃油费
        binding.stFuelCost.setEditAble(editAble);
        //招待费
        binding.stRemEntertain.setEditAble(editAble);
        //住宿费
        binding.stRemRoom.setEditAble(editAble);
        //人员补助费用
        binding.stRemSubsidy.setEditAble(editAble);
        //私家车日费单价
        binding.etPePrice.setEnabled(editAble);
        //私家车辆数
        binding.etPeUnit.setEnabled(editAble);
        //私家车使用天数
        binding.etPeDay.setEnabled(editAble);
        //邮寄费用
        binding.stRemMail.setEditAble(editAble);
        //其他费用
        binding.stOher.setEditAble(editAble);

        binding.pickFundType.setPickAble(editAble);
    }

    @Override
    public void onAduitClaim(String o) {
        showToast(getString(R.string.succ_option));
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void onDeleteClaim(String o) {
        showToast(getString(R.string.succ_delete));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onTopClaim(String o) {

    }

    @Override
    public void onUpdateClaim(String o) {
        showToast(getString(R.string.succ_edit));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CLAIM && resultCode == RESULT_OK) {
            if (data != null) {
                String pIds = data.getStringExtra("planIds");
                String total = data.getStringExtra("total");
                binding.pickFundType.setText(total);
                if (!TextUtils.isEmpty(pIds)) {
                    planIds = new ArrayList<>();
                    if (pIds.contains(",")) {
                        String[] ids = pIds.split(",");
                        for (String id : ids) {
                            planIds.add(ParseUtils.parseInt(id));
                        }
                    } else {
                        planIds.add(ParseUtils.parseInt(pIds));
                    }
                }
            }
        }
    }
}
