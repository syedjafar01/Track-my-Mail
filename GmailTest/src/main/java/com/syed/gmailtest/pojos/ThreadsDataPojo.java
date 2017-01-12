package com.syed.gmailtest.pojos;

import javax.persistence.Entity;

/**
 * Created by syedjafar on 3/1/17.
 */
public class ThreadsDataPojo {

    private String id;
    private String snippet;
    private String historyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }
}
