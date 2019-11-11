package com.zhitu.entity;

import java.io.Serializable;

/**
 * 计数类，主要就是用户上传照片成功失败的反馈
 */
public class Count implements Serializable {

    private int successCount = 0;  //上传成功计数

    private int failedCount = 0;  //上传失败计数，后面会输出失败原因

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }
}
