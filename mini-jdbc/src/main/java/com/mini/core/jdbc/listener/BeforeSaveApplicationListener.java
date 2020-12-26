package com.mini.core.jdbc.listener;

import com.mini.core.jdbc.common.LongId;
import com.mini.core.jdbc.common.StringId;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.data.relational.core.conversion.DbAction;
import org.springframework.data.relational.core.conversion.DbAction.WithGeneratedId;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import static com.mini.core.util.PKGenerator.id;
import static com.mini.core.util.PKGenerator.uuid;

@Component
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
