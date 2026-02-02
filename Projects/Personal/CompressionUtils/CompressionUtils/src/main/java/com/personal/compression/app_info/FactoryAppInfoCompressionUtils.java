package com.personal.compression.app_info;

import com.utils.app_info.AppInfo;
import com.utils.app_info.FactoryAppInfo;

public final class FactoryAppInfoCompressionUtils {

	private FactoryAppInfoCompressionUtils() {
	}

	public static AppInfo newInstance() {

		return FactoryAppInfo.computeInstance("CompressionUtils", "1.0.1");
	}
}
