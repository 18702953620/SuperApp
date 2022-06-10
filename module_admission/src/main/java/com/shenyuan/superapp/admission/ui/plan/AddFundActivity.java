package com.shenyuan.superapp.admission.ui.plan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcAddFundBinding;
import com.shenyuan.superapp.admission.api.presenter.PlanPresenter;
import com.shenyuan.superapp.admission.api.view.PlanView;
import com.shenyuan.superapp.admission.bean.PlanInfoBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.SimpleStringBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.utils.StrUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/15 10:40
 * desc 添加经费预算
 */

@Route(path = ARouterPath.Admission.ADMISSION_PLAN_ADD_FUND)
public class AddFundActivity extends BaseActivity<AcAddFundBinding, PlanPresenter> implements PlanView {
    /**
     * 经费类别 (字典值)
     */
    private SimpleStringBean feeType;

    @Autowired
    public HashMap<String, Object> info;

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

    @Override
    protected PlanPresenter createPresenter() {
        return new PlanPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_add_fund;
    }

    @Override
    protected void initView() {
        presenter.getFeeTypeList();
        if (info != null) {

            feeType = new SimpleStringBean();
            feeType.setKey((String) info.get("feeType"));
            feeType.setValue((String) info.get("feeTypeName"));

            //经费类别
            binding.pickFundType.setText(StrUtils.isEmpty(info.get("feeTypeName")));
            //往返公共交通费
            binding.stPublicGoBack.setText(StrUtils.isEmpty(info.get("roundTripFee")));
            //区间公共交通费
            binding.stSectionPublicGoBack.setText(StrUtils.isEmpty(info.get("intervalTripFee")));
            //汽车租赁费
            binding.stLease.setText(StrUtils.isEmpty(info.get("carRentFee")));
            //过路费
            binding.stRoadToll.setText(StrUtils.isEmpty(info.get("tollFee")));
            //燃油费
            binding.stFuelCost.setText(StrUtils.isEmpty(info.get("fuelFee")));

            //日房价
            binding.etRoomPrice.setText(StrUtils.isEmpty(info.get("roomFee")));
            //间
            binding.etRoomUnit.setText(StrUtils.isEmpty(info.get("roomNum")));
            //天
            binding.etRoomDay.setText(StrUtils.isEmpty(info.get("roomDays")));
            //总共
            binding.tvRoomTotal.setText(StrUtils.isEmpty(info.get("totalRoomFee")));

            //生活补助
            binding.etSubsidyPrice.setText(StrUtils.isEmpty(info.get("subsidyFee")));
            //天
            binding.etSubsidyDay.setText(StrUtils.isEmpty(info.get("subsidyDays")));
            //人
            binding.etSubsidyPerson.setText(StrUtils.isEmpty(info.get("subsidyPersons")));
            //总共
            binding.tvSubsidyTotal.setText(StrUtils.isEmpty(info.get("totalSubsidyFee")));

            //其他
            binding.stOther.setText(StrUtils.isEmpty(info.get("otherFee")));
            //说明
            binding.etOther.setText(StrUtils.isEmpty(info.get("otherFeePurpose")));

            //总共
            binding.stTotal.setText(StrUtils.isEmpty(info.get("totalCostMoney")));
        }
    }

    @Override
    protected void addListener() {
        binding.tvSubmit.setOnClickListener(v -> {

            if (feeType == null) {
                showToast(getString(R.string.please_select_fee_type));
                return;
            }
            HashMap<String, Object> map = new HashMap<>();

            map.put("feeType", feeType.getKey());
            map.put("feeTypeName", feeType.getValue());
            //往返公共交通费
            map.put("roundTripFee", ParseUtils.parseDouble(binding.stPublicGoBack.getText()));

            //区间公共交通费
            map.put("intervalTripFee", ParseUtils.parseDouble(binding.stSectionPublicGoBack.getText()));
            //汽车租赁费
            map.put("carRentFee", ParseUtils.parseDouble(binding.stLease.getText()));
            //过路费
            map.put("tollFee", ParseUtils.parseDouble(binding.stRoadToll.getText()));
            //燃油费
            map.put("fuelFee", ParseUtils.parseDouble(binding.stFuelCost.getText()));
            //单间住宿费
            map.put("roomFee", ParseUtils.parseDouble(getTv(binding.etRoomPrice)));
            //住房间数
            map.put("roomNum", ParseUtils.parseInt(getTv(binding.etRoomUnit)));
            //住房天数
            map.put("roomDays", ParseUtils.parseInt(getTv(binding.etRoomDay)));
            //住房总计
            map.put("totalRoomFee", ParseUtils.parseDouble(getTv(binding.tvRoomTotal)));

            //单天生活补助
            map.put("subsidyFee", ParseUtils.parseDouble(getTv(binding.etSubsidyPrice)));
            //住房间数
            map.put("subsidyDays", ParseUtils.parseInt(getTv(binding.etSubsidyDay)));
            //生活补助人数
            map.put("subsidyPersons", ParseUtils.parseInt(getTv(binding.etSubsidyPerson)));
            //生活补助总计
            map.put("totalSubsidyFee", ParseUtils.parseDouble(getTv(binding.tvSubsidyTotal)));


            //其他费用
            map.put("otherFee", ParseUtils.parseDouble(binding.stOther.getText()));
            //其他费用 用途
            map.put("otherFeePurpose", getTv(binding.etOther));
            //总计
            map.put("totalCostMoney", ParseUtils.parseDouble(binding.stTotal.getText()));


            Intent intent = new Intent();
            intent.putExtra("fund", map);
            setResult(RESULT_OK, intent);
            finish();
        });

        binding.tvRest.setOnClickListener(v -> {
            feeType = null;

            //经费类别
            binding.pickFundType.clear();
            //往返公共交通费
            binding.stPublicGoBack.clear();
            //区间公共交通费
            binding.stSectionPublicGoBack.clear();
            //汽车租赁费
            binding.stLease.clear();
            //过路费
            binding.stRoadToll.clear();
            //燃油费
            binding.stFuelCost.clear();

            //日房价
            binding.etRoomPrice.setText("");
            //间
            binding.etRoomUnit.setText("");
            //天
            binding.etRoomDay.setText("");
            //总共
            binding.tvRoomTotal.setText("");

            //生活补助
            binding.etSubsidyPrice.setText("");
            //天
            binding.etSubsidyDay.setText("");
            //人
            binding.etSubsidyPerson.setText("");
            //总共
            binding.tvSubsidyTotal.setText("");

            //其他
            binding.stOther.clear();
            //说明
            binding.etOther.setText("");
            //总共
            binding.stTotal.clear();
        });

        binding.stPublicGoBack.getEditText().addTextChangedListener(textWatcher);
        binding.stSectionPublicGoBack.getEditText().addTextChangedListener(textWatcher);
        binding.stLease.getEditText().addTextChangedListener(textWatcher);
        binding.stRoadToll.getEditText().addTextChangedListener(textWatcher);
        binding.stFuelCost.getEditText().addTextChangedListener(textWatcher);
        binding.stOther.getEditText().addTextChangedListener(textWatcher);


        binding.etRoomPrice.addTextChangedListener(textWatcher);
        binding.etRoomUnit.addTextChangedListener(textWatcher);
        binding.etRoomDay.addTextChangedListener(textWatcher);
        binding.etSubsidyPrice.addTextChangedListener(textWatcher);
        binding.etSubsidyDay.addTextChangedListener(textWatcher);
        binding.etSubsidyPerson.addTextChangedListener(textWatcher);
    }


    @SuppressLint("SetTextI18n")
    private void getResult() {
        double wf = ParseUtils.parseDouble(binding.stPublicGoBack.getText());
        double qj = ParseUtils.parseDouble(binding.stSectionPublicGoBack.getText());
        double qc = ParseUtils.parseDouble(binding.stLease.getText());
        double gl = ParseUtils.parseDouble(binding.stRoadToll.getText());
        double ry = ParseUtils.parseDouble(binding.stFuelCost.getText());
        double qt = ParseUtils.parseDouble(binding.stOther.getText());


        double zsy = ParseUtils.parseDouble(getTv(binding.etRoomPrice));
        double zsj = ParseUtils.parseDouble(getTv(binding.etRoomUnit));
        double zst = ParseUtils.parseDouble(getTv(binding.etRoomDay));


        double bzy = ParseUtils.parseDouble(getTv(binding.etSubsidyPrice));
        double bzt = ParseUtils.parseDouble(getTv(binding.etSubsidyDay));
        double bzr = ParseUtils.parseDouble(getTv(binding.etSubsidyPerson));


        BigDecimal zs = BigDecimal.valueOf(zsy).multiply(BigDecimal.valueOf(zsj)).multiply(BigDecimal.valueOf(zst));
        BigDecimal bz = BigDecimal.valueOf(bzy).multiply(BigDecimal.valueOf(bzt)).multiply(BigDecimal.valueOf(bzr));

        binding.tvRoomTotal.setText(zs.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        binding.tvSubsidyTotal.setText(bz.setScale(2, BigDecimal.ROUND_HALF_UP).toString());


        BigDecimal total = BigDecimal.valueOf(wf).add(BigDecimal.valueOf(qj)).add(BigDecimal.valueOf(qc))
                .add(BigDecimal.valueOf(gl)).add(BigDecimal.valueOf(ry))
                .add(BigDecimal.valueOf(qt)).add(zs).add(bz);

        binding.stTotal.setText(total.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }

    @Override
    public void onExamineList(List<SimpleBean> o) {

    }

    @Override
    public void onTaskList(List<SimpleBean> o) {

    }

    @Override
    public void onPlanList(List<PlanListBean> o) {

    }

    @Override
    public void onCreateList(List<SimpleStringBean> o) {

    }

    @Override
    public void onFeeTypeList(List<SimpleStringBean> o) {
        binding.pickFundType.setData(o, "value", bean -> feeType = bean);
    }

    @Override
    public void onPropagationWayDictListList(List<SimpleStringBean> o) {

    }

    @Override
    public void onMadeList(List<SimpleBean> o) {

    }

    @Override
    public void onPlanStaffList(List<StaffBean> o) {

    }

    @Override
    public void onAddPlan(String o) {

    }

    @Override
    public void onPlanInfo(PlanInfoBean o) {

    }

    @Override
    public void onAduitPlan(String o) {

    }

    @Override
    public void onUpdatePlan(String o) {

    }
}
