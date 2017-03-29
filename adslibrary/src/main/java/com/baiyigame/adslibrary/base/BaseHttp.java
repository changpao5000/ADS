package com.baiyigame.adslibrary.base;

/**
 * Created by Administrator on 2017/3/2.
 */

public class BaseHttp
{
	//request type
	public static final String Methed_Post = "POST";
	public static final String Methed_Get = "GET";

	//Content-DataType
	public static final String Content_Form = "application/x-www-form-urlencoded";
	public static final String Application_Json = "application/json";

	//save SD
	private boolean isSave = true;
	//body data
	private String BodyData;
	//riquest mothed
	private String mothed;
	//url
	private String url;
	//Content_type
	private String contentType;

	//Header
	private String header;

	public boolean isSave()
	{
		return isSave;
	}

	public void setSave(boolean save)
	{
		isSave = save;
	}

	public String getBodyData()
	{
		return BodyData;
	}

	public void setBodyData(String bodyData)
	{
		BodyData = bodyData;
	}

	public String getMothed()
	{
		return mothed;
	}

	public void setMothed(String mothed)
	{
		this.mothed = mothed;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
}
