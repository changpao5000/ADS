package com.baiyigame.adslibrary.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.baiyigame.adslibrary.Utils.Utils.bytesToHexString;

public class ImageUtils
{

    public static String hashKeyForDisk(String key)
    {
        String cacheKey;
        try
        {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e)
        {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }


    public static byte[] bitmap2Bytes(Bitmap bm)
    {
        if (bm == null)
        {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap bytes2Bitmap(byte[] bytes)
    {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    /**
     * Drawable → Bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable)
    {
        if (drawable == null)
        {
            return null;
        }
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /*
         * Bitmap → Drawable
		 */
    @SuppressWarnings("deprecation")
    public static Drawable bitmap2Drawable(Bitmap bm)
    {
        if (bm == null)
        {
            return null;
        }
        BitmapDrawable bd = new BitmapDrawable(bm);
        bd.setTargetDensity(bm.getDensity());
        return new BitmapDrawable(bm);
    }

    public static Bitmap revitionImageSize(String path) throws IOException
    {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true)
        {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000))
            {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    public static Bitmap getBitmapToByte(byte[] data)
    {
        if (data != null)
        {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inSampleSize = 1;

            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, opt);
            int bitmapSize = opt.outHeight * opt.outWidth * 4;// pixels*3 if it's RGB and pixels*4
            // if it's ARGB
            if (bitmapSize > 1000 * 1200)
                opt.inSampleSize = 2;
            opt.inJustDecodeBounds = false;
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
            return bm;
        }
        else
        {
            return null;
        }
    }

    /**
     * save image to sd
     * @param file
     * @return
     */
    public static void saveSD(File file,Bitmap bitmap)
    {
       FileUtils.saveBitmapToSD(file,bitmap);
    }

    /**
     * read image from sd
     * @param file
     * @return
     */
    public static Bitmap getBitmapToSD(File file)
    {
        return FileUtils.readBitmapFromSD(file);
    }

    public static boolean isPicture(String url)
    {
        if (url.endsWith(".png")||url.endsWith(".jpg"))
            return true;
        return false;
    }

    public static Bitmap createBitmapFromColor(Context context,int color)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),color);
        return bitmap;
    }

    //生成圆角图片
    //角度可以自定义
    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap,float roundPx)
    {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
//            final float roundPx = 14;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        }
        catch (Exception e)
        {
            return bitmap;
        }
    }
    //生成圆角图片
    //角度可以自定义
    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap,float roundPx,int borderSize)
    {
        try
        {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
//            final float roundPx = 14;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        }
        catch (Exception e)
        {
            return bitmap;
        }
    }
    //提取图像Alpha位图
    //动态修改图片的颜色
    public static Bitmap getAlphaBitmap(Bitmap mBitmap,int mColor)
    {
//          BitmapDrawable mBitmapDrawable = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.enemy_infantry_ninja);
//          Bitmap mBitmap = mBitmapDrawable.getBitmap();

        //BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
        //注意这两个方法的区别
        //Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(), mBitmapDrawable.getIntrinsicHeight(), ADConfig.ARGB_8888);
        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mAlphaBitmap);
        Paint mPaint = new Paint();

        mPaint.setColor(mColor);
        //从原位图中提取只包含alpha的位图
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        //在画布上（mAlphaBitmap）绘制alpha位图
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

        return mAlphaBitmap;
    }

    /**
     * Color对象转换成字符串
     * @param color Color对象
     * @return 16进制颜色字符串
     * */
    private static String toHexFromColor(int color)
    {
        String r,g,b;
        StringBuilder su = new StringBuilder();
        r = Integer.toHexString(Color.red(color));
        g = Integer.toHexString(Color.green(color));
        b = Integer.toHexString(Color.blue(color));
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() ==1 ? "0" +g : g;
        b = b.length() == 1 ? "0" + b : b;
        r = r.toUpperCase();
        g = g.toUpperCase();
        b = b.toUpperCase();
        su.append("0xFF");
        su.append(r);
        su.append(g);
        su.append(b);
        //0xFF0000FF
        return su.toString();
    }
    /**
     * 字符串转换成6进制颜色
     * @param colorStr 16进制颜色字符串
     * @return Color对象
     * */
    public static int toColorFromString(String colorStr)
    {
//        colorStr = colorStr.substring(4);
//        Color color =  new Color() ;
        int color = Color.parseColor(colorStr);
        //java.awt.Color[r=0,g=0,b=255]
        return color;
    }
    /**
     * 给bitmap设置边框
     * @param canvas
     */
    private static void setBitmapBorder(Canvas canvas ,int color)
    {
        Rect rect = canvas.getClipBounds();
        Paint paint = new Paint();
        //设置边框颜色
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        //设置边框宽度
        paint.setStrokeWidth(20);
        canvas.drawRect(rect, paint);
    }

    /**
     * 拼接图片
     * @param bit1
     * @param color
     * @param fw 边框的宽度
     * @return 返回拼接后的Bitmap
     */
    public static Bitmap addBorderToBitmap(Bitmap bit1,Bitmap bit2,int color,int fw)
    {
        int width = bit1.getWidth();
        int height = bit1.getHeight() +fw;
        //创建一个空的Bitmap(内存区域),宽度等于第一张图片的宽度，高度等于两张图片高度总和
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //将bitmap放置到绘制区域,并将要拼接的图片绘制到指定内存区域
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bit1, 0, 0, null);
//        canvas.drawBitmap(bit1, 0, bit1.getHeight(), null);
        //将canvas传递进去并设置其边框
        setBitmapBorder(canvas,color);
        return bitmap;
    }

    /**
     * 合并两张bitmap为一张
     * @param background
     * @param foreground
     * @return Bitmap
     */
    public static Bitmap combineBitmap(Bitmap foreground,Bitmap background, float borderSize)
    {
        if (background == null)
        {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        Bitmap newmap = Bitmap
                .createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
        Paint paint = new Paint();
        canvas.drawBitmap(background, 0, 0, null);
//        canvas.drawBitmap(foreground, bgWidth - borderSize,
//                /*(bgHeight - fgHeight) / 2*/bgHeight -borderSize, null);
        Rect rect = new Rect(0,0,0,0);
        RectF rectF = new RectF(borderSize,borderSize,borderSize,borderSize);
        canvas.drawBitmap(foreground,rect,rect,paint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return newmap;
    }

    /**
     *  图片合成
     *  第一个背景  第二个前景图片
     * @param frameBitmap
     * @param bmp
     * @return
     */
    public static Bitmap addFrameToImageTwo(Bitmap frameBitmap,Bitmap bmp,float borderSize) //bmp原图 frameBitmap资源图片(边框)
    {
        //bmp原图 创建新位图
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap drawBitmap =Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        //对边框进行缩放
        int w = frameBitmap.getWidth();
        int h = frameBitmap.getHeight();
        float scaleX = width*1F / w;        //缩放比 如果图片尺寸超过边框尺寸 会自动匹配
        float scaleY = height*1F / h;
//        float scaleX = borderSize;
//        float scaleY = borderSize;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);   //缩放图片
        Bitmap copyBitmap =  Bitmap.createBitmap(frameBitmap, 0, 0, w, h, matrix, true);

        int pixColor = 0;
        int layColor = 0;
        int newColor = 0;

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixA = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;
        int newA = 0;

        int layR = 0;
        int layG = 0;
        int layB = 0;
        int layA = 0;

        float alpha = 0.8F;
        float alphaR = 0F;
        float alphaG = 0F;
        float alphaB = 0F;

        for (int i = 0; i < width; i++)
        {
            for (int k = 0; k < height; k++)
            {
                pixColor = bmp.getPixel(i, k);
                layColor = copyBitmap.getPixel(i, k);
                // 获取原图片的RGBA值
                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                pixA = Color.alpha(pixColor);
                // 获取边框图片的RGBA值
                layR = Color.red(layColor);
                layG = Color.green(layColor);
                layB = Color.blue(layColor);
                layA = Color.alpha(layColor);
                // 颜色与纯黑色相近的点
                if (layR < 20 && layG < 20 && layB < 20)
                {
                    alpha = 1F;
                }
                else
                {
                    alpha = 0.3F;
                }
                alphaR = alpha;
                alphaG = alpha;
                alphaB = alpha;
                // 两种颜色叠加
                newR = (int) (pixR * alphaR + layR * (1 - alphaR));
                newG = (int) (pixG * alphaG + layG * (1 - alphaG));
                newB = (int) (pixB * alphaB + layB * (1 - alphaB));
                layA = (int) (pixA * alpha + layA * (1 - alpha));
                // 值在0~255之间
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                newA = Math.min(255, Math.max(0, layA));
                //绘制
                newColor = Color.argb(newA, newR, newG, newB);
                drawBitmap.setPixel(i, k, newColor);
            }
        }
        return drawBitmap;
    }
}