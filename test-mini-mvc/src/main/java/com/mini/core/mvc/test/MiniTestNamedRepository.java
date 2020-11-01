package com.mini.core.mvc.test;

import com.mini.core.jdbc.MiniBaseRepository;
import com.mini.core.jdbc.MiniNamedRepository;

public interface MiniTestNamedRepository<E> extends MiniNamedRepository, MiniBaseRepository<E> {
}
