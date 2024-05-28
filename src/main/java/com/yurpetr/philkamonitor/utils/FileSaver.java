package com.yurpetr.philkamonitor.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSaver {

	public static void saveHtmlToFile(String html, String fileName) {

		Path path = Paths.get("assets/" + fileName);
		byte[] htmlBytes = html.getBytes();

		try {
			Files.write(path, htmlBytes);
		} catch (IOException e) {
			log.error("Failed to save file");
		}

	}

}
