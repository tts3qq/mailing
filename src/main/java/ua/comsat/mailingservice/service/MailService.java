package ua.comsat.mailingservice.service;

import ua.comsat.mailingservice.model.RawUserInfo;

public interface MailService {

    boolean send(RawUserInfo rawUserInfo);

}
