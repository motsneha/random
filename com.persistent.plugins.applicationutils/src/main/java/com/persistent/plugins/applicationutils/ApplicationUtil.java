package com.persistent.plugins.applicationutils;

import org.apache.log4j.Logger;

import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;

@com.appiancorp.suiteapi.expression.annotations.AppianScriptingFunctionsCategory
public class ApplicationUtil {

	private static final Logger LOG = Logger.getLogger(ApplicationUtil.class);

	@Function
	public Long[] getCDTIdsByApplicationUUID(ServiceContext sc, @Parameter String UUID) {
		
		
		return null;
	}

}
