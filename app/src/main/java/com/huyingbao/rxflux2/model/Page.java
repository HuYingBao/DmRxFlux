package com.huyingbao.rxflux2.model;

import java.util.List;

/**
 * 分页数据
 * Created by liujunfeng on 2017/5/26.
 */
public class Page<T> {

    /**
     * rows : [{"code":"B-QX-00-03","complexDf":12,"complexJf":10,"createTime":"2017-05-17 22:15:43","degree":3,"dprcPeriod":10,"id":5,"manufacturer":"张家港海纳超声电器有限公司","name":"喷胶机03","org":"清洗二部","power":3,"price":6.4,"remark":"test","serviceDate":"2017-05-17","statusMain":1,"type":"HNP","typeId":5},{"code":"B-QX-00-01","complexDf":12,"complexJf":10,"createTime":"2017-05-17 22:15:43","degree":2,"dprcPeriod":10,"id":4,"manufacturer":"张家港海纳超声电器有限公司","name":"喷胶机","org":"清洗二部","power":3,"price":6.4,"remark":"","serviceDate":"2017-05-17","statusMain":1,"type":"HNP","typeId":5}]
     * total : 2
     */
    private int total;
    private List<T> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
