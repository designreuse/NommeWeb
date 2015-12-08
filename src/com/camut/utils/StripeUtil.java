/**
 * 
 */
package com.camut.utils;

import com.camut.framework.constant.StripeApiKey;
import com.google.common.base.Objects;

public class StripeUtil {
	

	public static enum IS_PRODUCTION{
		YES,
		NO
	}

	// environment variable names
	public static String IS_PRODUCTION_VAR = "IS_PRODUCTION";

	public static String getApiKey()
	{	
		String key=null;
		String variable = IS_PRODUCTION_VAR;
		String is_production = System.getenv(variable);
		
		//check environment variable
		if (is_production == null)
		{
			//// Check Java system properties
			is_production = System.getProperty(variable);
		}
		
		//second check
		if (is_production == null){
			System.out.println("ERROR: The environment variable " + variable + " not found");
			return null;
		}
		
		if (Objects.equal(is_production.toLowerCase(), IS_PRODUCTION.NO.toString().toLowerCase()))
		{
			key = StripeApiKey.TESTAPIKEY;
		}
		else if (Objects.equal(is_production.toLowerCase(), IS_PRODUCTION.YES.toString().toLowerCase()))
		{
			key = StripeApiKey.LIVEAPIKEY;
		}
		else{
			System.out.println("ERROR: Environment variable=" +IS_PRODUCTION_VAR + " has invalid value="+ is_production +". "
					+ "Exptecting value=" + IS_PRODUCTION.YES.toString() + "/" + IS_PRODUCTION.NO.toString() );
			return null;
		}
		
		if (key == null)
		{
			System.out.println("ERROR: Stripe API key not defined. The environment variable " + variable + " must be set.");
			return null;
		}
				
		return key;
	}
}
