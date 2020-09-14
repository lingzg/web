package com.lingzg.web.util.file;

import java.io.File;

public class FileUtil {

	public static void fileExists(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
}
