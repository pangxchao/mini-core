package com.mini.core.jdbc.listener;

import com.mini.core.jdbc.common.LongId;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.relational.core.mapping.event.RelationalEventWithEntity;
import org.springframework.stereotype.Component;

import static com.mini.core.util.PKGenerator.id;
import static java.util.Optional.of;

@Component
public class LongIdBeforeSaveApplicationListener implements ApplicationListener<BeforeSaveEvent<LongId>> {
    @Override
    public final void onApplicationEvent(@NotNull BeforeSaveEvent<LongId> event) {
        of(event).map(RelationalEventWithEntity::getEntity).ifPresent(it -> {
            if (it.getId() == null || it.getId() == 0) {
                it.setId(id());
            }
        });
    }
}
