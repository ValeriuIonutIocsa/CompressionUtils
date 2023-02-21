package com.personal.compression;

import java.time.Instant;

import com.personal.compression.modes.CompressionUtilsMode;
import com.personal.compression.modes.FactoryCompressionUtilsMode;
import com.utils.io.IoUtils;
import com.utils.io.PathUtils;
import com.utils.log.Logger;

import picocli.CommandLine;

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

		Logger.setDebugMode(true);

		final CommandLine.Model.CommandSpec commandSpec = CommandLine.Model.CommandSpec.create();
		commandSpec.name("compression_utils");
		commandSpec.mixinStandardHelpOptions(true);

		commandSpec.usageMessage()
				.description("compresses and decompresses regular files and XML files");

		final CommandLine.IVersionProvider versionProvider = new VersionProviderCompressionUtils();
		commandSpec.versionProvider(versionProvider);

		commandSpec.addPositional(CommandLine.Model.PositionalParamSpec.builder()
				.paramLabel("MODE")
				.type(String.class)
				.description("the mode of the application (supported modes: " +
						FactoryCompressionUtilsMode.createSupportedValuesString() + ")")
				.build());

		commandSpec.addPositional(CommandLine.Model.PositionalParamSpec.builder()
				.paramLabel("INPUT_FILE")
				.type(String.class)
				.description("path to the input file")
				.build());

		commandSpec.addPositional(CommandLine.Model.PositionalParamSpec.builder()
				.paramLabel("OUTPUT_FILE")
				.type(String.class)
				.description("path to the output file")
				.build());

		final CommandLine commandLine = new CommandLine(commandSpec);
		commandLine.setExecutionStrategy(AppStartCompressionUtils::run);
		return commandLine.execute(args);
	}

	static int run(
			final CommandLine.ParseResult parseResult) {

		final int exitCode;

		final Integer helpExitCode = CommandLine.executeHelpRequest(parseResult);
		if (helpExitCode != null) {
			exitCode = helpExitCode;

		} else {
			final Instant start = Instant.now();
			Logger.printProgress("compress_utils starting");

			final String modeString = parseResult.matchedPositionalValue(0, null);
			final CompressionUtilsMode compressionUtilsMode =
					FactoryCompressionUtilsMode.computeInstanceByDisplayName(modeString);
			if (compressionUtilsMode == null) {

				Logger.printError("invalid or missing mode");
				exitCode = -1;

			} else {
				Logger.printLine("mode: " + modeString);

				String inputFilePathString = parseResult.matchedPositionalValue(1, null);
				inputFilePathString = PathUtils.computeAbsolutePath("input file", null, inputFilePathString);
				if (inputFilePathString == null) {
					exitCode = -1;

				} else {
					Logger.printLine("input path:");
					Logger.printLine(inputFilePathString);
					if (!IoUtils.fileExists(inputFilePathString)) {

						Logger.printError("input file does not exist");
						exitCode = -1;

					} else {
						String outputFilePathString = parseResult.matchedPositionalValue(2, null);
						outputFilePathString = PathUtils.computeAbsolutePath("output file", null, outputFilePathString);
						if (outputFilePathString == null) {
							exitCode = -1;

						} else {
							Logger.printLine("output path:");
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
			}
			if (exitCode == 0) {
				Logger.printFinishMessage(start);
			}
		}
		return exitCode;
	}
}
