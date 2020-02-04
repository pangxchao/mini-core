package com.mini.plugin.util;

import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiPackage;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.intellij.ide.util.PackageUtil.findOrCreateDirectoryForPackage;
import static com.mini.plugin.util.Constants.TITLE_INFO;

public final class PackageUtil extends com.intellij.ide.projectView.impl.nodes.PackageUtil {
	
	@Nullable
	public static VirtualFile selectPackageFile(Module module) {
		PackageChooserDialog dialog = new PackageChooserDialog(TITLE_INFO, module);
		dialog.setModal(true);
		dialog.show();
		return Optional.ofNullable(dialog.getSelectedPackage())
			.map(p -> findOrCreateDirectoryForPackage(module, p.getQualifiedName(), null, false))
			.map(PsiDirectory::getVirtualFile)
			.orElse(null);
	}
	
	@Nullable
	public static PsiPackage selectPackage(Module module) {
		PackageChooserDialog dialog = new PackageChooserDialog(TITLE_INFO, module);
		dialog.setModal(true);
		dialog.show();
		return dialog.getSelectedPackage();
	}
}
