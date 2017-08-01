/**
 * Created the com.cfinal.web.preprocessor.CFPreprocessorPorxy.java
 * @created 2016年10月9日 下午3:57:41
 * @version 1.0.0
 */
package com.cfinal.web.preprocessor;

import com.cfinal.web.central.CFAbstractPorxy;

/**
 * com.cfinal.web.preprocessor.CFPreprocessorPorxy.java
 * @author XChao
 */
public class CFPreprocessorPorxy extends CFAbstractPorxy {

	/**
	 * 获取handler 处理器实现类
	 * @return
	 */
	public CFPreprocessor getPreprocessor() {
		return (CFPreprocessor) this.getInstence();
	}
}
