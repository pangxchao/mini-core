/**
 * Created the com.cfinal.util.CFJSONFile.java
 * @created 2017年4月25日 上午8:43:01
 * @version 1.0.0
 */
package com.cfinal.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * com.cfinal.util.CFJSONFile.java
 * @author XChao
 */
public class CFJSONFile {
	// 定义一个 ScriptEngineManager, 用于调用 script 引擎
	private static ScriptEngineManager engine = new ScriptEngineManager();
	private static ScriptEngine nashorn = engine.getEngineByName("nashorn");

	/**
	 * 将JSON字符串写入文件
	 * @param output 指定文件地址
	 * @param jsonContent
	 */
	public static void write(OutputStream output, String jsonContent) {
		try {
			output.write(jsonContent.getBytes());
			output.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(output != null) {
				try {
					output.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 将JSON字符串写入文件
	 * @param writer 指定文件地址
	 * @param jsonContent
	 */
	public static void write(OutputStreamWriter writer, String jsonContent) {
		try {
			writer.write(jsonContent);
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 将JSON字符串写入文件
	 * @param writer 指定文件地址
	 * @param jsonContent
	 */
	public static void write(BufferedWriter writer, String jsonContent) {
		try {
			writer.write(jsonContent);
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 将JSON字符串写入文件
	 * @param writer 指定文件地址
	 * @param jsonContent
	 */
	public static void write(FileWriter writer, String jsonContent) {
		try {
			writer.write(jsonContent);
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 将JSON字符串写入文件
	 * @param file 指定文件地址
	 * @param jsonContent
	 */
	public static void write(File file, String jsonContent) {
		try {
			write(new FileWriter(file), jsonContent);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将JSON字符串写入文件
	 * @param fileName 指定文件地址
	 * @param jsonContent
	 */
	public static void write(String fileName, String jsonContent) {
		write(new File(fileName), jsonContent);
	}

	/**
	 * 将JSON文件读取成JSON字符串， <br />
	 * @param reader
	 * @return
	 */
	public static String read(BufferedReader reader) {
		String lineString = null;
		StringBuilder jsonString = new StringBuilder();
		try {
			while ((lineString = reader.readLine()) != null) {
				jsonString.append(lineString).append("\r\n");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return jsonString.toString();
	}

	/**
	 * 将JSON文件读取成JSON字符串， <br />
	 * @param reader
	 * @return
	 */
	public static String read(FileReader reader) {
		try {
			return read(new BufferedReader(reader));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 将JSON文件读取成JSON字符串， <br />
	 * @param reader
	 * @return
	 */
	public static String read(InputStreamReader reader) {
		try {
			return read(new BufferedReader(reader));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 将JSON文件读取成JSON字符串， <br />
	 * @param inputStream
	 * @return
	 */
	public static String read(InputStream inputStream) {
		try {
			return read(new InputStreamReader(inputStream));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 将JSON文件读取成JSON字符串， <br />
	 * @param file
	 * @return
	 */
	public static String read(File file) {
		try {
			return read(new FileReader(file));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将JSON文件读取成JSON字符串， <br />
	 * @param fileName
	 * @return
	 */
	public static String read(String fileName) {
		try {
			return read(new FileReader(fileName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将JSON文件读取成JSONObject对象
	 * @param fileName
	 * @return
	 */
	public static JSONObject readToObject(String fileName) {
		String result = read(fileName);
		if(StringUtils.isNotBlank(result)) {
			return script2JSONObject(result);
		}
		return new JSONObject();
	}

	/**
	 * 将JSON文件读取成JSONObject对象
	 * @param fileName
	 * @return
	 */
	public static JSONObject readToObject(File file) {
		String result = read(file);
		if(StringUtils.isNotBlank(result)) {
			return script2JSONObject(result);
		}
		return new JSONObject();
	}

	/**
	 * 将JSON文件读取成JSONObject对象
	 * @param input
	 * @return
	 */
	public static JSONObject readToObject(InputStream input) {
		String result = read(input);
		if(StringUtils.isNotBlank(result)) {
			return script2JSONObject(result);
		}
		return new JSONObject();
	}

	/**
	 * 将JSON文件读取成JSONArray对象
	 * @param fileName
	 * @return
	 */
	public static JSONArray readToArray(String fileName) {
		String result = read(fileName);
		if(StringUtils.isNotBlank(result)) {
			return script2JSONArray(result);
		}
		return new JSONArray();
	}

	/**
	 * 将JSON文件读取成JSONArray对象
	 * @param fileName
	 * @return
	 */
	public static JSONArray readToArray(File file) {
		String result = read(file);
		if(StringUtils.isNotBlank(result)) {
			return script2JSONArray(result);
		}
		return new JSONArray();
	}

	/**
	 * 将JSON文件读取成JSONArray对象
	 * @param input
	 * @return
	 */
	public static JSONArray readToArray(InputStream input) {
		String result = read(input);
		if(StringUtils.isNotBlank(result)) {
			return script2JSONArray(result);// JSON.parseArray(result);
		}
		return new JSONArray();
	}
	
	/**
	 * 将ScriptObjectMirror对象转换成JSONObject对象
	 * @param script
	 * @return
	 */
	private static JSONObject script2JSONObject(String content) {
		try {
			Object object = nashorn.eval("(" + content + ")");
			return script2JSONObject((ScriptObjectMirror) object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将ScriptObjectMirror对象转换成JSONArray对象
	 * @param script
	 * @return
	 */
	private static JSONArray script2JSONArray(String content) {
		try {
			Object object = nashorn.eval("(" + content + ")");
			return script2JSONArray((ScriptObjectMirror) object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将ScriptObjectMirror对象转换成JSONObject对象
	 * @param script
	 * @return
	 */
	private static JSONObject script2JSONObject(ScriptObjectMirror script) {
		JSONObject jsonObject = new JSONObject();
		script.forEach((key, value) -> {
			if(value instanceof ScriptObjectMirror) {
				ScriptObjectMirror val = null;
				if((val = (ScriptObjectMirror) value).isArray()) {
					jsonObject.put(key, script2JSONArray(val));
				} else {
					jsonObject.put(key, script2JSONObject(val));
				}
			} else {
				jsonObject.put(key, value);
			}
		});

		return jsonObject;
	}

	/**
	 * 将ScriptObjectMirror对象转换成JSONArray对象
	 * @param script
	 * @return
	 */
	private static JSONArray script2JSONArray(ScriptObjectMirror script) {
		JSONArray jsonArray = new JSONArray();
		script.forEach((key, value) -> {
			if(value instanceof ScriptObjectMirror) {
				ScriptObjectMirror val = null;
				if((val = (ScriptObjectMirror) value).isArray()) {
					jsonArray.add(script2JSONArray(val));
				} else {
					jsonArray.add(script2JSONObject(val));
				}
			} else {
				jsonArray.add(value);
			}
		});

		return jsonArray;
	}
}
