package com.janus.rodeo.Communication;

public class IntentServiceResult {
    int mResult;
    String mResultValue;

    public IntentServiceResult(String resultValue) {
        mResultValue = resultValue;
    }

    public int getResult() {
        return mResult;
    }

    public String getResultValue() {
        return mResultValue;
    }

}