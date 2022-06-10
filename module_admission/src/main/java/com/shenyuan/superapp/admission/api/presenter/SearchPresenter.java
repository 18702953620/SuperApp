package com.shenyuan.superapp.admission.api.presenter;

import com.shenyuan.superapp.admission.api.view.SearchView;
import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.admission.bean.FeedBackListBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.base.api.BaseSubscriber;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/8 11:04
 * desc
 */
public class SearchPresenter extends BaseSchoolPresenter<SearchView> {
    public SearchPresenter(SearchView baseView) {
        super(baseView);
    }

    /**
     * 学校列表
     */
    public void getSchoolListLikeName(String name, int state) {
        addDisposable(schoolApiServer.getSchoolListLikeName(name, state), new BaseSubscriber<List<SchoolInfoBean>>(baseView) {
            @Override
            public void onSuccess(List<SchoolInfoBean> o) {
                baseView.onSearchSchoolList(o);
            }
        });
    }

    /**
     * 学校列表
     */
    public void getSchoolListLikeName(String name, int state, int page) {
        addDisposable(schoolApiServer.getSchoolListLikeName(name, state, 30, page), new BaseSubscriber<List<SchoolInfoBean>>(baseView) {
            @Override
            public void onSuccess(List<SchoolInfoBean> o) {
                baseView.onSearchSchoolList(o);
            }
        });
    }

    /**
     * 生源列表列表
     */
    public void getStudentListLikeName(String name) {
        addDisposable(schoolApiServer.getStudentListLikeName(name), new BaseSubscriber<List<StudentListBean>>(baseView) {
            @Override
            public void onSuccess(List<StudentListBean> o) {
                baseView.onSearchStudentList(o);
            }
        });
    }

    /**
     * 生源列表列表
     */
    public void getStudentListLikeNameByMy(String name) {
        addDisposable(schoolApiServer.getStudentListLikeNameByMy(name), new BaseSubscriber<List<StudentListBean>>(baseView) {
            @Override
            public void onSuccess(List<StudentListBean> o) {
                baseView.onSearchStudentList(o);
            }
        });
    }

    /**
     * 生源列表列表
     */
    public void getStudentListLikeNameByReturn(String name) {
        addDisposable(schoolApiServer.getStudentListLikeNameByReturn(name), new BaseSubscriber<List<StudentListBean>>(baseView) {
            @Override
            public void onSuccess(List<StudentListBean> o) {
                baseView.onSearchStudentList(o);
            }
        });
    }

    /**
     * 方案列表
     */
    public void getPlanListLikeName(String name) {
        addDisposable(schoolApiServer.getPlanListLikeName(name), new BaseSubscriber<List<PlanListBean>>(baseView) {
            @Override
            public void onSuccess(List<PlanListBean> o) {
                baseView.onSearchPlanList(o);
            }
        });
    }

    /**
     * 反馈列表
     */
    public void getFeedListLikeName(String name) {
        addDisposable(schoolApiServer.getFeedListLikeName(name), new BaseSubscriber<List<FeedBackListBean>>(baseView) {
            @Override
            public void onSuccess(List<FeedBackListBean> o) {
                baseView.onSearchFeedList(o);
            }
        });
    }

    /**
     * 报账列表
     */
    public void getClaimListLikeName(String name) {
        addDisposable(schoolApiServer.getClaimListLikeName(name), new BaseSubscriber<List<ClaimListBean>>(baseView) {
            @Override
            public void onSuccess(List<ClaimListBean> o) {
                baseView.onSearchClaimList(o);
            }
        });
    }

    /**
     * 搜索历史列表
     */
    public void getSchoolHistoryList() {
        addDisposable(schoolApiServer.getSchoolHistoryList(), new BaseSubscriber<List<String>>(baseView) {
            @Override
            public void onSuccess(List<String> o) {
                baseView.onHistoryList(o);
            }
        });
    }

    /**
     * 添加搜索历史
     */
    public void addSchoolHistory(String schoolName) {
        addDisposable(schoolApiServer.addSchoolHistory(schoolName), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                if (baseView != null) {
                    baseView.onAddHistory(o);
                }
            }
        });
    }

    /**
     * 删除搜索历史
     */
    public void deleteSchoolHistory() {
        addDisposable(schoolApiServer.deleteSchoolHistory(), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                if (baseView != null) {
                    baseView.onDeleteHistory(o);
                }
            }
        });
    }
}
