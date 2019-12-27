package com.mini.core.processor;

import com.mini.core.model.Getter;
import lombok.Builder;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

// @AutoService(Processor.class)
@Builder
public class GetterProcessor extends AbstractProcessor {

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Set.of(Getter.class.getCanonicalName());
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		processingEnv.getElementUtils();
		processingEnv.getFiler();
		processingEnv.getTypeUtils();

		//for (Element element : roundEnv.getElementsAnnotatedWith(Getter.class)) {
		//
		//}


		return false;
	}
}
