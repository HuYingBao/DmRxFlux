package com.huyingbao.dm.ui.maintenance.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.maintenance.model.MaintainItem;
import com.huyingbao.dm.ui.maintenance.model.MaintainItemTitle;
import com.huyingbao.rxflux2.action.ActionCreator;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.rxflux2.util.AppUtils;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.widget.switchview.SwitchView;

import java.util.List;

import javax.inject.Inject;

/**
 * 保养项
 * Created by liujunfeng on 2017/5/31.
 */
public class MaintainItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    @Inject
    ActionCreator mActionCreator;
    private boolean mChangeAble = false;//false：不可以更改,不显示switch
    private boolean mIsStart = true;//true：显示result图标

    public MaintainItemAdapter(List<MultiItemEntity> dataList) {
        super(dataList);
        AppUtils.getApplicationComponent().inject(this);
        addItemType(Constants.MAINTAIN_ITEM_TITLE, R.layout.item_maintain_item_title);
        addItemType(Constants.MAINTAIN_ITEM, R.layout.item_maintain_item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (item.getItemType()) {
            case Constants.MAINTAIN_ITEM_TITLE:
                final MaintainItemTitle maintainItemTitle = (MaintainItemTitle) item;
                if (mChangeAble) {
                    helper.setGone(R.id.iv_maintain_item_title_result, false);
                } else {
                    helper.setGone(R.id.iv_maintain_item_title_result, mIsStart);
                }
                helper.setText(R.id.tv_maintain_item_title, maintainItemTitle.getPosition())
                        .setImageResource(R.id.iv_maintain_item_title_result, CommonUtils.getSwitchResultImg(maintainItemTitle.getResult()))
                        .setImageResource(R.id.iv_maintain_item_title_drop, maintainItemTitle.isExpanded() ? R.drawable.ic_v_to_down : R.drawable.ic_v_to_right);
                helper.itemView.setSelected(maintainItemTitle.isExpanded());
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    if (maintainItemTitle.isExpanded()) collapse(pos);
                    else expand(pos);
                });
                break;
            case Constants.MAINTAIN_ITEM:
                final MaintainItem maintainItem = (MaintainItem) item;
                helper.setText(R.id.tv_maintain_item_content, maintainItem.getContent())
                        .setGone(R.id.sw_maintain_item_result, mChangeAble)
                        .setImageResource(R.id.iv_maintain_item_content_result, CommonUtils.getSwitchResultImg(maintainItem.getResult()));
                if (mChangeAble) {
                    helper.setGone(R.id.iv_maintain_item_content_result, false);
                    SwitchView swResult = helper.getView(R.id.sw_maintain_item_result);
                    if (maintainItem.getResult()) {
                        swResult.displayOn();
                    } else {
                        swResult.displayOff();
                    }
                    swResult.setOnClickListener(view -> {
                        maintainItem.setResult(!maintainItem.getResult());
                        if (!maintainItem.getResult()) {//显示保养项未完成原因对话框
                            swResult.displayOff();
                            mActionCreator.postLocalAction(Actions.TO_MAINTAIN_ITEM_SHOW_PAUSE, ActionsKeys.POSITION, helper.getAdapterPosition());
                        } else {//清空保养项未完成原因
                            swResult.displayOn();
                            maintainItem.setResultCause(null);
                        }
                    });
                } else {
                    helper.setGone(R.id.iv_maintain_item_content_result, mIsStart);
                    helper.itemView.setOnClickListener(v -> {
                        if (!maintainItem.getResult())
                            mActionCreator.postLocalAction(Actions.TO_MAINTAIN_ITEM_CONTENT, ActionsKeys.MAINTAIN_ITEM_CONTENT, maintainItem);
                    });
                }
                break;
        }
    }

    public void setChangeAble(boolean changeAble) {
        mChangeAble = changeAble;
    }

    public void setStart(boolean start) {
        mIsStart = start;
    }
}