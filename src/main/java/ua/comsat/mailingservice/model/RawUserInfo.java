package ua.comsat.mailingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RawUserInfo {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String message;
}
