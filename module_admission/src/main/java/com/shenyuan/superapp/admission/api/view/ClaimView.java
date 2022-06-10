package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.ClaimInfoBean;
import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/23 12:10
 * desc
 */
public interface ClaimView extends BaseView {
    void onAddClaim(String o);

    void onPlanList(List<PlanListBean> o);

    void onClaimList(List<ClaimListBean> o);

    void onClaimInfo(ClaimInfoBean o);

    void onAduitClaim(String o);

    void onDeleteClaim(String o);

    void onTopClaim(String o);

    void onUpdateClaim(String o);
}
