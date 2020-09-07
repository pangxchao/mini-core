package com.mini.plugin.ui.dialog;

import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiPackage;

import java.nio.file.Path;
import java.util.Optional;

import static com.intellij.ide.util.PackageUtil.findOrCreateDirectoryForPackage;
import static com.mini.plugin.util.ModuleUtil.getSourcePath;
import static java.util.Optional.ofNullable;


public class MiniPackageChooserDialog {
    private final PackageChooserDialog dialog;
    private final Module module;

    public MiniPackageChooserDialog(String title, Module module) {
        this.dialog = new PackageChooserDialog(title, module);
        this.dialog.setModal(true);
        this.module = module;
    }

    public void show(DialogCallback callback) {
        dialog.show();
        Optional.ofNullable(dialog.getSelectedPackage()).map(PsiPackage::getQualifiedName).ifPresent(name -> { //
            ofNullable(findOrCreateDirectoryForPackage(module, name, null, false)).ifPresent(dir -> { //
                getSourcePath(module).stream().filter(it -> startWith(dir, it)).findAny() //
                        .ifPresent(it -> callback.invoke(name, it, dir));
            });
        });
    }

    private boolean startWith(PsiDirectory dir, VirtualFile it) {
        return dir.getVirtualFile().getPath().startsWith(it.getPath());
    }

    @FunctionalInterface
    public interface DialogCallback {
        void invoke(String packageName, VirtualFile rootDir, PsiDirectory directory);
    }
}