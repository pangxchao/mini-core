package com.mini.plugin.action;

import com.intellij.openapi.actionSystem.DefaultActionGroup;

public class DBActionGroup extends DefaultActionGroup {
    public boolean  hideIfNoVisibleChildren() {
        return true;
    }
}