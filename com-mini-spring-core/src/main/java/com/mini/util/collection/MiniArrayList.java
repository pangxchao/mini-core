package com.mini.util.collection;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class MiniArrayList extends ArrayList<Object> implements MiniList, Serializable {
    private static final long serialVersionUID = 2871930803277311663L;

    public MiniArrayList() {
    }

    public MiniArrayList(int initialCapacity) {
        super(initialCapacity);
    }


    public MiniArrayList(@Nonnull Collection<?> c) {
        super(c);
    }
}
