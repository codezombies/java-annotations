package models;
import annotations.Form;
import annotations.FormField;
import annotations.Label;
import forms.InputType;

@Form(action="/rest/contact-us")
public class ContactUs {

    @Label("First Name: ")
    @FormField("**your first name")
    private String firstName;
    
    @Label("Last Name: ")
    @FormField("**your last name")
    private String lastName;
    
    @Label("Email Address: ")
    @FormField(inputType = InputType.EMAIL, value="**your email")
    private String email;
    
    @Label("Your message: ")
    @FormField(inputType = InputType.TEXTAREA)
    private String message;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

}
