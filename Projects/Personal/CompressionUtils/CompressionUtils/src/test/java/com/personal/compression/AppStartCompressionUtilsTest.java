package com.personal.compression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.utils.test.TestInputUtils;

class AppStartCompressionUtilsTest {

	@Test
	void testMainWithoutExit() {

		final String[] args;
		final int input = TestInputUtils.parseTestInputNumber("102");
		if (input == 1) {
			args = new String[] { "compress",
					"D:\\tmp\\CompressionUtils\\test.json",
					"D:\\tmp\\CompressionUtils\\test_COMPRESSED.json" };
		} else if (input == 2) {
			args = new String[] { "decompress",
					"D:\\tmp\\CompressionUtils\\test_COMPRESSED.json",
					"D:\\tmp\\CompressionUtils\\test_DECOMPRESSED.json" };

		} else if (input == 11) {
			args = new String[] { "compress",
					"D:\\tmp\\CompressionUtils\\PDA2OSYM.ypda",
					"D:\\tmp\\CompressionUtils\\PDA2OSYM_COMPRESSED.ypda" };
		} else if (input == 12) {
			args = new String[] { "decompress",
					"D:\\tmp\\CompressionUtils\\PDA2OSYM_COMPRESSED.ypda",
					"D:\\tmp\\CompressionUtils\\PDA2OSYM_DECOMPRESSED.ypda" };

		} else if (input == 21) {
			args = new String[] { "compress_xml",
					"D:\\tmp\\CompressionUtils\\server_raw_data.xml",
					"D:\\tmp\\CompressionUtils\\server_raw_data_COMPRESSED.xml" };
		} else if (input == 22) {
			args = new String[] { "decompress_xml",
					"D:\\tmp\\CompressionUtils\\server_raw_data_COMPRESSED.xml",
					"D:\\tmp\\CompressionUtils\\server_raw_data_DECOMPRESSED.xml" };

		} else if (input == 101) {
			args = new String[] {};
		} else if (input == 102) {
			args = new String[] { "--help" };
		} else if (input == 103) {
			args = new String[] { "--version" };

		} else {
			throw new RuntimeException();
		}

		final int exitCode = AppStartCompressionUtils.mainWithoutExit(args);
		Assertions.assertEquals(0, exitCode);
	}
}
