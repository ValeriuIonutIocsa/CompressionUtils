package com.personal.compression.modes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public final class FactoryCompressionUtilsMode {

	private static final CompressionUtilsMode[] VALUES = CompressionUtilsMode.values();

	private FactoryCompressionUtilsMode() {
	}

	public static CompressionUtilsMode computeInstanceByDisplayName(
			final String displayName) {

		CompressionUtilsMode compressionUtilsMode = null;
		for (final CompressionUtilsMode aCompressionUtilsMode : VALUES) {

			final String aCompressionUtilsModeDisplayName = aCompressionUtilsMode.getDisplayName();
			if (aCompressionUtilsModeDisplayName.equals(displayName)) {

				compressionUtilsMode = aCompressionUtilsMode;
				break;
			}
		}
		return compressionUtilsMode;
	}

	public static String createSupportedValuesString() {

		final List<String> supportedValueDisplayNameList = new ArrayList<>();
		for (final CompressionUtilsMode compressionUtilsMode : VALUES) {

			final String displayName = compressionUtilsMode.getDisplayName();
			supportedValueDisplayNameList.add(displayName);
		}
		return StringUtils.join(supportedValueDisplayNameList, ',');
	}
}
