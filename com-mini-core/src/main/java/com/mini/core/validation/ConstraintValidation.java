package com.mini.core.validation;

import java.lang.annotation.Annotation;

public interface ConstraintValidation<A extends Annotation> {
	void validate(A annotation, Object value);
}
