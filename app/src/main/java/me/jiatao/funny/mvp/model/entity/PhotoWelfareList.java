package me.jiatao.funny.mvp.model.entity;

import java.util.List;

/**
 * 福利图片集合
 * Created by JiaTao on 2017/4/13.
 */

public class PhotoWelfareList {
    private boolean error;

    private List<PhotoWelfareInfo> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<PhotoWelfareInfo> getResults() {
        return results;
    }

    public void setResults(List<PhotoWelfareInfo> results) {
        this.results = results;
    }
}
