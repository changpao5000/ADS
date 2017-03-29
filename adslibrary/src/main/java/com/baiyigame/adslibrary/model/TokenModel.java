package com.baiyigame.adslibrary.model;


import com.baiyigame.adslibrary.base.BaseModel;

import java.io.Serializable;

/**
 * Access token and initialize the advertising information
 * Created by Administrator on 2017/3/1.
 */

public class TokenModel extends BaseModel {

    private int status;
    private String msg;
    private data data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TokenModel.data getData() {
        return data;
    }

    public void setData(TokenModel.data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class data implements Serializable{

        private  String token;
        private long modify_time;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public long getModify_time() {
            return modify_time;
        }

        public void setModify_time(long modify_time) {
            this.modify_time = modify_time;
        }

        @Override
        public String toString() {

            return " data { " +
                    "token='" + token + '\'' +
                    ", modifyTime='" + modify_time + '\'' +
                    '}';
        }
    }
}
