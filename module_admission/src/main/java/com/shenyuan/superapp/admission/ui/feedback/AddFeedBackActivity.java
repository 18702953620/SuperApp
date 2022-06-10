package com.shenyuan.superapp.admission.ui.feedback;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcAddFeedbackBinding;
import com.shenyuan.superapp.admission.api.presenter.FeedBackPresenter;
import com.shenyuan.superapp.admission.api.view.FeedBackView;
import com.shenyuan.superapp.admission.bean.FeedBackInfoBean;
import com.shenyuan.superapp.admission.bean.FeedBackListBean;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.DateUtils;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.widget.datepicker.DatePickerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/17 11:04
 * desc 添加反馈
 */
@Route(path = ARouterPath.Admission.ADMISSION_FEEDBACK_ADD)
public class AddFeedBackActivity extends BaseActivity<AcAddFeedbackBinding, FeedBackPresenter> implements FeedBackView {

    public static final int REQUEST_CODE_ADD_FEED = 100;
    private String staffIds;
    /**
     * 特色活动意向 (0-没有意向，1-有意向)
     */
    private int activitiesIntention = -1;
    /**
     * 喜报是否送达 (0-未送达，1-已送达)
     */
    private int isGleeDelivered = -1;
    /**
     * 挂牌意愿 (0-无意愿，1-有意愿)
     */
    private int listedIntention = -1;
    /**
     * 来校参观意向 (0-没有意向，1-有意向)
     */
    private int visitIntention = -1;
    /**
     * 目标学校ID
     */
    private int targetSchoolId = -1;

    @Autowired
    public int feedId;
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

    private FeedBackInfoBean backInfoBean;

    @Override
    protected FeedBackPresenter createPresenter() {
        return new FeedBackPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_add_feedback;
    }

    @Override
    protected void initView() {
        if (feedId != 0) {
            presenter.getFeedBackById(feedId);
        } else {
            //宣传员列表
            presenter.getPlanStaffList();
        }

        //喜报是否送达
        presenter.getGiftList();
        //特色活动意向
        presenter.getIntentList();
        //挂牌意愿
        presenter.getListingList();
        //来校参观意向
        presenter.getVisitList();

        setAble();
    }

    @Override
    protected void addListener() {
        binding.pickSchool.setTextClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_ADD_SEARCH)
                .navigation(this, REQUEST_CODE_ADD_FEED));
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
                binding.stDays.setText("共" + DateUtils.getBetweenDays(start, end) + "天");

            }, getTv(binding.tvStartTime));
        });

        binding.tvSubmit.setOnClickListener(v -> saveOrUpdate());

        binding.tvRest.setOnClickListener(v -> reset());

        binding.tvEdit.setOnClickListener(v -> {
            editAble = true;
            setAble();
        });

    }

    private void reset() {
        binding.stName.clear();

        binding.tvStartTime.setText("");
        binding.tvEndTime.setText("");
        binding.stDays.clear();

        targetSchoolId = -1;
        staffIds = null;

        binding.pickSchool.clear();
        binding.stPersonName.clear();
        binding.stPersonDuty.clear();
        binding.stPersonTel.clear();
        binding.pickPropaganidst.clear();

        binding.stClassNumber.clear();
        binding.stStudentNumber.clear();
        binding.stOnlineNumber.clear();
        binding.stCsNumber.clear();
        binding.stHqNumber.clear();

        activitiesIntention = -1;
        binding.pickIntent.clear();
        isGleeDelivered = -1;
        binding.pickGoodNews.clear();
        listedIntention = -1;
        binding.pickListing.clear();
        visitIntention = -1;
        binding.pickVisit.clear();

        binding.stJzNumber.clear();
        binding.stHddyNumber.clear();
        binding.stGiftNumber.clear();
        binding.stStudentTotalNumber.clear();
        binding.etOther.setText("");

        binding.pickSchool.clear();
        binding.pickPropaganidst.clear();
    }

    private void saveOrUpdate() {
        if (TextUtils.isEmpty(binding.stName.getText())) {
            showToast(getString(R.string.please_input_feed_name));
            return;
        }
        if (targetSchoolId == -1) {
            showToast(getString(R.string.please_select_school));
            return;
        }
        if (TextUtils.isEmpty(staffIds)) {
            showToast(getString(R.string.please_select_staff));
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
        if (TextUtils.isEmpty(binding.stClassNumber.getText())) {
            showToast(getString(R.string.please_input_class_number));
            return;
        }
        if (TextUtils.isEmpty(binding.stStudentNumber.getText())) {
            showToast(getString(R.string.please_input_student_number));
            return;
        }


        HashMap<String, Object> map = new HashMap<>();
        //目标学校ID
        map.put("targetSchoolId", targetSchoolId);
        //开始时间
        map.put("startTime", getTv(binding.tvStartTime));
        //结束时间
        map.put("endTime", getTv(binding.tvEndTime));
        //毕业班级数
        map.put("classNum", binding.stClassNumber.getText());
        //毕业班级数
        map.put("studentNum", binding.stStudentNumber.getText());
        //二本上线人数
        map.put("onlineStudentNum", binding.stOnlineNumber.getText());
        //城市学院上一季度录取人数
        map.put("csEnrollmentNum", binding.stCsNumber.getText());
        //华清学院上一季度录取人数
        map.put("hqEnrollmentNum", binding.stHqNumber.getText());
        //特色活动意向
        if (activitiesIntention != -1) {
            map.put("activitiesIntention", activitiesIntention);
        }
        //喜报是否送达
        if (isGleeDelivered != -1) {
            map.put("isGleeDelivered", isGleeDelivered);
        }
        //挂牌意愿
        if (listedIntention != -1) {
            map.put("listedIntention", listedIntention);
        }
        //来校参观意向
        if (visitIntention != -1) {
            map.put("visitIntention", visitIntention);
        }
        //发送简章数
        map.put("distributeRegulationNum", binding.stJzNumber.getText());
        //发放特色活动单页份数
        map.put("distributeActivityNum", binding.stHddyNumber.getText());
        //发放礼品数
        map.put("distributeGiftNum", binding.stGiftNumber.getText());
        //发放喜报数
        map.put("distributeGleeNum", binding.stGoodnewsNumber.getText());
        //学生信息人数
        map.put("collectStudentNum", binding.stStudentTotalNumber.getText());
        //具体措施
        map.put("specificMeasure", binding.etOther.getText().toString());
        //宣传员IDS
        map.put("staffIds", staffIds);

        HashMap<String, Object> fb = new HashMap<>();
        fb.put("fbName", binding.stName.getText());
        List<HashMap<String, Object>> list = new ArrayList<>();
        list.add(map);
        fb.put("zsxtPropgtFeedbackSchoolDtoList", list);
        //开始时间
        fb.put("startTime", getTv(binding.tvStartTime));
        //结束时间
        fb.put("endTime", getTv(binding.tvEndTime));

        if (feedId != 0) {
            fb.put("id", feedId);
            presenter.updateFeedBack(fb);
        } else {
            presenter.addFeedBack(fb);
        }
    }

    @Override
    public void onIntentList(List<SimpleBean> o) {
        binding.pickIntent.setData(o, "value", bean -> activitiesIntention = bean.getKey());
    }

    @Override
    public void onGiftList(List<SimpleBean> o) {
        binding.pickGoodNews.setData(o, "value", bean -> isGleeDelivered = bean.getKey());
    }

    @Override
    public void onListingList(List<SimpleBean> o) {
        binding.pickListing.setData(o, "value", bean -> listedIntention = bean.getKey());
    }

    @Override
    public void onVisitList(List<SimpleBean> o) {
        binding.pickVisit.setData(o, "value", bean -> visitIntention = bean.getKey());
    }

    @Override
    public void onPlanStaffList(List<StaffBean> o) {
        List<StaffBean> list = new ArrayList<>();
        if (backInfoBean != null) {
            if (backInfoBean.getZsxtPropgtFeedbackSchoolViewVo() != null && backInfoBean.getZsxtPropgtFeedbackSchoolViewVo().size() > 0) {
                if (backInfoBean.getZsxtPropgtFeedbackSchoolViewVo().get(0) != null
                        && backInfoBean.getZsxtPropgtFeedbackSchoolViewVo().get(0).getPropagandistVoList() != null) {
                    list.addAll(backInfoBean.getZsxtPropgtFeedbackSchoolViewVo().get(0).getPropagandistVoList());
                }
            }
        }
        binding.pickPropaganidst.setPersonData(o, list, staffBeans -> {
            StringBuilder ids = new StringBuilder();
            if (staffBeans != null && staffBeans.size() > 0) {
                for (StaffBean bean : staffBeans) {
                    if (ids.length() > 0) {
                        ids.append(",");
                    }
                    ids.append(bean.getId());
                }
            }
            staffIds = ids.toString();
        });
    }

    @Override
    public void onAddFeedBack(String o) {
        showToast(getString(R.string.succ_add));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFeedBackList(List<FeedBackListBean> o) {

    }

    @Override
    public void onFeedBackInfo(FeedBackInfoBean o) {
        backInfoBean = o;
        if (o != null) {
            binding.stName.setText(o.getFbName());

            //宣传员列表
            presenter.getPlanStaffList();

            binding.tvStartTime.setText(o.getStartTime());
            binding.tvEndTime.setText(o.getEndTime());

            String start = getTv(binding.tvStartTime);
            String end = getTv(binding.tvEndTime);

            binding.stDays.setText("共" + DateUtils.getBetweenDays(start, end) + "天");
            if (o.getZsxtPropgtFeedbackSchoolViewVo() != null && o.getZsxtPropgtFeedbackSchoolViewVo().size() > 0) {
                FeedBackInfoBean.SchoolBean sl = o.getZsxtPropgtFeedbackSchoolViewVo().get(0);

                targetSchoolId = sl.getTargetSchoolId();

                staffIds = sl.getStaffIds();

                binding.pickSchool.setText(sl.getSchoolName());
                binding.stPersonName.setText(sl.getContact());
                binding.stPersonDuty.setText(sl.getContactDuty());
                binding.stPersonTel.setText(sl.getContactPhone());
                binding.pickPropaganidst.setText(sl.getStaffNames());


                binding.stClassNumber.setText(String.valueOf(sl.getClassNum()));
                binding.stStudentNumber.setText(String.valueOf(sl.getStudentNum()));
                binding.stOnlineNumber.setText(String.valueOf(sl.getOnlineStudentNum()));
                binding.stCsNumber.setText(String.valueOf(sl.getCsEnrollmentNum()));
                binding.stHqNumber.setText(String.valueOf(sl.getHqEnrollmentNum()));

                activitiesIntention = sl.getActivitiesIntention();
                binding.pickIntent.setText(sl.getActivitiesIntentionValue());
                isGleeDelivered = sl.getIsGleeDelivered();
                binding.pickGoodNews.setText(sl.getIsGleeDeliveredValue());
                listedIntention = sl.getListedIntention();
                binding.pickListing.setText(sl.getListedIntentionValue());
                visitIntention = sl.getVisitIntention();
                binding.pickVisit.setText(sl.getVisitIntentionValue());

                binding.stJzNumber.setText(String.valueOf(sl.getDistributeRegulationNum()));
                binding.stHddyNumber.setText(String.valueOf(sl.getDistributeActivityNum()));
                binding.stGiftNumber.setText(String.valueOf(sl.getDistributeGiftNum()));
                binding.stStudentTotalNumber.setText(String.valueOf(sl.getCollectStudentNum()));
                binding.stGoodnewsNumber.setText(String.valueOf(sl.getDistributeGleeNum()));

                binding.etOther.setText(StrUtils.isEmpty(sl.getSpecificMeasure()));
            }
        }
    }

    @Override
    public void onUpdateFeedBack(String o) {
        showToast(getString(R.string.succ_edit));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onDeleteFeedBack(String o) {

    }

    @Override
    public void onTopFeedBack(String o) {

    }

    private void setAble() {
        if (editAble) {
            binding.title.setTitle(getString(R.string.title_feed_edit));
            binding.llTools.setVisibility(View.VISIBLE);
            binding.tvEdit.setVisibility(View.GONE);
            binding.tvRest.setVisibility(View.VISIBLE);
            binding.tvSubmit.setVisibility(View.VISIBLE);
        } else {
            binding.title.setTitle(getString(R.string.title_feed_info));

            if (PermissionCommon.hasFeedUpdate() && showEdit) {
                binding.llTools.setVisibility(View.VISIBLE);
                binding.tvEdit.setVisibility(View.VISIBLE);
                binding.tvRest.setVisibility(View.GONE);
                binding.tvSubmit.setVisibility(View.GONE);
            } else {
                binding.llTools.setVisibility(View.GONE);
            }
        }


        binding.stName.setEditAble(editAble);
        binding.stDays.setEditAble(editAble);
        binding.pickSchool.setPickAble(editAble);
        binding.stPersonName.setEditAble(editAble);
        binding.stPersonDuty.setEditAble(editAble);
        binding.stPersonTel.setEditAble(editAble);
        binding.pickPropaganidst.setPickAble(editAble);

        if (!editAble) {
            binding.tvStartTime.setEnabled(false);
            binding.tvEndTime.setEnabled(false);
        }


        binding.stClassNumber.setEditAble(editAble);
        binding.stStudentNumber.setEditAble(editAble);
        binding.stOnlineNumber.setEditAble(editAble);
        binding.stCsNumber.setEditAble(editAble);
        binding.stHqNumber.setEditAble(editAble);

        binding.pickIntent.setPickAble(editAble);
        binding.pickGoodNews.setPickAble(editAble);
        binding.pickListing.setPickAble(editAble);
        binding.pickVisit.setPickAble(editAble);

        binding.stJzNumber.setEditAble(editAble);
        binding.stHddyNumber.setEditAble(editAble);
        binding.stGiftNumber.setEditAble(editAble);
        binding.stStudentTotalNumber.setEditAble(editAble);
        binding.stGoodnewsNumber.setEditAble(editAble);

        binding.etOther.setEnabled(editAble);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_FEED && resultCode == RESULT_OK) {
            if (data != null) {
                SchoolInfoBean school = (SchoolInfoBean) data.getSerializableExtra("school");
                if (school != null) {
                    targetSchoolId = school.getId();
                    binding.pickSchool.setText(school.getSchoolName());
                    binding.stPersonName.setText(school.getContact());
                    binding.stPersonDuty.setText(school.getContactDuty());
                    binding.stPersonTel.setText(school.getContactPhone());
                }
            }
        }
    }
}
