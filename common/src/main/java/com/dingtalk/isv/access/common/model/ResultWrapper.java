package com.dingtalk.isv.access.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * service层返回对象列表封装
 * @param <T>
 */
public class ResultWrapper<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<T> list;
    private Long nextCursor;
    private Boolean hasMore;

    public ResultWrapper() {
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getNextCursor() {
        return this.nextCursor;
    }

    public void setNextCursor(Long nextCursor) {
        this.nextCursor = nextCursor;
    }

    public Boolean getHasMore() {
        return this.hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}
