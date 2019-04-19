package sn.mini.java.gen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassInfo {
	private String className;
	private String packageName;
	private final Set<String> importList = new HashSet<>();
	private final List<FieldInfo> fieldList = new ArrayList<>();
	private final List<FieldInfo> staticFieldList = new ArrayList<>();

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Set<String> getImportList() {
		return importList;
	}

	public List<FieldInfo> getFieldList() {
		return fieldList;
	}

	public List<FieldInfo> getStaticFieldList() {
		return staticFieldList;
	}

}
