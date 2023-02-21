package com.personal.compression;

import com.utils.app_info.AppInfo;
import com.utils.app_info.FactoryAppInfo;

import picocli.CommandLine;

class VersionProviderCompressionUtils implements CommandLine.IVersionProvider {

	VersionProviderCompressionUtils() {
	}

	@Override
	public String[] getVersion() {

		final AppInfo appInfo = FactoryAppInfo.computeInstance("CompressionUtils", "0.0.1");
		final String appTitleAndVersion = appInfo.getAppTitleAndVersion();
		return new String[] { appTitleAndVersion };
	}
}
