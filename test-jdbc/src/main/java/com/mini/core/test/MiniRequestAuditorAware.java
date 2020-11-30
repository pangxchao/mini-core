package com.mini.core.test;

import com.mini.core.test.entity.UserInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.mini.core.mvc.util.WebSession.SESSION_KEY;
import static java.util.Optional.ofNullable;

@Component
public class MiniRequestAuditorAware implements AuditorAware<UserInfo> {

    private HttpSession session;

    @Autowired
    public void setRequest(HttpSession session) {
        this.session = session;
    }

    @NotNull
    @Override
    public Optional<UserInfo> getCurrentAuditor() {
        return ofNullable(session).map(it -> it.getAttribute(SESSION_KEY))
                .filter(it -> it instanceof UserInfo)
                .map(UserInfo.class::cast);
    }
}
