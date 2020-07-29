package com.mini.plugin.util;

import java.io.File;
import java.io.IOException;

public class FileUtil extends com.intellij.openapi.util.io.FileUtil {
	
	public static void saveTemplate(File file, String template) {
		try {
			writeToFile(file, template);
		} catch (IOException | RuntimeException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
}
