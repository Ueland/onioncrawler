package no.ueland.onionCrawler.web.data;

import com.google.common.base.Strings;

/**
 * Created by TorHenning on 19.08.2015.
 */
public class WebResponse {
    private Boolean success;
    private Object result;
    private String errorMessage;
    private Throwable t;


    private WebResponse() {
    }

    private WebResponse(Throwable t) {
        this.t = t;
        this.setSuccess(false);
        errorMessage = t.getMessage();
    }

    public static WebResponse success() {
        WebResponse wr = new WebResponse();
        wr.setSuccess(true);
        return wr;
    }

    public static WebResponse success(Object result) {
        WebResponse wr = WebResponse.success();
        wr.setResult(result);
        return wr;
    }

    public static WebResponse fail() {
        WebResponse wr = new WebResponse();
        wr.setSuccess(false);
        return wr;
    }

    public static WebResponse fail(String message) {
        WebResponse wr = WebResponse.fail();
        wr.setErrorMessage(message);
        return wr;
    }

    public static WebResponse fail(Throwable t) {
        WebResponse wr = new WebResponse(t);
        return wr;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        if (!Strings.isNullOrEmpty(errorMessage)) {
            this.errorMessage = errorMessage;
        } else {
            this.errorMessage = null;
        }

    }

}
