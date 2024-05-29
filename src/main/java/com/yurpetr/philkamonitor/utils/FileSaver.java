package com.yurpetr.philkamonitor.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSaver {

	private FileSaver() {
	}

	public static void saveTextToFile(String text, String fileName) {

		Path path = Paths.get("assets/" + fileName);
		byte[] textBytes = text.getBytes();

		try {
			log.debug("Saving file assets/{}", fileName);
			Files.write(path, textBytes);
		} catch (IOException e) {
			log.error("Failed to save file");
		}

	}

}
