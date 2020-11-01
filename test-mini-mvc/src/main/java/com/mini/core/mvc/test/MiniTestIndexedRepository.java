package com.mini.core.mvc.test;

import com.mini.core.jdbc.MiniBaseRepository;
import com.mini.core.jdbc.MiniIndexedRepository;

public interface MiniTestIndexedRepository<E> extends MiniIndexedRepository, MiniBaseRepository<E> {
}
