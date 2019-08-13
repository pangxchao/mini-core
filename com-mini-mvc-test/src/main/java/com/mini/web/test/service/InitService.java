package com.mini.web.test.service;

import com.google.inject.ImplementedBy;
import com.mini.web.test.service.base.BaseInitService;
import com.mini.web.test.service.impl.InitServiceImpl;

/**
 * --- InitService.java
 * @author xchao
 */
@ImplementedBy(InitServiceImpl.class)
public interface InitService extends BaseInitService {
}
