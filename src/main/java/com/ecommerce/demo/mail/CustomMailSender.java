package com.ecommerce.demo.mail;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class CustomMailSender {

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender javaMailSender;
    private Logger logger = Logger.getLogger(String.valueOf(CustomMailSender.class));
    public void sendRegistrationWelcomeMail(String to) {
        try {
            String path = System.getProperty("user.dir");
            System.out.println("PATH: "+path);
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "  <title></title>\n" +
                    "  <style>\n" +
                    "  @import url('https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&family=Titillium+Web:ital,wght@0,200;0,300;0,400;0,600;0,700;0,900;1,200;1,300;1,400;1,600;1,700&display=swap');\n" +
                    "    *, *::before, *::after {\n" +
                    "      margin: 0;\n" +
                    "      padding: 0;\n" +
                    "      box-sizing: border-box;\n" +
                    "    }\n" +
                    "    .container {\n" +
                    "      text-align: center;\n" +
                    "      margin: 1rem;\n" +
                    "    }\n" +
                    "    .container div:nth-child(2) {\n" +
                    "      display: flex;\n" +
                    "      flex-direction: column;\n" +
                    "      justify-content: center;\n" +
                    "      align-items: center;\n" +
                    "      gap: 4rem;\n" +
                    "    }\n" +
                    "    .container section {\n" +
                    "      font-family: 'Titilium Web';\n" +
                    "    }\n" +
                    "    .container div:first-child {\n" +
                    "      \n" +
                    "      border-bottom: 0.2rem solid black;\n" +
                    "      margin-bottom: 1rem;\n" +
                    "      background: url('/rocket.png') no-repeat;\n" +
                    "      width: 80%;\n" +
                    "      margin: 0 auto;\n" +
                    "      background-size: contain;\n" +
                    "      height: 300px;\n" +
                    "    }\n" +
                    "    h3 {\n" +
                    "      font-size: 2rem;\n" +
                    "      margin-top: 1rem;\n" +
                    "      text-align: center;\n" +
                    "      font-family: 'Noto sans';\n" +
                    "    }\n" +
                    "    label {\n" +
                    "      font-family: 'Titillium Web';\n" +
                    "    }\n" +
                    "    button {\n" +
                    "      width: 120px;\n" +
                    "      height: 2.3rem;\n" +
                    "      margin: 1.2rem auto;\n" +
                    "      background: red;\n" +
                    "      color: white;\n" +
                    "      border: none;\n" +
                    "      font-weight: bolder;\n" +
                    "      font-size: 1.3rem;\n" +
                    "      font-family: cursive;\n" +
                    "      outline: none;\n" +
                    "      box-shadow: 1px 1px 2px darkgray;\n" +
                    "      \n" +
                    "    }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <main class=\"container\">\n" +
                    "      <div></div>\n" +
                    "      <div>\n" +
                    "        <h3>Welcome</h3>\n" +
                    "        <label>You have successfully registered to our Shopping API</label>\n" +
                    "        <section>Thank you for joining with our Shopping API. we will always be there to keep you updated on new API's</section>\n" +
                    "        <button>Explore NOW</button>\n" +
                    "      </div>\n" +
                    "    </main>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(new InternetAddress(from));
            mimeMessageHelper.setSubject("Welcome To Our Shopping API");
            mimeMessageHelper.setText(htmlContent,true);
            mimeMessageHelper.setTo(to);
            javaMailSender.send(mimeMessage);
            System.out.println("mail sent successfully");
        }
        catch(Exception exception) {
            try {
                exception.printStackTrace();
            }
            catch (Exception ex) {
                System.out.println(exception.getMessage());
                logger.info(exception.getMessage());
            }
        }
    }

    public void sendMail(String to, String msg)  {
       try {
           MimeMessage mimeMessage = javaMailSender.createMimeMessage();
           MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
           mimeMessageHelper.setFrom(from);
           mimeMessageHelper.setTo(to);
           mimeMessageHelper.setText(msg,true);
           mimeMessageHelper.setSubject("REQUEST FOR ORDER ID TO PURCHASE PRODUCT");

           javaMailSender.send(mimeMessage);
           System.out.println("mail sent successfully");
       }
       catch(Exception e) {
           try {
               e.printStackTrace();
           }
           catch (Exception ex) {
               System.out.println(ex.getMessage());
               logger.info(ex.getMessage());
           }
       }
    }
}
