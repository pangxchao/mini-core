package com.mini.util.collection;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class MiniCopyOnWriteArrayList extends CopyOnWriteArrayList<Object> implements MiniList, Serializable {
    private static final long serialVersionUID = -9190357503046651915L;

    public MiniCopyOnWriteArrayList() {
    }

    public MiniCopyOnWriteArrayList(@Nonnull Collection<?> c) {
        super(c);
    }

    public MiniCopyOnWriteArrayList(@Nonnull Object[] toCopyIn) {
        super(toCopyIn);
    }
}
