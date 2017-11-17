package com.huyingbao.dm.ui.inspection.store;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.dm.ui.inspection.model.InspectItem;
import com.huyingbao.dm.ui.inspection.model.Inspection;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.List;

/**
 * 巡检详情模块
 * Created by liujunfeng on 2017/3/29.
 */
public class InspectionDetailStore extends BaseRxStore {
    private static InspectionDetailStore sInstance;
    private List<InspectItem> mInspectItemList;
    private List<MultiItemEntity> mInspectionList;
    private Inspection mInspection;

    private InspectionDetailStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static InspectionDetailStore getInstance(Dispatcher dispatcher) {
        if (sInstance == null) sInstance = new InspectionDetailStore(dispatcher);
        return sInstance;
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_INSPECTION_DETAIL:
            case Actions.GET_INSPECTION_DETAIL_BY_DEVICE:
            case Actions.INSPECTION_START:
            case Actions.INSPECTION_FINISH:
                mInspection = getRightResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_INSPECT_ITEM_LIST_BY_INSPECT:
                mInspectItemList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_INSPECTION_LIST_BY_USER:
                mInspectionList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            default:
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    public Inspection getInspection() {
        return mInspection;
    }

    public List<InspectItem> getInspectItemList() {
        return mInspectItemList;
    }

    public List<MultiItemEntity> getInspectionList() {
        return mInspectionList;
    }
}
