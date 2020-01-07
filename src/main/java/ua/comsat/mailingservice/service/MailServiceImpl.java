package ua.comsat.mailingservice.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ua.comsat.mailingservice.model.MessageDetails;
import ua.comsat.mailingservice.model.RawUserInfo;

import java.io.IOException;

@Slf4j
@Configuration
@PropertySource("classpath:configuration.properties")
public class MailServiceImpl implements MailService {

    private static final String TEXT_PLAIN = "text/plain";
    private static final String MAIL_SEND = "mail/send";
    private static final String MESSAGE_TEMPLATE = "First name: %s\n Last name: %s\n Phone: %s\n Message: %s";

    @Value("${receiver.email}")
    private String receiver;

    @Value("${smtp.api.key}")
    private String apiKey;

    @Value("${email.title}")
    private String title;

    public boolean send(RawUserInfo rawUserInfo) {
        log.info("Trying to send message from {}", rawUserInfo.getEmail());

        MessageDetails messageDetails = prepareMessageData(rawUserInfo);
        Content content = new Content(TEXT_PLAIN, messageDetails.getMessage());
        Mail mail = new Mail(messageDetails.getFrom(), messageDetails.getSubject(), messageDetails.getTo(), content);
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint(MAIL_SEND);
            request.setBody(mail.build());
            Response response = sg.api(request);

            log.info("Message sending returned {} status code", response.getStatusCode());

            return response.getStatusCode() == HttpStatus.SC_ACCEPTED;
        } catch (IOException ex) {
            log.error("An error occurred trying to send message");
            ex.printStackTrace();
        }
        return false;
    }

    private MessageDetails prepareMessageData(RawUserInfo rawUserInfo) {
        return MessageDetails.builder()
                .from(new Email(rawUserInfo.getEmail()))
                .message(String.format(MESSAGE_TEMPLATE, rawUserInfo.getFirstName().trim(),
                        rawUserInfo.getLastName().trim(), rawUserInfo.getPhone(), rawUserInfo.getMessage()))
                .to(new Email(receiver))
                .subject(title)
                .build();
    }
}
