package be.vinci.ipl.pfe.group05.shiftingpact.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender emailSender;

  public void sendSimpleEmail(String to, String subject, String text) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(to);
      message.setSubject(subject);
      message.setText(text);
      System.out.println("Envoi de l'email à : " + to);
      emailSender.send(message);
      System.out.println("Email envoyé avec succès");
    } catch (MailException e) {
      System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void sendHtmlEmail(String to, String subject, String htmlText) throws Exception {
    MimeMessage mimeMessage = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setFrom("your-email@gmail.com");
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(htmlText, true);
    emailSender.send(mimeMessage);
  }
}
