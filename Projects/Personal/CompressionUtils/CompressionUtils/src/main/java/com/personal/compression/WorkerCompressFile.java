package com.personal.compression;

import com.utils.compression.GZipFileCompressionUtils;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

class WorkerCompressFile {

	private final String inputFilePathString;
	private final String outputFilePathString;

	WorkerCompressFile(
			final String inputFilePathString,
			final String outputFilePathString) {

		this.inputFilePathString = inputFilePathString;
		this.outputFilePathString = outputFilePathString;
	}

	int work() {

		int exitCode;
		try {
			GZipFileCompressionUtils.compressFile(inputFilePathString, outputFilePathString);
			exitCode = 0;

		} catch (final Throwable throwable) {
			Logger.printError("error occurred while compressing file");
			Logger.printThrowable(throwable);
			exitCode = -1;
		}
		return exitCode;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
