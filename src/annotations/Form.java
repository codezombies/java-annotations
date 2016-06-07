package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import forms.FormMethod;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Form {

    
    /**
     * @return form name, if none is provided class name will be used
     */
    String name() default "";
    
    /**
     * @return form action
     */
    String action() default ".";
    
    
    /**
     * @return form method
     */
    FormMethod method() default FormMethod.POST;
    
}
