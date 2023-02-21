package com.personal.compression.modes;

public enum CompressionUtilsMode {

    COMPRESS ("compress"),
    DECOMPRESS ("decompress"),
    COMPRESS_XML ("compress_xml"),
    DECOMPRESS_XML ("decompress_xml");

    private final String displayName;

    CompressionUtilsMode(String displayName) {

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
