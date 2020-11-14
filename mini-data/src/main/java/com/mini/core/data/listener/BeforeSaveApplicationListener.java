package com.mini.core.data.listener;

import com.mini.core.data.common.LongId;
import com.mini.core.data.common.StringId;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.data.relational.core.conversion.DbAction;
import org.springframework.data.relational.core.conversion.DbAction.WithGeneratedId;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;

import static com.mini.core.util.PKGenerator.id;
import static com.mini.core.util.PKGenerator.uuid;

public class BeforeSaveApplicationListener implements ApplicationListener<BeforeSaveEvent<Object>> {
    @Override
    public void onApplicationEvent(@NotNull BeforeSaveEvent<Object> beforeSaveEvent) {
        beforeSaveEvent.getAggregateChange().forEachAction(action -> {
            if (!(action instanceof DbAction.WithGeneratedId<?>)) {
                return;
            }
            var entity = ((WithGeneratedId<?>) action).getEntity();
            if (entity instanceof StringId) {
                var id = (StringId) entity;
                if (id.getId() == null) {
                    id.setId(uuid());
                }
            }
            if (entity instanceof LongId) {
                var id = (LongId) entity;
                if (id.getId() == null) {
                    id.setId(id());
                }
            }
        });
    }
}
