package com.baiyigame.adslibrary.base;

import com.baiyigame.adslibrary.Utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The Model of the base class
 * Created by Administrator on 2017/2/28.
 */

public class BaseModel implements Serializable
{
    /**
     * Save the JavaBean to the SD card
     *
     * @param model
     */
    public void save(BaseModel model, String fileName)
    {

        boolean isD = FileUtils.deleteFile(fileName);
        ObjectOutputStream out = null;
        try
        {
            //Serialized objects
            out = new ObjectOutputStream(new FileOutputStream(FileUtils.getSDChachPath(fileName)));
            out.writeObject(model);    //Write a customer object
            out.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)
                    out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reading from SD Calvin a JavaBean
     *
     * @param fileName
     * @return
     */
    public Object open(String fileName)
    {
        synchronized (fileName)
        {
            //Deserialize objects
            ObjectInputStream in = null;
            Object model = null;
            try {
                File file = new File(FileUtils.getSDChachPath(fileName));
                if (!file.exists())
                {
                    return null;
                }
                in = new ObjectInputStream(new FileInputStream(file));
                model = in.readObject();    //Read the customer object
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (in != null)
                        in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return model;
        }
    }
}
