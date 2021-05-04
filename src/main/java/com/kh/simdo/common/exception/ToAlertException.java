package com.kh.simdo.common.exception;

import com.kh.simdo.common.code.ErrorCode;

public class ToAlertException extends CustomException {


    public ToAlertException(ErrorCode error) {
        // TODO Auto-generated constructor stub
        super(error);

    }
    public ToAlertException(ErrorCode error, Exception e) {
        // TODO Auto-generated constructor stub
        super(error,e);

    }

}
