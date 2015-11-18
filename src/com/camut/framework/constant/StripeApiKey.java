/**
 * 
 */
package com.camut.framework.constant;

import java.io.Console;

import com.google.common.base.Objects;

/**
 * @ClassName StripeApiKey.java
 * @author wangpin
 * @createtime Jul 28, 2015
 * @author
 * @updateTime
 * @memo
 */
public class StripeApiKey {
	

	public static enum IS_PRODUCTION{
		YES,
		NO
	}

	// environment variable names
	public static String IS_PRODUCTION_VAR = "IS_PRODUCTION";

	
	public final static String TESTPUBLISHKEY="pk_test_iwMqezkfoq23l6fiDxdvhg1A";//测试publishkey
	public final static String TESTAPIKEY="sk_test_D9BCxpj4Naw8GdlZmPDHKvlL";//测试apikey
	
	public final static String LIVEPUBLISHKEY="pk_live_xovT9dFAtWbDUmouH39ULcMq";//正式publishkey
	public final static String LIVEAPIKEY="sk_live_yZEoNLRlxtaW3Vh4cX21jthp";//测试apikey
	
	public static String getApiKey()
	{	
		
		String variable = IS_PRODUCTION_VAR;
		
		String is_production = System.getenv(variable);
		
		if (is_production == null)
		{
			//// Check Java system properties
			is_production = System.getProperty(variable);
		}
		
		String key=null;
		
		if (Objects.equal(is_production, IS_PRODUCTION.NO.toString()))
		{
			key = TESTAPIKEY;
		}
		else if (Objects.equal(is_production, IS_PRODUCTION.YES.toString()))
		{
			key = LIVEAPIKEY;
		}
		else{
			throw new IllegalArgumentException("Environment variable=" +IS_PRODUCTION_VAR + " has invalid value="+ is_production +". "
					+ "Exptecting value=" + IS_PRODUCTION.YES.toString() + "/" + IS_PRODUCTION.NO.toString() );
		}
		
		if (key == null)
		{
			throw new IllegalArgumentException("Stripe API key not defined. The environment variable " + variable + " must be set.");
		}
				
		return key;
	}
}
