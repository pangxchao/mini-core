package com.mini.core.test.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@Entity
@Table(name = "user_role")
@SuperBuilder(toBuilder = true)
public class UserRole implements Serializable {

    @Data
    @SuperBuilder(toBuilder = true)
    public static class ID implements Serializable {

        // userId
        @Column(name = "user_id")
        private Long userId;

        // roleId
        @Column(name = "role_id")
        private Long roleId;

        @Tolerate
        public ID() {
        }
    }

    @Id
    private ID id;

    @Tolerate
    public UserRole() {
    }


}
 
 
