package com.huyingbao.dm.ui.message.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.message.model.Message;
import com.huyingbao.dm.ui.message.model.MessageData;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.TimeUtils;

import java.util.List;

/**
 * Created by liujunfeng on 2017/8/11.
 */
public class MessageDataAdapter extends BaseQuickAdapter<MessageData, BaseViewHolder> {
    public MessageDataAdapter(@Nullable List<MessageData> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageData item) {
        try {
            Message message = JSONObject.parseObject(item.getContent(), Message.class);
            helper.setText(R.id.tv_message_content, message.getMsg())
                    .setText(R.id.tv_message_status, "已查看")
                    .setBackgroundRes(R.id.tv_message_status, CommonUtils.getStatusColor(Constants.STATUS_DEVICE_NORMAL))
                    .setText(R.id.tv_message_time, TimeUtils.formatTime(TextUtils.isEmpty(item.getSendTime()) ? item.getCreateTime() : item.getSendTime()));
        } catch (Exception e) {
        }
    }
}
