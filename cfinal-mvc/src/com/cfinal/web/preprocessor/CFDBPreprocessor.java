/**
 * Created the com.cfinal.web.preprocessor.CFDBPreprocessor.java
 * @created 2016年10月13日 上午11:25:00
 * @version 1.0.0
 */
package com.cfinal.web.preprocessor;

import com.cfinal.db.CFDBFactory;
import com.cfinal.db.CFDB;

/**
 * 带有默认数据库连接的预处理执行器
 * @author XChao
 */
public abstract class CFDBPreprocessor implements CFPreprocessor {

	protected CFDB getDB(String name) {
		return CFDBFactory.create(name);
	}

	@Override
	public void process() {
		try {
			this.dbProcess();
		} finally {
			for (String name : CFDBFactory.getThreadDB().keySet()) {
				CFDBFactory.close(CFDBFactory.getThreadDB().get(name));
			}
		}
	}

	public abstract void dbProcess();

}
