package com.personal.compression;

import java.time.Instant;

import com.personal.compression.app_info.FactoryAppInfoCompressionUtils;
import com.personal.compression.modes.CompressionUtilsMode;
import com.personal.compression.modes.FactoryCompressionUtilsMode;
import com.utils.app_info.AppInfo;
import com.utils.io.IoUtils;
import com.utils.io.PathUtils;
import com.utils.log.Logger;

final class AppStartCompressionUtils {

	private AppStartCompressionUtils() {
	}

	public static void main(
			final String[] args) {

		final int exitCode = mainWithoutExit(args);
		System.exit(exitCode);
	}

	static int mainWithoutExit(
			final String[] args) {

		final int exitCode;
		if (args.length == 0) {

			printHelpMessage();
			exitCode = -1;

		} else {
			final String firstArgument = args[0];
			if ("-help".equals(firstArgument)) {

				printHelpMessage();
				exitCode = 0;

			} else if ("-version".equals(firstArgument)) {

				printAppVersion();
				exitCode = 0;

			} else {
				if (args.length < 3) {

					Logger.printError("insufficient command line arguments");
					printHelpMessage();
					exitCode = -1;

				} else {
					exitCode = mainWithoutExitL2(args);
				}
			}
		}
		return exitCode;
	}

	private static int mainWithoutExitL2(
			final String[] args) {

		final int exitCode;
		final Instant start = Instant.now();

		final AppInfo appInfo = FactoryAppInfoCompressionUtils.newInstance();
		final String appTitleAndVersion = appInfo.getAppTitleAndVersion();
		Logger.printProgress("starting " + appTitleAndVersion);

		final String modeString = args[0];
		final CompressionUtilsMode compressionUtilsMode =
				FactoryCompressionUtilsMode.computeInstanceByDisplayName(modeString);
		if (compressionUtilsMode == null) {

			Logger.printError("invalid or missing mode command line argument");
			exitCode = -1;

		} else {
			Logger.printLine("mode: " + modeString);

			String inputFilePathString = args[1];
			inputFilePathString = PathUtils.computeAbsolutePath("input file", null, inputFilePathString);
			if (inputFilePathString == null) {

				Logger.printError("missing input file path command line argument");
				exitCode = -1;

			} else {
				Logger.printLine("input file path:");
				Logger.printLine(inputFilePathString);
				if (!IoUtils.fileExists(inputFilePathString)) {

					Logger.printError("input file does not exist");
					exitCode = -1;

				} else {
					String outputFilePathString = args[2];
					outputFilePathString = PathUtils.computeAbsolutePath("output file", null, outputFilePathString);
					if (outputFilePathString == null) {

						Logger.printError("missing output file path command line argument");
						exitCode = -1;

					} else {
						Logger.printLine("output file path:");
						Logger.printLine(outputFilePathString);

						if (compressionUtilsMode == CompressionUtilsMode.COMPRESS) {
							exitCode = new WorkerCompressFile(inputFilePathString, outputFilePathString).work();
						} else if (compressionUtilsMode == CompressionUtilsMode.DECOMPRESS) {
							exitCode = new WorkerDecompressFile(inputFilePathString, outputFilePathString).work();
						} else if (compressionUtilsMode == CompressionUtilsMode.COMPRESS_XML) {
							exitCode = new WorkerCompressXml(inputFilePathString, outputFilePathString).work();
						} else if (compressionUtilsMode == CompressionUtilsMode.DECOMPRESS_XML) {
							exitCode = new WorkerDecompressXml(inputFilePathString, outputFilePathString).work();
						} else {
							Logger.printError("unsupported mode: " + compressionUtilsMode);
							exitCode = -1;
						}
					}
				}
			}
			if (exitCode == 0) {
				Logger.printFinishMessage(start);
			}
		}
		return exitCode;
	}

	private static void printAppVersion() {

		final AppInfo appInfo = FactoryAppInfoCompressionUtils.newInstance();
		final String appTitleAndVersion = appInfo.getAppTitleAndVersion();
		Logger.printLine(appTitleAndVersion);
	}

	private static void printHelpMessage() {

		final String supportedModesString = FactoryCompressionUtilsMode.createSupportedValuesString();
		Logger.printLine("compression_utils MODE INPUT_FILE_PATH OUTPUT_FILE_PATH" + System.lineSeparator() +
				"    supported modes " + supportedModesString + System.lineSeparator() +
				"compression_utils -version" + System.lineSeparator() +
				"    prints the application version information");
	}
}
