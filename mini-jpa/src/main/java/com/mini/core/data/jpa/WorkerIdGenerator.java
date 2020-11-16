package com.mini.core.data.jpa;

import com.mini.core.util.PKGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class WorkerIdGenerator extends IdentityGenerator {


    public final void setWorkerId(long workerId) {
        PKGenerator.setWorkerId(workerId);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return PKGenerator.id();
    }
}
