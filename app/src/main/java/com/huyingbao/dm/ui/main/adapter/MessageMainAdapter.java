package com.huyingbao.dm.ui.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.message.model.Message;
import com.huyingbao.rxflux2.util.TimeUtils;

import java.util.List;

/**
 * Created by liujunfeng on 2017/8/11.
 */
public class MessageMainAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {
    public MessageMainAdapter(@Nullable List<Message> data) {
        super(R.layout.item_message_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        helper.setText(R.id.tv_message_main_msg, "● " + item.getMsg())
                .setText(R.id.tv_message_main_time, TimeUtils.formatTime(item.getSendTime()));
    }
}
