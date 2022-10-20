package com.util.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import org.assertj.core.api.Condition;

public class AssertJUtil {

    private AssertJUtil() {
        // Private constructor to hide the default public one.
    }


    /**
     * Return a condition that checks whether an object is an instance of the given class.
     *
     * @param expected the expected class
     * @param <T>      the type of object to test
     * @param <C>      the type of expected class
     * @return a condition
     */
    public static <T, C> Condition<T> instanceOf(final Class<C> expected) {
        return new Condition<>(expected::isInstance, "instanceof " + expected.getName());
    }

    /**
     * Returns a condition that checks whether a class or a method is annotated with the given annotation
     *
     */
    public static <A extends Annotation> Condition<AnnotatedElement> annotatedWith(final Class<A> expected) {
        return new Condition<>(m -> m.isAnnotationPresent(expected), "annotation");
    }



}
