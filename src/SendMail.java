import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendMail {
    public static void main(String[] args) {
        final String senderEmail = "new_radio_flex_music@yahoo.com"; // Ваш email Yahoo
        final String senderPassword = "216666zw"; // Ваш пароль или App Password
        final String recipientEmail = "lazowski9.21@gmail.com"; // Email получателя

        // Настройки SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Включаем STARTTLS
        properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Указываем протокол TLS 1.2

        // Создаём сессию с аутентификацией
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Создаём сообщение
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );
            message.setSubject("Тема письма");
            message.setText("Это текст письма.");

            // Отправляем сообщение
            Transport.send(message);
            System.out.println("Письмо успешно отправлено!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}