import java.lang.reflect.Field;
import java.text.MessageFormat;

import annotations.Form;
import annotations.FormField;
import annotations.Label;
import forms.InputType;

public class FormProcessor {

    /**
     * 
     * translates an instance of HtmlForm class to their actual HTML code
     * 
     * @param htmlForm
     * @return string html code of the provided HtmlForm instance
     */
    public static String htmlify(final Object htmlForm) {
        final Class<?> clazz = htmlForm.getClass();
        // get annotation from the class
        final Form form = clazz.getDeclaredAnnotation(Form.class);
        
        // form is not supposed to be serializable, throw an exception
        if(form == null) {
            throw new IllegalArgumentException(clazz + " is not serializable to an html form.");
        }
        
        final String formName = !form.name().trim().equals("") ? form.name().trim() : clazz.getSimpleName();
        final StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("<form name=\"{0}\" method=\"{1}\" action=\"{2}\">", formName, form.method(), form.action()));
        
        // get all fields from the class
        final Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            
            // get annotation label if it exists
            final Label label = field.getDeclaredAnnotation(Label.class);
            if(label != null) {
                // if label exists, append value
                sb.append(label.value());
            }
            
            // get annotation formField if it exists
            final FormField formField = field.getDeclaredAnnotation(FormField.class);
            if(formField != null) {
                
                // if it exists, let's get the input type so that we know how to translate inputs based on input type
                final InputType inputType = formField.inputType();
                
                // get the value of the field if provided, otherwise show the default value
                final String fieldValue = fieldValue(htmlForm, field, formField.value());
                switch (inputType) {
                    case TEXTAREA:
                        // format for textareas
                        sb.append(MessageFormat.format("<textarea name=\"{0}\">{1}</textarea>", field.getName(), fieldValue));
                        break;
                        // format for refular input boxes
                    case EMAIL:
                    case TEXT:
                        sb.append(MessageFormat.format("<input name=\"{0}\" value=\"{1}\" type=\"{2}\" />", field.getName(), fieldValue, inputType.toString().toLowerCase()));
                        break;
                }
            }
        }
        
        sb.append("</form>");
        return sb.toString();
    }
 
    private static String fieldValue(final Object htmlForm, final Field field, final String defaultValue) {
        Object value;
        try {
            //variable is private, set accessible to true. bad practice!
            field.setAccessible(true);
            // get value of field from htmlForm instance
            value = field.get(htmlForm);
            if(value != null) {
                // return value if provided
                return value.toString();
            }
        }
        catch (IllegalArgumentException | IllegalAccessException e) {
            // ignore anny errors
        }
        
        // return default value
        return defaultValue;
    }
}
