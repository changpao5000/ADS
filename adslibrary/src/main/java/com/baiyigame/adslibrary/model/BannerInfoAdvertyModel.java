package com.baiyigame.adslibrary.model;


import com.baiyigame.adslibrary.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Banners, plaque kind of advertising information
 * Created by Administrator on 2017/3/1.
 */

public class BannerInfoAdvertyModel extends BaseModel
{
    private int status;
    private String msg;

    private data data;

     public static class data implements Serializable
    {
        private  String i ;//https://117.34.95.103/adp-web 广告系统web地址
        private String n; //string 广告单元1 广告位名称
        private String t;// string Full：插页；Banner：横幅 广告位类型
        private int r ;// 0:不刷新；>0：刷新秒数 刷新时间
        private int s ;// 0：展示一次；与r混用，表示r秒刷新s次 展示次数
        private boolean dap; //bool true：视频自动播放；false：视频不自动播放 视频是否自动播放
        private boolean cap ;//bool true：声音自动播放；false：声音不自动播放 声音是否自动播放
        private String f ;//string 频次 广告位频次
        private String ws;// string 文字广告样式 广告位文字样式
        private List<String> ctl;// list ["Image","Text","Video"] 广告位内容类型

        private style style;// object 样式 广告位样式

        public static class style implements Serializable
        {
            private b b;// base样式
            private c c;// close样式
            private i i;// icon样式
            private l l;//logo样式
            private t t;//text样式

            public static class b implements Serializable
            {
                private int h ;//int 30：表示广告高度为30px，常用于banner类型广告 高度 px
                private int  w ;//int 30 宽度 px
                private int bw ;//int 0：无边框；>0：边框宽度 边宽 px
                private String bc;// string #DDDDDD 颜色 色值FFFFFF
                private int  r ;//int 2：圆角大小为2px 圆角大小
                private int  px;// int 2：左右边框到内容的距离为2px； 竖边到内容距离 px
                private int py ;//int 2：上下边框到内容的距离为2px 横边到内容距离 px
                private int mx ;//int 2：左右边框到手机屏幕边缘的距离为2px 竖边外边距
                private int  my ;//int 2：上下边框到手机屏幕边缘的距离为2px 横边外边距
                private String lu ;//string https://117.34.95.103/adp-web/upload/web/app/loading/loading.gif 加载图片url
                private String lbgc ;//string #FFFFFF 加载背景颜色

                public int getH() {
                    return h;
                }

                public void setH(int h) {
                    this.h = h;
                }

                public int getW() {
                    return w;
                }

                public void setW(int w) {
                    this.w = w;
                }

                public int getBw() {
                    return bw;
                }

                public void setBw(int bw) {
                    this.bw = bw;
                }

                public String getBc() {
                    return bc;
                }

                public void setBc(String bc) {
                    this.bc = bc;
                }

                public int getR() {
                    return r;
                }

                public void setR(int r) {
                    this.r = r;
                }

                public int getPx() {
                    return px;
                }

                public void setPx(int px) {
                    this.px = px;
                }

                public int getPy() {
                    return py;
                }

                public void setPy(int py) {
                    this.py = py;
                }

                public int getMx() {
                    return mx;
                }

                public void setMx(int mx) {
                    this.mx = mx;
                }

                public int getMy() {
                    return my;
                }

                public void setMy(int my) {
                    this.my = my;
                }

                public String getLu() {
                    return lu;
                }

                public void setLu(String lu) {
                    this.lu = lu;
                }

                public String getLbgc() {
                    return lbgc;
                }

                public void setLbgc(String lbgc) {
                    this.lbgc = lbgc;
                }

                @Override
                public String toString() {
                    return "b{" +
                            "h=" + h +
                            ", w=" + w +
                            ", bw=" + bw +
                            ", bc='" + bc + '\'' +
                            ", r=" + r +
                            ", px=" + px +
                            ", py=" + py +
                            ", mx=" + mx +
                            ", my=" + my +
                            ", lu='" + lu + '\'' +
                            ", lbgc='" + lbgc + '\'' +
                            '}';
                }
            }
           public static class c implements Serializable
            {
                private int  delay ;//int 1：广告显示1秒后，显示关闭按钮 关闭延长时常（秒）
                private String url;// string https://117.34.95.103/adp-web/upload/web/app/close/close.png 关闭按钮地址
                private int x ;//int 0：最左；100：最右;n(0<n<100):（屏幕横向分辨率-lw）/100*n px 横向位置 百分比（0最左 100最右）
                private int y;// int 0：最上；100：最下;n(0<n<100):（屏幕纵向分辨率-lh）/100*n px 纵向位置 百分比（0最左 100最右）
                private float i;// float 1：关闭按钮显示在内；0：关闭按钮显示在外；0.5：一半在内一半在外；-1：不显示 关闭按钮显示在内（1内 0外 0.5一半一半）
                private int w ;//int 26：关闭按钮宽度26px 宽度
                private int h ;//int 26：关闭按钮高度26px 高度
                private int r ;//int 2:2px； 圆角大小
                private int bw ;//int 0:0px； logo边宽
                private String bc;// string #000000 logo背景颜色
                private float o ;//float 0.5 背景颜色透明度
                private int p ;//int 0:0px 内边距
                private int m;// int 0:0px 外边距

                public int getDelay() {
                    return delay;
                }

                public void setDelay(int delay) {
                    this.delay = delay;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public float getI() {
                    return i;
                }

                public void setI(float i) {
                    this.i = i;
                }

                public int getW() {
                    return w;
                }

                public void setW(int w) {
                    this.w = w;
                }

                public int getH() {
                    return h;
                }

                public void setH(int h) {
                    this.h = h;
                }

                public int getR() {
                    return r;
                }

                public void setR(int r) {
                    this.r = r;
                }

                public int getBw() {
                    return bw;
                }

                public void setBw(int bw) {
                    this.bw = bw;
                }

                public String getBc() {
                    return bc;
                }

                public void setBc(String bc) {
                    this.bc = bc;
                }

                public float getO() {
                    return o;
                }

                public void setO(float o) {
                    this.o = o;
                }

                public int getP() {
                    return p;
                }

                public void setP(int p) {
                    this.p = p;
                }

                public int getM() {
                    return m;
                }

                public void setM(int m) {
                    this.m = m;
                }

                @Override
                public String toString() {
                    return "c{" +
                            "delay=" + delay +
                            ", url='" + url + '\'' +
                            ", x=" + x +
                            ", y=" + y +
                            ", i=" + i +
                            ", w=" + w +
                            ", h=" + h +
                            ", r=" + r +
                            ", bw=" + bw +
                            ", bc='" + bc + '\'' +
                            ", o=" + o +
                            ", p=" + p +
                            ", m=" + m +
                            '}';
                }
            }
            public static class i implements Serializable
            {
                private int s ;//int 80：80px icon大小，正方形
                private int l;// int 10:ico到左边的距离为10px； 左边距
                private int t ;//int 5：ico到上边的距离为5px； 上边距
                private int bw ;//int 0：无边框；>0（n）：npx logo边宽
                private String bc ;//string #DDDDDD ico边框颜色
                private int r ;//int 2：ico边框圆角为2px 圆角大小
                private int p ;//int 1：ico边框到ico图片的边距为1px 内边距
                private int m ;//int 0：ico边框到ico外部容器的边距为0px 外边距

                public int getS() {
                    return s;
                }

                public void setS(int s) {
                    this.s = s;
                }

                public int getL() {
                    return l;
                }

                public void setL(int l) {
                    this.l = l;
                }

                public int getT() {
                    return t;
                }

                public void setT(int t) {
                    this.t = t;
                }

                public int getBw() {
                    return bw;
                }

                public void setBw(int bw) {
                    this.bw = bw;
                }

                public String getBc() {
                    return bc;
                }

                public void setBc(String bc) {
                    this.bc = bc;
                }

                public int getR() {
                    return r;
                }

                public void setR(int r) {
                    this.r = r;
                }

                public int getP() {
                    return p;
                }

                public void setP(int p) {
                    this.p = p;
                }

                public int getM() {
                    return m;
                }

                public void setM(int m) {
                    this.m = m;
                }

                @Override
                public String toString() {
                    return "i{" +
                            "s=" + s +
                            ", l=" + l +
                            ", t=" + t +
                            ", bw=" + bw +
                            ", bc='" + bc + '\'' +
                            ", r=" + r +
                            ", p=" + p +
                            ", m=" + m +
                            '}';
                }
            }
           public static class l implements Serializable
            {
                private int w ;//int 100:logo宽度为100px； logo宽度
                private int h ;//int 30：logo高度为30px； logo高度
                private int l ;//int 10：logo到左边的距离10px; 左边距
                private int t ;//int 10：logo到上边的距离为10px； 上边距
                private int bw ;//int 1:logo边框为1px； logo边宽
                private String bc ;//string #000000 logo背景颜色
                private int r ;//int 2：logo圆角大小为2px； 圆角大小
                private int p ;//int 1：logo边框到logo图片距离为1px； 内边距
                private int m ;//int 0：logo边框到外部的距离为0px； 外边距

                public int getW() {
                    return w;
                }

                public void setW(int w) {
                    this.w = w;
                }

                public int getH() {
                    return h;
                }

                public void setH(int h) {
                    this.h = h;
                }

                public int getL() {
                    return l;
                }

                public void setL(int l) {
                    this.l = l;
                }

                public int getT() {
                    return t;
                }

                public void setT(int t) {
                    this.t = t;
                }

                public int getBw() {
                    return bw;
                }

                public void setBw(int bw) {
                    this.bw = bw;
                }

                public String getBc() {
                    return bc;
                }

                public void setBc(String bc) {
                    this.bc = bc;
                }

                public int getR() {
                    return r;
                }

                public void setR(int r) {
                    this.r = r;
                }

                public int getP() {
                    return p;
                }

                public void setP(int p) {
                    this.p = p;
                }

                public int getM() {
                    return m;
                }

                public void setM(int m) {
                    this.m = m;
                }

                @Override
                public String toString() {
                    return "l{" +
                            "w=" + w +
                            ", h=" + h +
                            ", l=" + l +
                            ", t=" + t +
                            ", bw=" + bw +
                            ", bc='" + bc + '\'' +
                            ", r=" + r +
                            ", p=" + p +
                            ", m=" + m +
                            '}';
                }
            }
           public static class t implements Serializable
            {
                private int ts ;//int 12:12px 标题文字大小
                private String tc;// string #696969 标题颜色
                private int rs ;//int 10:10px 描述文字大小
                private String rc ;//string #666666 描述颜色
                private int sts ;//int 10:10px 副标题大小
                private String stc;// string #666666 副标题颜色
                private int h ;//int 25:15px 文本区域高度
                private int w;// int 100px 文本区域宽度
                private String bgc ;//string #000000 背景颜色
                private float o ;//float 0：不透明；100：纯透明 背景不透明度
                private int l;// int 100：文本区域到左边的距离为100px 左边距
                private int t;// int 5：文本区域到上边的距离为5px 上边距

                public int getTs() {
                    return ts;
                }

                public void setTs(int ts) {
                    this.ts = ts;
                }

                public String getTc() {
                    return tc;
                }

                public void setTc(String tc) {
                    this.tc = tc;
                }

                public int getRs() {
                    return rs;
                }

                public void setRs(int rs) {
                    this.rs = rs;
                }

                public String getRc() {
                    return rc;
                }

                public void setRc(String rc) {
                    this.rc = rc;
                }

                public int getSts() {
                    return sts;
                }

                public void setSts(int sts) {
                    this.sts = sts;
                }

                public String getStc() {
                    return stc;
                }

                public void setStc(String stc) {
                    this.stc = stc;
                }

                public int getH() {
                    return h;
                }

                public void setH(int h) {
                    this.h = h;
                }

                public int getW() {
                    return w;
                }

                public void setW(int w) {
                    this.w = w;
                }

                public String getBgc() {
                    return bgc;
                }

                public void setBgc(String bgc) {
                    this.bgc = bgc;
                }

                public float getO() {
                    return o;
                }

                public void setO(float o) {
                    this.o = o;
                }

                public int getL() {
                    return l;
                }

                public void setL(int l) {
                    this.l = l;
                }

                public int getT() {
                    return t;
                }

                public void setT(int t) {
                    this.t = t;
                }

                @Override
                public String toString() {
                    return "t{" +
                            "ts=" + ts +
                            ", tc='" + tc + '\'' +
                            ", rs=" + rs +
                            ", rc='" + rc + '\'' +
                            ", sts=" + sts +
                            ", stc='" + stc + '\'' +
                            ", h=" + h +
                            ", w=" + w +
                            ", bgc='" + bgc + '\'' +
                            ", o=" + o +
                            ", l=" + l +
                            ", t=" + t +
                            '}';
                }
            }

            public BannerInfoAdvertyModel.data.style.b getB() {
                return b;
            }

            public void setB(BannerInfoAdvertyModel.data.style.b b) {
                this.b = b;
            }

            public BannerInfoAdvertyModel.data.style.c getC() {
                return c;
            }

            public void setC(BannerInfoAdvertyModel.data.style.c c) {
                this.c = c;
            }

            public BannerInfoAdvertyModel.data.style.i getI() {
                return i;
            }

            public void setI(BannerInfoAdvertyModel.data.style.i i) {
                this.i = i;
            }

            public BannerInfoAdvertyModel.data.style.l getL() {
                return l;
            }

            public void setL(BannerInfoAdvertyModel.data.style.l l) {
                this.l = l;
            }

            public BannerInfoAdvertyModel.data.style.t getT() {
                return t;
            }

            public void setT(BannerInfoAdvertyModel.data.style.t t) {
                this.t = t;
            }

            @Override
            public String toString() {
                return "style{" +
                        "b=" + b.toString() +
                        ", c=" + c.toString()+
                        ", i=" + i.toString() +
                        ", l=" + l.toString() +
                        ", t=" + t.toString() +
                        '}';
            }
        }

        public String getI() {
            return i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getS() {
            return s;
        }

        public void setS(int s) {
            this.s = s;
        }

        public boolean isDap() {
            return dap;
        }

        public void setDap(boolean dap) {
            this.dap = dap;
        }

        public boolean isCap() {
            return cap;
        }

        public void setCap(boolean cap) {
            this.cap = cap;
        }

        public String getF() {
            return f;
        }

        public void setF(String f) {
            this.f = f;
        }

        public String getWs() {
            return ws;
        }

        public void setWs(String ws) {
            this.ws = ws;
        }

        public List<String> getCtl() {
            return ctl;
        }

        public void setCtl(List<String> ctl) {
            this.ctl = ctl;
        }

        public BannerInfoAdvertyModel.data.style getStyle() {
            return style;
        }

        public void setStyle(BannerInfoAdvertyModel.data.style style) {
            this.style = style;
        }

        @Override
        public String toString() {
            return "data{" +
                    "i='" + i + '\'' +
                    ", n='" + n + '\'' +
                    ", t='" + t + '\'' +
                    ", r=" + r +
                    ", s=" + s +
                    ", dap=" + dap +
                    ", cap=" + cap +
                    ", f='" + f + '\'' +
                    ", ws='" + ws + '\'' +
                    ", ctl=" + ctl +
                    ", style=" + style +
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

    public BannerInfoAdvertyModel.data getData() {
        return data;
    }

    public void setData(BannerInfoAdvertyModel.data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BannerInfoAdvertyModel{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
