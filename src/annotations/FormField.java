package annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import forms.InputType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormField {

    /**
     * @return type of form field
     */
    InputType inputType() default InputType.TEXT;
    
    /**
     * @return default value to show
     */
    String value() default "";
    
}
