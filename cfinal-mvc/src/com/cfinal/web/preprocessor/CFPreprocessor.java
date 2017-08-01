/**   
 * Created the com.cfinal.web.preprocessor.CFPreprocessor.java
 * @created 2016年9月29日 上午10:54:26 
 * @version 1.0.0 
 */
package com.cfinal.web.preprocessor;

import com.cfinal.web.central.CFBasics;

/**
 * 预处理器，用于系统环境加载，在系统配置文件加载过后立即执行的接口
 * @author XChao
 */
public interface CFPreprocessor extends CFBasics {
	public void process();
}
