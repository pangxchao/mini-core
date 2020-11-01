package com.mini.core.jdbc.listener;

import com.mini.core.jdbc.common.StringId;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.relational.core.mapping.event.RelationalEventWithEntity;
import org.springframework.stereotype.Component;

import static com.mini.core.util.PKGenerator.uuid;
import static java.util.Optional.of;

@Component
public class StringIdBeforeSaveApplicationListener implements ApplicationListener<BeforeSaveEvent<StringId>> {
    @Override
    public final void onApplicationEvent(@NotNull BeforeSaveEvent<StringId> event) {
        of(event).map(RelationalEventWithEntity::getEntity).ifPresent(it -> {
            if (it.getId() == null || it.getId().isBlank()) {
                it.setId(uuid());
            }
        });
    }
}
