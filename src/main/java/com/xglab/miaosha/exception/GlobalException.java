package com.xglab.miaosha.exception;

import com.xglab.miaosha.result.CodeMsg;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description: 全局异常
 * @date: 2019/11/14
 */
public class GlobalException extends RuntimeException{

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
