package com.example.graphql.exception;

import com.example.graphql.result.Result;
import com.example.graphql.util.MessageUtil;
import org.springframework.core.NestedRuntimeException;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class BaseException extends NestedRuntimeException {
    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String[] errorMsgArgs;
    private Object resultData;
    private Throwable cause;

    public BaseException(Result result) {
        this(result.getResultCode(), result.getResultMsgId(), result.getResultMsgArgs());
    }

    public BaseException(String errorCode, String errorBundleCode) {
        super(MessageUtil.getMessage(errorBundleCode));
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode, String errorMsgId, String errorMsgArg) {
        this(errorCode, errorMsgId, new String[] {errorMsgArg});
    }

    public BaseException(String errorCode, String errorMsgId, String[] errorMsgArgs) {
        super(MessageUtil.getMessage(errorMsgId, errorMsgArgs));
        this.errorCode = errorCode;
        this.errorMsgArgs = errorMsgArgs;
    }

    public BaseException setResultData(Object resultData) {
        this.resultData = resultData;
        return this;
    }

    public BaseException setCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return super.getMessage();
    }

    public String[] getErrorArguments() {
        return errorMsgArgs;
    }

    public Object getResultData() {
        return resultData;
    }

    public Throwable getCause() {
        return cause;
    }

    private boolean hasCause() {
        return null != getCause() && this != getCause();
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (hasCause()) {
            getRootCause().printStackTrace();
        }
    }

    public void printStackTrace(PrintStream stream) {
        super.printStackTrace(stream);
        if (hasCause()) {
            getRootCause().printStackTrace(stream);
        }
    }

    public void printStackTrace(PrintWriter writer) {
        super.printStackTrace(writer);
        if (hasCause()) {
            getRootCause().printStackTrace(writer);
        }
    }
}
