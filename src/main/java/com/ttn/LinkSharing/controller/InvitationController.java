package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.entities.Topic;
import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.services.TopicService;
import com.ttn.LinkSharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

@Controller
public class InvitationController {

    @Autowired
    UserService userService;
    @Autowired
    TopicService topicService;

    @GetMapping("/sendemail")
    public ModelAndView sendEmail(HttpSession session, HttpServletResponse response, @RequestParam("recEmail") String recEmail,
                                  @RequestParam("topicId") Integer topicId, RedirectAttributes redirectAttributes) throws MessagingException, IOException {
        ModelAndView modelAndView = new ModelAndView();
        User currentUser = (User) session.getAttribute("user");
        User user = userService.getUserByMail(recEmail);
        Topic topic = topicService.getTopicById(topicId);
        String topicName = topic.getTopicName();
        Integer userId = user.getUserId();
        Boolean check = userService.findByEmail(recEmail);
        if (check == true) {
            final String SEmail = "linksharingtothenew@gmail.com";
            final String SPass = "K96sanjeev@";
            final String REmail = recEmail;
            final String subject = "Invitation for Topic" + topicName + "Link Sharing";
            String link = "<html><head></head><body><form name='linkForm' method='get' action='http://localhost:8080/subscribeFromLink'><input type='hidden' name='topicId' value='" + topicId + "'/><input type='hidden' name='userId' value='" + userId + "'/> <button type='submit'>click to subscribe</a> </form></body> </html>";
            final String Body = "You are invited by " + currentUser.getFirstName() + " " + currentUser.getLastName() + " for topic\n" + topicName + " " + link;

            //code for mail sending
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "465");
            Session ses = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SEmail, SPass);
                }
            });

            //For Message
            Multipart multiPart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Message message = new MimeMessage(ses);
            message.setFrom(new InternetAddress(SEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(REmail));
            message.setSubject(subject);
            InternetHeaders headers = new InternetHeaders();
            headers.addHeader("Content-type", "text/html; charset=UTF-8");
            messageBodyPart.setText(Body, "UTF-8", "html");

            multiPart.addBodyPart(messageBodyPart);
            message.setContent(multiPart);
            Transport.send(message);
            redirectAttributes.addFlashAttribute("message", "Invitation sent Successfully");

        } else {
            redirectAttributes.addFlashAttribute("message", "Invitation sent Successfully");

        }
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

}
