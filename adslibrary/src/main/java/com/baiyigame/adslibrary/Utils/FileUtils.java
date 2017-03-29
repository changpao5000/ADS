package com.baiyigame.adslibrary.Utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.baiyigame.adslibrary.application.ADSLibAplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.baiyigame.adslibrary.Utils.ImageUtils.bitmap2Bytes;
import static com.baiyigame.adslibrary.Utils.ImageUtils.getBitmapToByte;

/**
 * Created by Administrator on 2017/3/1.
 */

public class FileUtils
{
    public static String getSDPath()
    {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static List<String> getImageList(List<String> folderPaths)
    {
        List<String> pathList  = new ArrayList<String>();
        for (int j = 0; j < folderPaths.size(); j++)
        {
            File file = new File(folderPaths.get(j));
            if (file.exists())
            {
                File [] files = file.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                    pathList.add(files[files.length-i-1].getAbsolutePath());
                }
            }
            else
            {
            }
        }
        return pathList;
    }

    public List<String> getAllSDImageFolder(Activity context)
    {

        List<String> allFolder = new ArrayList<String>();
        String[] projection = new String[]
                { MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                "count(" + MediaStore.Images.Media._ID + ")"
        };
        String selection = " 0==0) group by bucket_display_name --(";
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, selection, null, "");
        int i = 0;
        if (null != cursor)
        {
            while (cursor.moveToNext())
            {
                i++;
                String folderId = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                String folder = cursor
                        .getString(cursor
                                .getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                long fileId = cursor.getLong(cursor
                        .getColumnIndex(MediaStore.Images.Media._ID));
                String finaName = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                String path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                File file = new File(path);
                allFolder.add(file.getParentFile().getAbsolutePath());

            }
            if (!cursor.isClosed())
            {
                cursor.close();
            }
        }
        return allFolder;
    }

    public static List<String> getScreenImageFolder(Context context)
    {

        List<String> screenFolder = new ArrayList<String>();

        String[] projection = new String[] { MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                "count(" + MediaStore.Images.Media._ID + ")"
        };
        String selection = " 0==0) group by bucket_display_name --(";
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, selection, null, "");
        if (null != cursor)
        {
            while (cursor.moveToNext())
            {

                String path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                if (path.contains("Screen")||path.contains("��ͼ"))
                {
                    File file = new File(path);
                    screenFolder.add(file.getParentFile().getAbsolutePath());
                }
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }

        }
        return screenFolder;
    }

    public static String getRealFilePath( final Context context, final Uri uri )
    {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) )
        {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) )
        {
            Cursor cursor = context.getContentResolver().query( uri, new String[]
                    { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor )
            {
                if ( cursor.moveToFirst() )
                {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 )
                    {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * Access to the directory
     * @param fileName
     * @return
     */
    public static String getSDChachPath(String fileName)
    {
        File f = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            String SDRoot =  Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(SDRoot+"/ADS");

            if (!file.getAbsoluteFile().exists())
            {
                file.mkdirs();
            }

             f = new File(file.getAbsoluteFile()+"/"+fileName+".properties");
            if (!f.getAbsoluteFile().exists())
            {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return f.getAbsolutePath();//
        //
        //return "Do not share the external storage medium";
    }

    public static String getSDADSRoot()
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            String SDRoot =  Environment.getExternalStorageDirectory().getAbsolutePath();
            return SDRoot+"/ADS";
        }
        return "Do not share the external storage medium";
    }

    public static boolean deleteFile(String fileName)
    {
        File file = new File(getSDADSRoot()+"/"+fileName);
        if (file.exists())
        {
            return file.delete();
        }
        return false;
    }

    /**
     * Save the certificate
     * @param inputStream
     */
    public static void saveCertificate(final InputStream inputStream) {
        if (inputStream == null)
        {
            return;
        }
        new Thread()
        {
            @Override
            public void run()
            {
                OutputStream outputStream= null;
                try
                {
                    int len = 0;
                    byte[] bytes = new byte[4*1024];

                    File file = new File(getSDPath()+"/"+"certificate.cer");
                    if (file.exists())
                    {
                        file.delete();
                    }
                    outputStream = new FileOutputStream(file);
                    while ((len= inputStream.read(bytes))!=-1)
                    {
                        outputStream.write(bytes,0,len);
                        outputStream.flush();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally
                {
                    try {
                        if (inputStream != null)
                        {
                            inputStream.close();
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    try {
                        if (outputStream != null)
                        {
                            outputStream.close();
                        }
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * Read the certificate
     * @return
     */
    public static InputStream readCertificate()
    {
        InputStream inputStream = null;
        try {
            File file = new File(getSDADSRoot()+"/"+"certificate.cer");
            inputStream = new FileInputStream(file);

            ADSLibAplication.setCertificate(inputStream);
            return inputStream;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                if (inputStream != null)
                {
                    inputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Save images to the SD card
     * @param file
     * @param bitmap
     */
    public static void saveBitmapToSD(File file, Bitmap bitmap)
    {
        OutputStream outputStream = null;

        try {

            if (file.exists())
            {
                file.delete();
            }

            outputStream = new FileOutputStream(file);
            outputStream.write(bitmap2Bytes(bitmap));
            outputStream.flush();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                if (outputStream != null)
                {
                    outputStream.close();
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read images to the SD card
     * @param file
     * @return
     */
    public static Bitmap readBitmapFromSD(File file)
    {
        InputStream inputStream = null;
        try
        {
            Bitmap bitmap =  BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bitmap != null)
            {
                return bitmap;
            }
            else
            {
                inputStream = new FileInputStream(file);
                byte[] bytes = new byte[50*1024];
                inputStream.read(bytes);
                return getBitmapToByte(bytes);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                if (inputStream != null)
                {
                    inputStream.close();
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
