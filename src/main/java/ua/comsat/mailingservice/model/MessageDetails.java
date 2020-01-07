package ua.comsat.mailingservice.model;

import com.sendgrid.helpers.mail.objects.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDetails {

    private Email from;
    private Email to;
    private String subject;
    private String message;
}
