import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import annotations.FormField;
import models.ContactUs;

public class FormProcessorTest {

    static class SubscribeForm {
        @FormField
        String email;
    }
    
    @Rule public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testFormProcessorForNonFormClass() {
        exception.expect(IllegalArgumentException.class);
        FormProcessor.htmlify(new SubscribeForm()); // no @Form annotation so it will fail
    }
    
    @Test
    public void testFormProcessorForContactUsFormDefaultValues() {
        
        final ContactUs htmlForm = new ContactUs();
        
        final String html = FormProcessor.htmlify(htmlForm);
        
        // form was printed
        assertTrue(html.indexOf("<form name=\"ContactUs\" method=\"POST\" action=\"/rest/contact-us\">") >= 0);
        assertTrue(html.indexOf("</form>") >= 0);
        
        // first name
        assertTrue(html.indexOf("First Name: ") >= 0);
        assertTrue(html.indexOf("<input name=\"firstName\" value=\"**your first name\" type=\"text\" />") >= 0);

        // last name
        assertTrue(html.indexOf("Last Name: ") >= 0);
        assertTrue(html.indexOf("<input name=\"lastName\" value=\"**your last name\" type=\"text\" />") >= 0);

        // email
        assertTrue(html.indexOf("Email Address: ") >= 0);
        assertTrue(html.indexOf("<input name=\"email\" value=\"**your email\" type=\"email\" />") >= 0);
        
        // message
        assertTrue(html.indexOf("Your message: ") >= 0);
        assertTrue(html.indexOf("<textarea name=\"message\"></textarea>") >= 0);
        
    }

    @Test
    public void testFormProcessorForContactUsFormWithValues() {
        
        final ContactUs htmlForm = new ContactUs();
        htmlForm.setFirstName("Ron");
        htmlForm.setLastName("de Leon");
        htmlForm.setEmail("ronald@codingzombies.com");
        htmlForm.setMessage("Zombies!!!");
        
        final String html = FormProcessor.htmlify(htmlForm);
        
        // form was printed
        assertTrue(html.indexOf("<form name=\"ContactUs\" method=\"POST\" action=\"/rest/contact-us\">") >= 0);
        assertTrue(html.indexOf("</form>") >= 0);
        
        // first name
        assertTrue(html.indexOf("First Name: ") >= 0);
        assertTrue(html.indexOf("<input name=\"firstName\" value=\"Ron\" type=\"text\" />") >= 0);
        
        // last name
        assertTrue(html.indexOf("Last Name: ") >= 0);
        assertTrue(html.indexOf("<input name=\"lastName\" value=\"de Leon\" type=\"text\" />") >= 0);
        
        // email
        assertTrue(html.indexOf("Email Address: ") >= 0);
        assertTrue(html.indexOf("<input name=\"email\" value=\"ronald@codingzombies.com\" type=\"email\" />") >= 0);
        
        // message
        assertTrue(html.indexOf("Your message: ") >= 0);
        assertTrue(html.indexOf("<textarea name=\"message\">Zombies!!!</textarea>") >= 0);
        
    }
    
}
