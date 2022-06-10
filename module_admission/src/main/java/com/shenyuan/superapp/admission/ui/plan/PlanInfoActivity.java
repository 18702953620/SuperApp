package com.shenyuan.superapp.admission.ui.plan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcPlanInfoBinding;
import com.shenyuan.admission.databinding.ItemPlanInfoLineBinding;
import com.shenyuan.admission.databinding.ItemPlanInfoSchoolBinding;
import com.shenyuan.superapp.admission.api.presenter.PlanPresenter;
import com.shenyuan.superapp.admission.api.view.PlanView;
import com.shenyuan.superapp.admission.bean.PlanInfoBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.SimpleStringBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.widget.SimEditText;
import com.shenyuan.superapp.common.widget.datepicker.DatePickerUtils;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/15 19:13
 * desc 招生方案详情
 */
@Route(path = ARouterPath.Admission.ADMISSION_PLAN_INFO)
public class PlanInfoActivity extends BaseActivity<AcPlanInfoBinding, PlanPresenter> implements PlanView {
    private BaseQuickAdapter<PlanInfoBean.PlanLine, BaseDataBindingHolder> lineAdapter;
    public static final int REQUEST_CODE_PLAN_INFO = 100;
    @Autowired
    public int planId;
    @Autowired
    public int status;

    /**
     * 是否展示编辑按钮
     */
    @Autowired
    public boolean showEdit;
    /**
     * 是否可编辑
     */
    @Autowired
    public boolean editAble;
    /**
     * 宣传方式
     */
    private List<SimpleStringBean> wayListBeans;
    /**
     * 喜报
     */
    private List<SimpleBean> newsListBeans;
    /**
     * 宣传员
     */
    private List<StaffBean> staffBeans;
    /**
     * 任务
     */
    private List<SimpleBean> taskBeans;

    private PlanInfoBean planInfoBean;

    private int linePosition;
    private int schoolPosition;

    private final HashMap<Integer, BaseQuickAdapter<PlanInfoBean.PlanLine.TargetSchool, BaseDataBindingHolder>> mpAdapter = new HashMap<>();

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
    private SimpleStringBean feeType;

    @Override
    protected PlanPresenter createPresenter() {
        return new PlanPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_plan_info;
    }

    @Override
    protected void initView() {
        presenter.getPlanById(String.valueOf(planId));
        if (status == 0 || status == 3) {
            //出差任务
            presenter.getPlanTaskList();
            //是否制作喜报
            presenter.getMadeList();
            //宣传方式
            presenter.getPropagationWayDictList();
            //宣传员
            presenter.getPlanStaffList();
            //经费类型
            presenter.getFeeTypeList();
        }
        lineAdapter = new BaseQuickAdapter<PlanInfoBean.PlanLine, BaseDataBindingHolder>(R.layout.item_plan_info_line) {
            @SuppressLint("DefaultLocale")
            @Override
            protected void convert(@NotNull BaseDataBindingHolder holder, PlanInfoBean.PlanLine sm) {
                ItemPlanInfoLineBinding bi = (ItemPlanInfoLineBinding) holder.getDataBinding();
                if (bi == null) {
                    return;
                }

                if (sm.isSelect()) {
                    bi.ivLineArrow.setBackgroundResource(R.mipmap.ic_plan_arrow_up);
                    bi.llInfo.setVisibility(View.VISIBLE);
                } else {
                    bi.ivLineArrow.setBackgroundResource(R.mipmap.ic_plan_arrow_down);
                    bi.llInfo.setVisibility(View.GONE);
                }

                bi.tvLineTitle.setText(String.format("线路方案 - %d", holder.getAdapterPosition() + 1));
                //起点-终点
                bi.tvStart.setText(sm.getLineStart());
                bi.tvEnd.setText(sm.getLineEnd());

                //开始-结束
                bi.tvStartTime.setText(sm.getBeginTime());
                bi.tvEndTime.setText(sm.getEndTime());
                //宣传员
                bi.pickPropaganidst.setText(sm.getStaffNames());
                //任务类型
                bi.pickTask.setText(sm.getTaskTypeValue());

                bi.tvStart.setEnabled(editAble);
                bi.tvEnd.setEnabled(editAble);
                bi.pickPropaganidst.setPickAble(editAble);
                bi.pickTask.setPickAble(editAble);

                bi.pickPropaganidst.setPersonData(staffBeans, sm.getPropagandistVoList(), staffBeans -> {
                    StringBuilder ids = new StringBuilder();
                    StringBuilder names = new StringBuilder();
                    if (staffBeans != null && staffBeans.size() > 0) {
                        for (StaffBean bean : staffBeans) {
                            if (ids.length() > 0) {
                                ids.append(",");
                            }
                            ids.append(bean.getId());
                            if (names.length() > 0) {
                                names.append(",");
                            }
                            names.append(bean.getStaffName());
                        }
                    }
                    sm.setStaffIds(ids.toString());
                    sm.setStaffNames(names.toString());
                    bi.pickPropaganidst.setText(sm.getStaffNames());
                });

                bi.pickTask.setData(taskBeans, "value", bean -> {
                    sm.setTaskType(bean.getKey());
                    sm.setTaskTypeValue(bean.getValue());
                    bi.pickTask.setText(sm.getTaskTypeValue());
                });


                BaseQuickAdapter<PlanInfoBean.PlanLine.TargetSchool, BaseDataBindingHolder> schoolAdapter =
                        new BaseQuickAdapter<PlanInfoBean.PlanLine.TargetSchool,
                                BaseDataBindingHolder>(R.layout.item_plan_info_school,
                                sm.getZsxtPropgtPlanTargetList()) {
                            @Override
                            protected void convert(@NotNull BaseDataBindingHolder vh,
                                                   PlanInfoBean.PlanLine.TargetSchool s) {
                                ItemPlanInfoSchoolBinding bind = (ItemPlanInfoSchoolBinding) vh.getDataBinding();
                                if (bind == null) {
                                    return;
                                }
                                bind.tvSchoolTitle.setText("目标学校 - " + (vh.getAdapterPosition() + 1));
                                if (editAble) {
                                    if (vh.getAdapterPosition() == 0) {
                                        bind.ivSchoolTools.setBackgroundResource(R.mipmap.ic_plan_school_add);
                                    } else {
                                        bind.ivSchoolTools.setBackgroundResource(R.mipmap.ic_plan_school_mins);
                                    }
                                    bind.llSchoolAdd.setVisibility(View.VISIBLE);
                                    bind.llSchoolAdd.setOnClickListener(v -> {
                                        if (vh.getAdapterPosition() == 0) {
                                            PlanInfoBean.PlanLine.TargetSchool b = new PlanInfoBean.PlanLine.TargetSchool();
                                            sm.getZsxtPropgtPlanTargetList().add(b);
                                        } else {
                                            sm.getZsxtPropgtPlanTargetList().remove(vh.getAdapterPosition());
                                        }

                                        lineAdapter.notifyItemChanged(holder.getAdapterPosition());
                                    });


                                } else {
                                    bind.llSchoolAdd.setVisibility(View.GONE);
                                }


                                //学校名称
                                bind.pickSchoolName.setText(s.getSchoolName());
                                //地址
                                bind.stSchoolAddress.setText(s.getAddress());
                                //联系人
                                bind.stPersonName.setText(s.getContact());
                                //职务
                                bind.stPersonDuty.setText(s.getContactDuty());
                                //电话
                                bind.stPersonTel.setText(s.getContactPhone());
                                //是否制作喜报
                                bind.pickGoodNews.setText(s.getIsMadeValue());
                                //礼品需求
                                bind.stGift.setText(String.valueOf(s.getGiftNum()));
                                //预计发放资料
                                bind.stFile.setText(String.valueOf(s.getFileNum()));
                                //宣传方式
                                bind.pickPublicity.setText(s.getPropagationWayValue());

                                bind.pickGoodNews.setPickAble(editAble);
                                bind.pickPublicity.setPickAble(editAble);
                                bind.pickSchoolName.setPickAble(editAble);

                                bind.pickSchoolName.setTextClickListener(v -> {
                                    linePosition = holder.getAdapterPosition();
                                    schoolPosition = vh.getAdapterPosition();

                                    ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_ADD_SEARCH)
                                            .navigation(PlanInfoActivity.this, REQUEST_CODE_PLAN_INFO);
                                });

                                bind.pickGoodNews.setData(newsListBeans, "value", bean -> {
                                    s.setIsMade(bean.getKey());
                                    s.setIsMadeValue(bean.getValue());
                                    bind.pickGoodNews.setText(s.getIsMadeValue());
                                });
                                bind.pickPublicity.setData(wayListBeans, "value", bean -> {
                                    s.setPropagationWay(ParseUtils.parseInt(bean.getKey()));
                                    s.setPropagationWayValue(bean.getValue());
                                    bind.pickPublicity.setText(s.getPropagationWayValue());
                                });
                            }
                        };

                bi.rvSchool.setLayoutManager(new LinearLayoutManager(context));
                bi.rvSchool.setAdapter(schoolAdapter);

                //保存
                mpAdapter.put(holder.getAdapterPosition(), schoolAdapter);
            }
        };

        binding.rvLine.setLayoutManager(new LinearLayoutManager(context));
        binding.rvLine.setAdapter(lineAdapter);


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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setAble() {

        if (status == 0 || status == 3) {
            if (editAble) {
                binding.tvRest.setText(getString(R.string.save_draft));
                binding.tvSubmit.setText(getString(R.string.submit_for_review));
                binding.llBottom.setVisibility(View.VISIBLE);
                binding.tvEdit.setVisibility(View.GONE);
                binding.tvRest.setVisibility(View.VISIBLE);
                binding.tvSubmit.setVisibility(View.VISIBLE);
            } else {
                if (PermissionCommon.hasSchoolUpdate() && showEdit) {
                    binding.llBottom.setVisibility(View.VISIBLE);
                    binding.tvEdit.setVisibility(View.VISIBLE);
                    binding.tvRest.setVisibility(View.GONE);
                    binding.tvSubmit.setVisibility(View.GONE);
                } else {
                    binding.llBottom.setVisibility(View.GONE);
                }
            }

        } else if (status == 1) {
            if (PermissionCommon.hasPlanAduit()) {
                binding.llBottom.setVisibility(View.VISIBLE);
                binding.tvRest.setText(getString(R.string.refuse));
                binding.tvSubmit.setText(getString(R.string.pass));
                binding.tvRest.setVisibility(View.VISIBLE);
                binding.tvSubmit.setVisibility(View.VISIBLE);
                binding.tvEdit.setVisibility(View.GONE);
            } else {
                binding.llBottom.setVisibility(View.GONE);
            }
        } else if (status == 2) {
            binding.llBottom.setVisibility(View.GONE);
        }


        //经费类别
        binding.pickFundType.setPickAble(editAble);
        //往返公共交通费
        binding.stPublicGoBack.setEditAble(editAble);
        //区间公共交通费
        binding.stSectionPublicGoBack.setEditAble(editAble);
        //汽车租赁费
        binding.stLease.setEditAble(editAble);
        //过路费
        binding.stRoadToll.setEditAble(editAble);
        //燃油费
        binding.stFuelCost.setEditAble(editAble);

        //日房价
        binding.etRoomPrice.setEnabled(editAble);
        //间
        binding.etRoomUnit.setEnabled(editAble);
        //天
        binding.etRoomDay.setEnabled(editAble);

        //生活补助
        binding.etSubsidyPrice.setEnabled(editAble);
        //天
        binding.etSubsidyDay.setEnabled(editAble);
        //人
        binding.etSubsidyPerson.setEnabled(editAble);

        //其他
        binding.stOther.setEditAble(editAble);
        //说明
        binding.etOther.setEnabled(editAble);
        //总共
        binding.stTotal.setEditAble(editAble);
    }

    @Override
    protected void addListener() {
        lineAdapter.addChildClickViewIds(R.id.ll_title, R.id.tv_start_time, R.id.tv_end_time);
        lineAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PlanInfoBean.PlanLine sm = lineAdapter.getItem(position);
            if (view.getId() == R.id.ll_title) {
                sm.setSelect(!sm.isSelect());
                lineAdapter.notifyItemChanged(position);
            }
            if (!editAble) {
                return;
            }

            if (view.getId() == R.id.tv_start_time) {
                TextView tvStartTime = (TextView) lineAdapter.getViewByPosition(position, R.id.tv_start_time);
                if (tvStartTime != null) {
                    DatePickerUtils.showYYMMDDDialog(context, time -> {
                        tvStartTime.setText(time.split(" ")[0]);
                        sm.setBeginTime(time);
                    });
                }
            } else if (view.getId() == R.id.tv_end_time) {
                TextView tvStartTime = (TextView) lineAdapter.getViewByPosition(position, R.id.tv_start_time);
                if (tvStartTime != null) {
                    if (TextUtils.isEmpty(getTv(tvStartTime))) {
                        showToast("请选择开始时间");
                        return;
                    }
                    TextView tvEndTime = (TextView) lineAdapter.getViewByPosition(position, R.id.tv_end_time);
                    if (tvEndTime != null) {
                        DatePickerUtils.showYYMMDDDialog(context, time -> {
                            tvEndTime.setText(time.split(" ")[0]);
                            sm.setEndTime(time);
                        }, getTv(tvStartTime));
                    }
                }
            }

        });

        binding.tvStartTime.setOnClickListener(v -> {
            if (!editAble) {
                return;
            }
            DatePickerUtils.showYYMMDDDialog(context, binding.tvStartTime);
        });
        binding.tvEndTime.setOnClickListener(v -> {
            if (!editAble) {
                return;
            }

            if (TextUtils.isEmpty(getTv(binding.tvStartTime))) {
                showToast("请选择开始时间");
                return;
            }

            DatePickerUtils.showYYMMDDDialog(context, binding.tvEndTime, getTv(binding.tvStartTime));
        });

        binding.tvRest.setOnClickListener(v -> {
            if ("拒绝".equals(getTv(binding.tvRest))) {
                aduitPlan(3);
            } else {
                if (planInfoBean != null) {
                    planInfoBean.setStatus("0");
                    editPlan();
                    presenter.updatePlan(planInfoBean);
                }

            }
        });
        binding.tvSubmit.setOnClickListener(v -> {
            if ("通过".equals(getTv(binding.tvSubmit))) {
                aduitPlan(2);
            } else {
                if (planInfoBean != null) {
                    planInfoBean.setStatus("1");
                    editPlan();
                    presenter.updatePlan(planInfoBean);
                }
            }
        });
        binding.tvEdit.setOnClickListener(v -> {
            editAble = true;
            setAble();
        });

        setAble();
    }

    private void editPlan() {
        if (planInfoBean == null) {
            return;
        }
        for (PlanInfoBean.PlanLine line : lineAdapter.getData()) {
            int index = lineAdapter.getData().indexOf(line);
            EditText tv_start = (EditText) lineAdapter.getViewByPosition(index, R.id.tv_start);
            if (tv_start != null) {
                line.setLineStart(getTv(tv_start));
            }
            EditText tv_end = (EditText) lineAdapter.getViewByPosition(index, R.id.tv_end);
            if (tv_end != null) {
                line.setLineEnd(getTv(tv_end));
            }
            TextView tv_start_time = (TextView) lineAdapter.getViewByPosition(index, R.id.tv_start_time);
            if (tv_end != null) {
                line.setBeginTime(getTv(tv_start_time));
            }
            TextView tv_end_time = (TextView) lineAdapter.getViewByPosition(index, R.id.tv_end_time);
            if (tv_end != null) {
                line.setEndTime(getTv(tv_end_time));
            }

            BaseQuickAdapter<PlanInfoBean.PlanLine.TargetSchool, BaseDataBindingHolder> schoolAdapter = mpAdapter.get(index);
            for (PlanInfoBean.PlanLine.TargetSchool sl : line.getZsxtPropgtPlanTargetList()) {
                int ps = line.getZsxtPropgtPlanTargetList().indexOf(sl);
                if (schoolAdapter != null) {
                    SimEditText st_gift = (SimEditText) schoolAdapter.getViewByPosition(ps, R.id.st_gift);
                    if (st_gift != null) {
                        sl.setGiftNum(ParseUtils.parseInt(st_gift.getText()));
                    }
                    SimEditText st_file = (SimEditText) schoolAdapter.getViewByPosition(ps, R.id.st_file);
                    if (st_file != null) {
                        sl.setFileNum(ParseUtils.parseInt(st_file.getText()));
                    }
                }
            }
        }
        if (feeType!=null){
            planInfoBean.setFeeType(ParseUtils.parseInt(feeType.getKey()));
            planInfoBean.setFeeTypeValue(feeType.getValue());
        }

        //往返公共交通费
        planInfoBean.setRoundTripFee(ParseUtils.parseDouble(binding.stPublicGoBack.getText()));
        //区间公共交通费
        planInfoBean.setIntervalTripFee(ParseUtils.parseDouble(binding.stSectionPublicGoBack.getText()));
        //汽车租赁费
        planInfoBean.setCarRentFee(ParseUtils.parseDouble(binding.stLease.getText()));
        //过路费
        planInfoBean.setTollFee(ParseUtils.parseDouble(binding.stRoadToll.getText()));
        //燃油费
        planInfoBean.setFuelFee(ParseUtils.parseDouble(binding.stFuelCost.getText()));
        //日房价
        planInfoBean.setRoomFee(ParseUtils.parseDouble(getTv(binding.etRoomPrice)));
        //间
        planInfoBean.setRoomNum(ParseUtils.parseInt(getTv(binding.etRoomUnit)));
        //天
        planInfoBean.setRoomDays(ParseUtils.parseInt(getTv(binding.etRoomDay)));

        //生活补助
        planInfoBean.setSubsidyFee(ParseUtils.parseDouble(getTv(binding.etSubsidyPrice)));
        //天
        planInfoBean.setSubsidyDays(ParseUtils.parseInt(getTv(binding.etSubsidyDay)));
        //人
        planInfoBean.setSubsidyPersons(ParseUtils.parseInt(getTv(binding.etSubsidyPerson)));

        //其他
        planInfoBean.setOtherFee(ParseUtils.parseDouble(binding.stOther.getText()));

        //说明
        planInfoBean.setOtherFeePurpose(getTv(binding.etOther));
    }

    /**
     * 审核
     *
     * @param status status
     */
    private void aduitPlan(int status) {
        HashMap<String, Object> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(planId);
        map.put("ids", list);
        map.put("status", status);
        presenter.aduitPlan(map);
    }

    @Override
    public void onExamineList(List<SimpleBean> o) {

    }

    @Override
    public void onTaskList(List<SimpleBean> o) {
        taskBeans = o;
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
        wayListBeans = o;
    }

    @Override
    public void onMadeList(List<SimpleBean> o) {
        newsListBeans = o;
    }

    @Override
    public void onPlanStaffList(List<StaffBean> o) {
        staffBeans = o;
    }

    @Override
    public void onAddPlan(String o) {

    }

    @Override
    public void onPlanInfo(PlanInfoBean o) {
        this.planInfoBean = o;
        if (o != null) {

            //开始时间
            binding.tvStartTime.setText(o.getBeginTime());
            //结束时间
            binding.tvEndTime.setText(o.getEndTime());
            //线路
            lineAdapter.setNewInstance(o.getZsxtPropgtPlanLineList());
            //经费类别
            binding.pickFundType.setText(o.getFeeTypeValue());

            feeType = new SimpleStringBean();
            feeType.setKey(String.valueOf(o.getFeeType()));
            feeType.setValue(o.getFeeTypeValue());

            //往返公共交通费
            binding.stPublicGoBack.setText(StrUtils.valueOf(o.getRoundTripFee()));
            //区间公共交通费
            binding.stSectionPublicGoBack.setText(StrUtils.valueOf(o.getIntervalTripFee()));
            //汽车租赁费
            binding.stLease.setText(StrUtils.valueOf(o.getCarRentFee()));
            //过路费
            binding.stRoadToll.setText(StrUtils.valueOf(o.getTollFee()));
            //燃油费
            binding.stFuelCost.setText(StrUtils.valueOf(o.getFuelFee()));

            //日房价
            binding.etRoomPrice.setText(StrUtils.valueOf(o.getRoomFee()));
            //间
            binding.etRoomUnit.setText(String.valueOf(o.getRoomNum()));
            //天
            binding.etRoomDay.setText(String.valueOf(o.getRoomDays()));
            //总共
            binding.tvRoomTotal.setText(StrUtils.valueOf(o.getTotalRoomFee()));

            //生活补助
            binding.etSubsidyPrice.setText(StrUtils.valueOf(o.getSubsidyFee()));
            //天
            binding.etSubsidyDay.setText(String.valueOf(o.getSubsidyDays()));
            //人
            binding.etSubsidyPerson.setText(String.valueOf(o.getSubsidyPersons()));
            //总共
            binding.tvSubsidyTotal.setText(StrUtils.valueOf(o.getTotalSubsidyFee()));

            //其他
            binding.stOther.setText(StrUtils.valueOf(o.getOtherFee()));
            //说明
            binding.etOther.setText(o.getOtherFeePurpose());
            //总共
            binding.stTotal.setText(StrUtils.valueOf(o.getTotalCostMoney()));
        }
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


        BigDecimal total = BigDecimal.valueOf(wf).add(BigDecimal.valueOf(qj)).add(BigDecimal.valueOf(qc)).add(BigDecimal.valueOf(gl)).add(BigDecimal.valueOf(ry))
                .add(BigDecimal.valueOf(qt)).add(zs).add(bz);

        binding.stTotal.setText(total.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }

    @Override
    public void onAduitPlan(String o) {
        showToast(getString(R.string.succ_review));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onUpdatePlan(String o) {
        showToast(getString(R.string.succ_option));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PLAN_INFO && resultCode == RESULT_OK) {
            if (data != null) {
                SchoolInfoBean school = (SchoolInfoBean) data.getSerializableExtra("school");
                if (school != null) {
                    PlanInfoBean.PlanLine line = lineAdapter.getItem(linePosition);
                    if (line.getZsxtPropgtPlanTargetList() != null && line.getZsxtPropgtPlanTargetList().size() > 0) {

                        PlanInfoBean.PlanLine.TargetSchool sl = line.getZsxtPropgtPlanTargetList().get(schoolPosition);
                        sl.setTargetSchoolId(school.getId());
                        sl.setSchoolName(school.getSchoolName());
                        sl.setAddress(school.getProvince() + " " + school.getCity() + " " + school.getRegion() + " " + school.getAddress());
                        sl.setContact(school.getContact());
                        sl.setContactDuty(school.getContactDuty());
                        sl.setContactPhone(school.getContactPhone());
                        lineAdapter.notifyItemChanged(linePosition);
                    }
                }
            }
        }
    }
}
