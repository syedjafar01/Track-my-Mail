package com.syed.gmailtest.pojos;

import java.util.List;

/**
 * Created by syedjafar on 3/1/17.
 */

public class ThreadsPojo {
    private List<ThreadsDataPojo> threads;
    private String nextPageToken;

    public List<ThreadsDataPojo> getThreads() {
        return threads;
    }

    public void setThreads(List<ThreadsDataPojo> threads) {
        this.threads = threads;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}
