package com.mini.core.jpa;

import com.mini.core.data.MiniBaseRepository;
import org.jetbrains.annotations.NotNull;

public interface MiniJpaRepository extends MiniBaseRepository  {

    <T> T insert(@NotNull T entity);

    <T> T update(@NotNull T entity);

}
