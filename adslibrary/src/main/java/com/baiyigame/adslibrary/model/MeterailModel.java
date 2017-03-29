package com.baiyigame.adslibrary.model;


import com.baiyigame.adslibrary.base.BaseModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * According to the advertisement a key for all advertising material information
 * Advertising material,This is to display on the interface.
 * Created by Administrator on 2017/3/2.
 */

public class MeterailModel extends BaseModel {
    private int status;// 请求标识
    private String msg;//返回信息
    private List<data> data;//返回数据

   public static class data implements Serializable {

            private String key;//广告素材key
            private String name;//广告素材名称(title)
            private ArrayList<String> picurl;//广告素材图片链接
            private String matcon;//广告素材内容
            private String matcontype;// 广告素材内容类型
            private String event;// 广告素材事件(打开链接、下载app)
            private String weburl;// 网址链接(链接地址或app地址)
            private String subtitle;// 副标题
            private String logourl;//logo地址
            private String iconurl;// icon地址
            private String voiceUrl;// 声音地址
            private String voiceDelay;//声音延时
            private String voiceLoop;//声音循环
            private boolean videoFlag;//是否视频
            private boolean voiceFlag;//有无声音

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public ArrayList<String> getPicurl() {
                return picurl;
            }

            public void setPicurl(ArrayList<String> picurl) {
                this.picurl = picurl;
            }

            public String getMatcon() {
                return matcon;
            }

            public void setMatcon(String matcon) {
                this.matcon = matcon;
            }

            public String getMatcontype() {
                return matcontype;
            }

            public void setMatcontype(String matcontype) {
                this.matcontype = matcontype;
            }

            public String getEvent() {
                return event;
            }

            public void setEvent(String event) {
                this.event = event;
            }

            public String getWeburl() {
                return weburl;
            }

            public void setWeburl(String weburl) {
                this.weburl = weburl;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getLogourl() {
                return logourl;
            }

            public void setLogourl(String logourl) {
                this.logourl = logourl;
            }

            public String getIconurl() {
                return iconurl;
            }

            public void setIconurl(String iconurl) {
                this.iconurl = iconurl;
            }

            public String getVoiceUrl() {
                return voiceUrl;
            }

            public void setVoiceUrl(String voiceUrl) {
                this.voiceUrl = voiceUrl;
            }

            public String getVoiceDelay() {
                return voiceDelay;
            }

            public void setVoiceDelay(String voiceDelay) {
                this.voiceDelay = voiceDelay;
            }

            public String getVoiceLoop() {
                return voiceLoop;
            }

            public void setVoiceLoop(String voiceLoop) {
                this.voiceLoop = voiceLoop;
            }

            public boolean isVideoFlag() {
                return videoFlag;
            }

            public void setVideoFlag(boolean videoFlag) {
                this.videoFlag = videoFlag;
            }

            public boolean isVoiceFlag() {
                return voiceFlag;
            }

            public void setVoiceFlag(boolean voiceFlag) {
                this.voiceFlag = voiceFlag;
            }

            @Override
            public String toString() {
                return "InsertMeterailDetail{" +
                        "key='" + key + '\'' +
                        ", name='" + name + '\'' +
                        ", picurl=" + picurl +
                        ", matcon='" + matcon + '\'' +
                        ", matcontype='" + matcontype + '\'' +
                        ", event='" + event + '\'' +
                        ", weburl='" + weburl + '\'' +
                        ", subtitle='" + subtitle + '\'' +
                        ", logourl='" + logourl + '\'' +
                        ", iconurl='" + iconurl + '\'' +
                        ", voiceUrl='" + voiceUrl + '\'' +
                        ", voiceDelay='" + voiceDelay + '\'' +
                        ", voiceLoop='" + voiceLoop + '\'' +
                        ", videoFlag=" + videoFlag +
                        ", voiceFlag=" + voiceFlag +
                        '}';
            }

    }

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

    public List<MeterailModel.data> getData() {
        return data;
    }

    public void setData(List<MeterailModel.data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MeterailModel{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
