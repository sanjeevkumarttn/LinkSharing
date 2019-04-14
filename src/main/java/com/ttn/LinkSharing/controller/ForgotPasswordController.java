package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

@Controller
public class ForgotPasswordController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    @GetMapping("/forgotPassword")
    public String forgotPassword(){
        return "forgetPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordSendMail(@RequestParam("email") String email, HttpServletResponse response) throws MessagingException, IOException {
        Boolean check=userService.findPasswordByEmail(email);
        Random random=new Random();
        int password= 100000 +random.nextInt(20000);
        if (check) {
            session.setAttribute("email",email);
            //System.out.println(session.getAttribute("email"));
            final String SEmail = "linksharing.tothenew@gmail.com";
            final String SPass = "K96sanjeev@";
            final String REmail = email;
            final String subject = "Your Password is here For Link Sharing";
            final String Body = "Your Email Id :" + email + " And OTP is : " + password;

            //code for mail sending
            Properties properties = new Properties();
            properties.put("mail.smtp.host","smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port","465");
            properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth","true");
            properties.put("mail.smtp.port","465");

            Session ses = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SEmail, SPass);
                }
            });

            //For Message

            Message message = new MimeMessage(ses);
            message.setFrom(new InternetAddress(SEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(REmail));
            message.setSubject(subject);
            message.setContent(Body, "text/html");
            Transport.send(message);
            session.setAttribute("otp",String.valueOf(password));
            session.setAttribute("msg", "Mail Sent Successfully");
            return "enterOTP";
        }
        session.setAttribute("msg", "Invalid Mail Id");
        return "redirect:/forgotPassword";

    }

    @PostMapping("/otpCheck")
    public String checkOTP(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String otpUser=String.valueOf(request.getParameter("otp"));
        String otp=(String) session.getAttribute("otp");
        if(otp.equals(otpUser))
            return "changePassword";

        session.setAttribute("msg","Your Otp is Wrong match your OTP");
        return "enterOTP";

    }
    @PostMapping("/resetPassword")
    public ModelAndView setPassword(@RequestParam("password") String password, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        int status = userService.resetPassword(password,session.getAttribute("email").toString());
        if(status>0)
            redirectAttributes.addFlashAttribute("message","Password Changed..!");
        else
            redirectAttributes.addFlashAttribute("message","Issue to Change..! Please try again..");

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}

