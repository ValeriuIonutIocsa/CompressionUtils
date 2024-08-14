package com.personal.compression;

import org.w3c.dom.Document;

import com.utils.compression.GZipFileCompressionUtils;
import com.utils.io.PathUtils;
import com.utils.io.file_deleters.FactoryFileDeleter;
import com.utils.log.Logger;
import com.utils.string.StrUtils;
import com.utils.xml.dom.XmlDomUtils;
import com.utils.xml.dom.compress.XmlDomCompressUtils;

class WorkerDecompressXml {

	private final String inputFilePathString;
	private final String outputFilePathString;

	WorkerDecompressXml(
			final String inputFilePathString,
			final String outputFilePathString) {

		this.inputFilePathString = inputFilePathString;
		this.outputFilePathString = outputFilePathString;
	}

	int work() {

		int exitCode;
		try {
			final String outputFilePathStringWoExt = PathUtils.computePathWoExt(outputFilePathString);
			final String extension = PathUtils.computeExtension(outputFilePathString);
			final String temporaryOutputFilePathString =
					PathUtils.computePath(outputFilePathStringWoExt + "_TMP." + extension);

			GZipFileCompressionUtils.decompressFile(inputFilePathString, temporaryOutputFilePathString);

			final Document document = XmlDomUtils.openDocument(temporaryOutputFilePathString);
			XmlDomCompressUtils.decompressXml(document);

			XmlDomUtils.saveXmlFile(document, false, 0, outputFilePathString);

			final boolean success = FactoryFileDeleter.getInstance()
					.deleteFile(temporaryOutputFilePathString, false, true);
			if (success) {
				exitCode = 0;
			} else {
				exitCode = -1;
			}

		} catch (final Exception exc) {
			Logger.printError("error occurred while decompressing XML file");
			Logger.printException(exc);
			exitCode = -1;
		}
		return exitCode;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
