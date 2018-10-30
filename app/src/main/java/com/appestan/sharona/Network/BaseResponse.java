package com.appestan.sharona.Network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {

//    @SerializedName("data")
//    @Expose
//    private T data;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

//    public T getData()
//    {
//        return data;
//    }
//
//    public void setData(T data)
//    {
//        this.data = data;
//    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }

    public Boolean getError()
    {
        return error;
    }

    public void setError(Boolean error)
    {
        this.error = error;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}

