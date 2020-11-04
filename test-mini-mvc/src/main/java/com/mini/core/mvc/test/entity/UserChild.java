package com.mini.core.mvc.test.entity;

import java.util.Objects;

public class UserChild extends User {
    private String phone;
    private String email;

    public UserChild() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserChild child = (UserChild) o;
        return Objects.equals(phone, child.phone) &&
                Objects.equals(email, child.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phone, email);
    }

    @Override
    public String toString() {
        return "UserChild{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                "} " + super.toString();
    }



}
