package com.mini.core.mvc.test.entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String name;
    private int age;
    private boolean iSAge;

    public User() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    protected <E extends User, B extends User.Builder<E, B>> User(User.Builder<E, B> builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    public static User.Builder<? extends User, ? extends User.Builder<?, ?>> builder() {
        return new User.BuilderImpl();
    }

    public static User.Builder<? extends User, ? extends User.Builder<?, ?>> builder(User user) {
        return new User.BuilderImpl(user);
    }

    public static abstract class Builder<E extends User, B extends User.Builder<E, B>> {
        private String name;
        private int age;

        protected Builder() {
        }

        protected Builder(User user) {
            if (user == null) {
                return;
            }
            this.name = user.name;
            this.age = user.age;
        }

        protected abstract B getThis();

        public final B name(String name) {
            this.name = name;
            return getThis();
        }

        public final B age(int age) {
            this.age = age;
            return getThis();
        }

        public abstract E build();
    }


    private static final class BuilderImpl extends User.Builder<User, User.BuilderImpl> {
        public BuilderImpl() {
        }

        public BuilderImpl(User user) {
            super(user);
        }

        @Override
        protected User.BuilderImpl getThis() {
            return this;
        }

        @Override
        public final User build() {
            return new User(this);
        }
    }
}
