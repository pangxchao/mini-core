package com.mini.plugin.builder.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.mini.plugin.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static com.intellij.psi.JavaPsiFacade.getInstance;

public abstract class PsiAbstractBuilder<T, E extends PsiElement> extends AbstractBuilder<T> {
	protected final PsiElementFactory factory;
	
	protected PsiAbstractBuilder(@NotNull Project project) {
		factory = getInstance(project).getElementFactory();
	}
	
	public abstract E build();
	
	public final T add(@NotNull PsiElement element) {
		build().add(element);
		return getThis();
	}
	
	public final T addBefore(@NotNull PsiElement element, Function<E, PsiElement> anchor) {
		build().addBefore(element, anchor.apply(build()));
		return getThis();
	}
	
	public final T addAfter(@NotNull PsiElement element, Function<E, PsiElement> anchor) {
		build().addAfter(element, anchor.apply(build()));
		return getThis();
	}
	
	public final T addRange(PsiElement first, Function<E, PsiElement> last) {
		build().addRange(first, last.apply(build()));
		return getThis();
	}
	
	public final T addRangeBefore(@NotNull PsiElement first, @NotNull Function<E, PsiElement> last,
		Function<E, PsiElement> anchor) {
		build().addRangeBefore(first, last.apply(build()), anchor.apply(build()));
		return getThis();
	}
	
	public final T addRangeAfter(PsiElement first, Function<E, PsiElement> last, Function<E, PsiElement> anchor) {
		build().addRangeAfter(first, last.apply(build()), anchor.apply(build()));
		return getThis();
	}
}
