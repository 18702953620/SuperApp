package com.shenyuan.superapp.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.google.android.material.tabs.TabLayout;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.common.widget.CustItemDragListener;
import com.shenyuan.superapp.adapter.CustMoreAdapter;
import com.shenyuan.superapp.api.presenter.HomePresenter;
import com.shenyuan.superapp.api.view.HomeView;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimDialog;
import com.shenyuan.superapp.base.utils.DensityUtils;
import com.shenyuan.superapp.base.widget.recy.DividerDecoration;
import com.shenyuan.superapp.bean.BannerBean;
import com.shenyuan.superapp.bean.MenuBean;
import com.shenyuan.superapp.bean.NewsTypeBean;
import com.shenyuan.superapp.bean.ServiceBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.bean.NewsBean;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superapp.databinding.AcMoreBinding;
import com.shenyuan.superapp.databinding.ItemMoreBinding;
import com.shenyuan.superapp.databinding.ItemMoreMenuBinding;
import com.shenyuan.superapp.databinding.ItemMoreMenuLayoutBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/1/20 9:34
 * desc 更多
 */
@Route(path = ARouterPath.AppTeacher.APP_MORE)
public class MoreActivity extends BaseActivity<AcMoreBinding, HomePresenter> implements HomeView {
    private CustMoreAdapter custAdapter;
    private BaseQuickAdapter<ServiceBean, BaseDataBindingHolder> menuAdapter;
    /**
     * 编辑状态
     */
    private boolean showEdit;
    /**
     * 是否展开
     */
    private boolean drag;
    /**
     * 菜单列表的状态
     */
    private int menuState;

    private List<MenuBean> custList = new ArrayList<>();
    private boolean mShouldScroll;
    private int nextPosition;

    private boolean isEdit;

    @Override
    protected void setStatusBar() {
        setStatusBarColor(R.color.color_f5f5f5);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_more;
    }

    @Override
    protected void initView() {
        //获取所有服务
        presenter.getAllService();
        //我的服务
        presenter.getMyService();


        custAdapter = new CustMoreAdapter();
        binding.rvCustMenu.setLayoutManager(new GridLayoutManager(context, 4));
        binding.rvCustMenu.setAdapter(custAdapter);
        //多于1行才展示
        if (custAdapter.getData().size() > 4) {
            binding.llDrag.setVisibility(View.VISIBLE);
        } else {
            binding.llDrag.setVisibility(View.GONE);
        }

        // 拖拽监听
        custAdapter.getDraggableModule().setDragEnabled(false);
        custAdapter.getDraggableModule().setOnItemDragListener(new CustItemDragListener());


        menuAdapter = new BaseQuickAdapter<ServiceBean, BaseDataBindingHolder>(R.layout.item_more_menu_layout) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, ServiceBean menu) {
                ItemMoreMenuLayoutBinding menuBinding = (ItemMoreMenuLayoutBinding) baseDataBindingHolder.getDataBinding();
                if (menuBinding == null) {
                    return;
                }
                menuBinding.tvMoreName.setText(menu.getTypeName());

                BaseQuickAdapter<ServiceBean.SysListDTO, BaseDataBindingHolder> serviceAdapter
                        = new BaseQuickAdapter<ServiceBean.SysListDTO, BaseDataBindingHolder>(R.layout.item_more_menu, menu.getSysList()) {
                    @Override
                    protected void convert(@NotNull BaseDataBindingHolder holder, ServiceBean.SysListDTO sm) {

                        ItemMoreMenuBinding layoutBinding = (ItemMoreMenuBinding) holder.getDataBinding();
                        if (layoutBinding == null) {
                            return;
                        }
                        layoutBinding.tvMoreName.setText(sm.getSystemName());

                        BaseVBAdapter<MenuBean, BaseDataBindingHolder> beanAdapter
                                = new BaseVBAdapter<MenuBean, BaseDataBindingHolder>(R.layout.item_more, sm.getInfoList(), false) {
                            @Override
                            protected void convert(@NotNull BaseDataBindingHolder holder, MenuBean menuBean) {
                                ItemMoreBinding moreBinding = (ItemMoreBinding) holder.getDataBinding();
                                if (moreBinding == null) {
                                    return;
                                }
                                moreBinding.tvMenuName.setText(menuBean.getServiceName());

                                GlideUtils.load(context, menuBean.getIconUrl(), moreBinding.ivMenuImg, R.mipmap.ic_default_menu);
                                if (showEdit) {
                                    moreBinding.llAdd.setVisibility(View.VISIBLE);
                                } else {
                                    moreBinding.llAdd.setVisibility(View.GONE);
                                }
                            }
                        };
                        layoutBinding.rvMoreMenu.setHasFixedSize(true);
                        layoutBinding.rvMoreMenu.setLayoutManager(new GridLayoutManager(context, 4));
                        layoutBinding.rvMoreMenu.setAdapter(beanAdapter);

                        beanAdapter.setItemClickListener((bean, view, position) -> {
                            if (showEdit) {
                                if (contains(bean)) {
                                    showToast(getString(R.string.menu_exist));
                                } else {
                                    custAdapter.addData(bean);

                                    if (custAdapter.getData().size() > 4) {
                                        binding.llDrag.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.llDrag.setVisibility(View.GONE);
                                    }
                                }
                            } else {
                                ARouterPath.router(bean.getRouterUrl());
                            }
                        });
                    }
                };
                menuBinding.rvMoreMenu.setLayoutManager(new LinearLayoutManager(context));
                menuBinding.rvMoreMenu.setAdapter(serviceAdapter);
            }
        };
        binding.rvMenu.setLayoutManager(new LinearLayoutManager(context));
        binding.rvMenu.setAdapter(menuAdapter);
        binding.rvMenu.addItemDecoration(new DividerDecoration(getValuesColor(R.color.color_d8d8d8),
                1, DensityUtils.dp2px(context, 12), DensityUtils.dp2px(context, 12)));
    }

    /**
     * 是否包含
     *
     * @param mn mn
     * @return boolean
     */
    private boolean contains(MenuBean mn) {
        if (mn == null) {
            return false;
        }

        List<MenuBean> dtos = custAdapter.getData();
        if (dtos.size() == 0) {
            return false;
        }

        for (MenuBean dt : dtos) {
            if (mn.getId() == dt.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void addListener() {
        custAdapter.setItemClickListener((bean, view, position) -> {
            if (getString(R.string.edit).equals(bean.getServiceName())) {
                showEdit = !showEdit;
                resetUi();
            } else {
                if (showEdit) {
                    custAdapter.remove(bean);
                } else {
                    ARouterPath.router(bean.getRouterUrl());
                }
            }
        });

        binding.llDrag.setOnClickListener(v -> {
            drag = !drag;
            if (drag) {
                binding.ivDrag.setBackgroundResource(R.mipmap.ic_arrow_more_down);
                if (custAdapter.getData().size() > 4) {
                    custList = new ArrayList<>(custAdapter.getData());
                    custAdapter.setList(new ArrayList<>(custList.subList(0, 4)));
                } else {
                    custAdapter.setList(custList);
                }
            } else {
                binding.ivDrag.setBackgroundResource(R.mipmap.ic_arrow_more_up);
                custAdapter.setList(custList);
            }
        });

        binding.tvMoreCancel.setOnClickListener(v -> {
            showEdit = false;
            resetUi();
        });
        binding.tvMoreSubmit.setOnClickListener(v -> {
            new SimDialog.Builder(context).title(getString(R.string.save_content)).submitText(getString(R.string.save)).onBacklistener(new BaseDialog.BaseOnBack() {
                @Override
                public void onConfirm() {
                    List<HashMap<String, Object>> mapList = new ArrayList<>();

                    for (MenuBean dt : custAdapter.getData()) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("serviceInfoId", dt.getId());
                        map.put("sort", custAdapter.getData().indexOf(dt));
                        mapList.add(map);
                    }
                    presenter.editMyService(mapList);
                }
            }).show();
        });
        binding.tlMore.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (menuState == RecyclerView.SCROLL_STATE_IDLE) {
                    nextPosition = tab.getPosition();
                    LinearLayoutManager layoutManager = (LinearLayoutManager) binding.rvMenu.getLayoutManager();
                    //获取当前RecycleView屏幕可见的第一项和最后一项的Position
                    if (layoutManager != null) {
                        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                        if (nextPosition < firstVisibleItemPosition) {
                            //要置顶的项在当前显示的第一项之前
                            binding.rvMenu.smoothScrollToPosition(nextPosition);
                        } else if (nextPosition < lastVisibleItemPosition) {
                            //要置顶的项已经在屏幕上显示，计算它离屏幕原点的距离
                            int top = binding.rvMenu.getChildAt(nextPosition - firstVisibleItemPosition).getTop();
                            binding.rvMenu.smoothScrollBy(0, top);
                        } else {
                            //要置顶的项在当前显示的最后一项之后
                            mShouldScroll = true;
                            binding.rvMenu.smoothScrollToPosition(nextPosition);
                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.rvMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (mShouldScroll) {
                    mShouldScroll = false;
                    int n = 0;
                    if (layoutManager != null) {
                        n = nextPosition - layoutManager.findFirstVisibleItemPosition();
                    }
                    if (n >= 0 && n < binding.rvMenu.getChildCount()) {
                        //获取要置顶的项顶部距离RecyclerView顶部的距离
                        int top = binding.rvMenu.getChildAt(n).getTop();
                        //进行第二次滚动（最后的距离）
                        binding.rvMenu.smoothScrollBy(0, top);
                    }
                }

                if (menuState == RecyclerView.SCROLL_STATE_SETTLING || menuState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (layoutManager != null) {
                        binding.tlMore.selectTab(binding.tlMore.getTabAt(layoutManager.findFirstVisibleItemPosition()));
                    }
                }


            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                menuState = newState;
            }
        });
    }

    /**
     *
     */
    private void resetUi() {
        //刷新列表
        custAdapter.setShowEdit(showEdit);
        menuAdapter.notifyDataSetChanged();

        if (showEdit) {
            //可拖动
            custAdapter.getDraggableModule().setDragEnabled(true);

            binding.title.setTitle(getString(R.string.option_my_app));
            binding.clMoreEdit.setVisibility(View.VISIBLE);
            binding.tvMyMenu.setVisibility(View.GONE);
            //删除最后1个编辑
            custAdapter.remove(custAdapter.getData().size() - 1);
        } else {
            //不可拖动
            custAdapter.getDraggableModule().setDragEnabled(false);

            binding.title.setTitle(getString(R.string.more));
            binding.clMoreEdit.setVisibility(View.GONE);
            binding.tvMyMenu.setVisibility(View.VISIBLE);
            //添加
            MenuBean dto = new MenuBean();
            dto.setServiceName(getString(R.string.edit));
            custAdapter.addData(dto);
        }
    }

    @Override
    public void onBannerList(List<BannerBean> o) {

    }

    @Override
    public void onServiceList(List<ServiceBean> o) {
        if (o != null && o.size() > 0) {
            for (ServiceBean bean : o) {
                binding.tlMore.addTab(binding.tlMore.newTab().setText(bean.getTypeName()));
            }
            menuAdapter.setNewInstance(o);
        }
    }

    @Override
    public void onMyServiceList(List<MenuBean> o) {
        if (o == null) {
            o = new ArrayList<>();
        }
        MenuBean dt = new MenuBean();
        dt.setServiceName(getString(R.string.edit));

        o.add(o.size(), dt);

        custAdapter.setNewInstance(o);
    }

    @Override
    public void onEditSucc(String o) {
        showEdit = false;
        isEdit = true;
        resetUi();
    }

    @Override
    public void onNewsTypeList(List<NewsTypeBean> o) {

    }

    @Override
    public void onNewsList(List<NewsBean> o) {

    }

    @Override
    public void onNoticeList(List<NewsBean> o) {

    }

    @Override
    public void onUpdate(HashMap<String, String> url) {

    }

    @Override
    public void onBackPressed() {
        if (isEdit) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        showEdit = false;
        resetUi();
    }
}
