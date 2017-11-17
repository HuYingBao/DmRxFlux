package com.huyingbao.dm.ui.maintenance.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.rxflux2.constant.Constants;

/**
 * 保养项
 * Created by liujunfeng on 2017/5/26.
 */
public class MaintainItemTitle extends AbstractExpandableItem<MaintainItem> implements MultiItemEntity {
    private String position;
    private boolean result;//true 通过,false 不通过

    public MaintainItemTitle(String position, boolean result) {
        this.position = position;
        this.result = result;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return Constants.MAINTAIN_ITEM_TITLE;
    }
}
