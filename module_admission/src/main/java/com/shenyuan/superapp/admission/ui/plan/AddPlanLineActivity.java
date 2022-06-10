package com.shenyuan.superapp.admission.ui.plan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcAddPlanLineBinding;
import com.shenyuan.admission.databinding.ItemLineSchoolBinding;
import com.shenyuan.superapp.admission.api.presenter.PlanPresenter;
import com.shenyuan.superapp.admission.api.view.PlanView;
import com.shenyuan.superapp.admission.bean.PlanInfoBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.SimpleStringBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.DateUtils;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.widget.PickerTextView;
import com.shenyuan.superapp.common.widget.SimEditText;
import com.shenyuan.superapp.common.widget.datepicker.DatePickerUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/13 13:58
 * desc 添加路线
 */

@Route(path = ARouterPath.Admission.ADMISSION_PLAN_ADD_LINE)
public class AddPlanLineActivity extends BaseActivity<AcAddPlanLineBinding, PlanPresenter> implements PlanView {
    private BaseQuickAdapter<HashMap<String, Object>, BaseDataBindingHolder> schoolAdapter;
    public static final int REQUEST_CODE_ADD_LINE = 100;

    private List<SimpleStringBean> wayListBeans;
    private List<SimpleBean> newsListBeans;

    /**
     * 出差任务类型
     */
    private int taskType = -1;
    /**
     * 宣传员
     */
    private String staffIds;
    private String staffNames;

    private int currentSchoolPosition;


    @Override
    protected PlanPresenter createPresenter() {
        return new PlanPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_add_plan_line;
    }

    @Override
    protected void initView() {
        //出差任务
        presenter.getPlanTaskList();
        //是否制作喜报
        presenter.getMadeList();
        //宣传方式
        presenter.getPropagationWayDictList();
        //宣传员
        presenter.getPlanStaffList();

        schoolAdapter = new BaseQuickAdapter<HashMap<String, Object>, BaseDataBindingHolder>(R.layout.item_line_school) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder holder, HashMap<String, Object> sm) {
                ItemLineSchoolBinding bi = (ItemLineSchoolBinding) holder.getDataBinding();
                if (bi == null) {
                    return;
                }
                bi.pickGoodNews.setData(newsListBeans, "value", bean -> sm.put("isMade", String.valueOf(bean.getKey())));
                bi.pickPublicity.setData(wayListBeans, "value", bean -> sm.put("propagationWay", bean.getKey()));
                bi.pickSchool.setTextClickListener(v -> {
                    currentSchoolPosition = holder.getAdapterPosition();
                    ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_ADD_SEARCH)
                            .navigation(AddPlanLineActivity.this, REQUEST_CODE_ADD_LINE);
                });

                if (!sm.containsKey("targetSchoolId")){
                    bi.pickSchool.setText("");
                    bi.stSchoolAddress.setText("");
                    bi.stPersonDuty.setText("");
                    bi.stPersonName.setText("");
                    bi.stPersonTel.setText("");
                }
            }
        };
        binding.rvSchool.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchool.setAdapter(schoolAdapter);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void addListener() {
        schoolAdapter.addChildClickViewIds(R.id.ll_mins);
        schoolAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_mins) {
                schoolAdapter.removeAt(position);
            }
        });
        binding.llAddSchool.setOnClickListener(v -> schoolAdapter.addData(new HashMap<>()));

        binding.tvStartTime.setOnClickListener(v -> DatePickerUtils.showYYMMDDDialog(context, binding.tvStartTime));
        binding.tvEndTime.setOnClickListener(v -> {
            if (TextUtils.isEmpty(getTv(binding.tvStartTime))) {
                showToast(getString(R.string.please_select_start_time));
                return;
            }

            DatePickerUtils.showYYMMDDDialog(context, time -> {
                binding.tvEndTime.setText(time.split(" ")[0]);
                String start = getTv(binding.tvStartTime);
                String end = getTv(binding.tvEndTime);
                binding.tvDay.setText(String.format("共%d天", DateUtils.getBetweenDays(start, end)));

            }, getTv(binding.tvStartTime));
        });
        binding.tvRest.setOnClickListener(v -> {
            binding.tvStart.getText().clear();
            binding.tvEnd.getText().clear();
            binding.tvStartTime.setText("");
            binding.tvEndTime.setText("");
            binding.tvDay.setText("");
            binding.pickTask.clear();

            binding.pickPropaganidst.clearStaff();

            staffIds = null;
            staffNames = null;
            taskType = -1;

            schoolAdapter.setNewInstance(null);
        });

        binding.tvSubmit.setOnClickListener(v -> addLine());
    }

    /**
     * 添加线路
     */
    private void addLine() {
        if (TextUtils.isEmpty(getTv(binding.tvStart))) {
            showToast(getString(R.string.please_input_start_address));
            return;
        }
        if (TextUtils.isEmpty(getTv(binding.tvEnd))) {
            showToast(getString(R.string.please_input_end_address));
            return;
        }
        if (TextUtils.isEmpty(getTv(binding.tvStartTime))) {
            showToast(getString(R.string.please_select_start_time));
            return;
        }
        if (TextUtils.isEmpty(getTv(binding.tvEndTime))) {
            showToast(getString(R.string.please_select_end_time));
            return;
        }
        if (TextUtils.isEmpty(staffIds)) {
            showToast(getString(R.string.please_select_staff));
            return;
        }
        if (taskType == -1) {
            showToast("请选择出差任务");
            return;
        }

        if (schoolAdapter.getData().size() == 0) {
            showToast("请添加目标学校");
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        //出发地
        map.put("lineStart", getTv(binding.tvStart));
        //抵达地
        map.put("lineEnd", getTv(binding.tvEnd));
        //开始时间
        map.put("beginTime", getTv(binding.tvStartTime));
        //结束时间
        map.put("endTime", getTv(binding.tvEndTime));
        //宣传员
        map.put("staffIds", staffIds);
        map.put("staffNames", staffNames);
        //出差任务
        map.put("taskType", taskType);

        for (HashMap<String, Object> mp : schoolAdapter.getData()) {
            int index = schoolAdapter.getData().indexOf(mp);
            if (!mp.containsKey("isMade")) {
                showToast("请选择第" + (index + 1) + "个目标学校是否制作喜报");
                return;
            }
            //	礼品需求数量
            SimEditText stGift = (SimEditText) schoolAdapter.getViewByPosition(index, R.id.st_gift);

            if (stGift != null) {
                if (TextUtils.isEmpty(stGift.getText())) {
                    showToast("请输入第" + (index + 1) + "个目标学校礼品需求数量");
                    return;
                }
                mp.put("giftNum", ParseUtils.parseInt(stGift.getText()));
            }

            //预计发放资料数量
            SimEditText stFile = (SimEditText) schoolAdapter.getViewByPosition(index, R.id.st_file);
            if (stFile != null) {
                if (TextUtils.isEmpty(stFile.getText())) {
                    showToast("请输入第" + (index + 1) + "个目标学校预计发放资料数量");
                    return;
                }
                mp.put("fileNum", ParseUtils.parseInt(stFile.getText()));
            }
            if (!mp.containsKey("propagationWay")) {
                showToast("请选择第" + (index + 1) + "个目标学校宣传方式");
                return;
            }
            //	预期宣传效果
            EditText etPublicity = (EditText) schoolAdapter.getViewByPosition(index, R.id.et_publicity);
            if (etPublicity != null) {
                mp.put("expectedEffect", getTv(etPublicity));
            }
            //	自主选购礼品需求
            EditText etGift = (EditText) schoolAdapter.getViewByPosition(index, R.id.et_gift);
            if (etGift != null) {
                mp.put("personalNeeds", getTv(etGift));
            }
            //	问题与建议
            EditText etProblem = (EditText) schoolAdapter.getViewByPosition(index, R.id.et_problem);
            if (etProblem != null) {
                mp.put("problemsSolutions", getTv(etProblem));
            }
        }
        //目标学校
        map.put("zsxtPropgtPlanTargetList", schoolAdapter.getData());

        Intent intent = new Intent();
        intent.putExtra("planLine", map);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onExamineList(List<SimpleBean> o) {

    }

    @Override
    public void onTaskList(List<SimpleBean> o) {
        binding.pickTask.setData(o, "value", bean -> taskType = bean.getKey());
    }

    @Override
    public void onPlanList(List<PlanListBean> o) {

    }

    @Override
    public void onCreateList(List<SimpleStringBean> o) {

    }

    @Override
    public void onFeeTypeList(List<SimpleStringBean> o) {

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
        binding.pickPropaganidst.setPersonData(o, staffBeans -> {
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
            staffIds = ids.toString();
            staffNames = names.toString();
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_LINE && resultCode == RESULT_OK) {
            if (data != null) {
                SchoolInfoBean school = (SchoolInfoBean) data.getSerializableExtra("school");
                if (school != null) {
                    HashMap<String, Object> mp = schoolAdapter.getItem(currentSchoolPosition);
                    if (mp != null) {
                        mp.put("targetSchoolId", String.valueOf(school.getId()));
                        mp.put("targetSchoolName", school.getSchoolName());
                    }

                    SimEditText st_school_address = (SimEditText) schoolAdapter.getViewByPosition(currentSchoolPosition, R.id.st_school_address);
                    SimEditText st_person_tel = (SimEditText) schoolAdapter.getViewByPosition(currentSchoolPosition, R.id.st_person_tel);
                    SimEditText st_person_name = (SimEditText) schoolAdapter.getViewByPosition(currentSchoolPosition, R.id.st_person_name);
                    SimEditText st_person_duty = (SimEditText) schoolAdapter.getViewByPosition(currentSchoolPosition, R.id.st_person_duty);
                    PickerTextView pick_school = (PickerTextView) schoolAdapter.getViewByPosition(currentSchoolPosition, R.id.pick_school);

                    if (st_school_address != null) {
                        st_school_address.setText(StrUtils.isEmpty(school.getProvince()) + " " + StrUtils.isEmpty(school.getCity()) +
                                " " + StrUtils.isEmpty(school.getRegion()) + " " + StrUtils.isEmpty(school.getAddress()));
                    }
                    if (st_person_name != null) {
                        st_person_name.setText(school.getContact());
                    }
                    if (st_person_duty != null) {
                        st_person_duty.setText(school.getContactDuty());
                    }
                    if (st_person_tel != null) {
                        st_person_tel.setText(school.getContactPhone());
                    }
                    if (pick_school != null) {
                        pick_school.setText(school.getSchoolName());
                    }
                }
            }
        }
    }
}
