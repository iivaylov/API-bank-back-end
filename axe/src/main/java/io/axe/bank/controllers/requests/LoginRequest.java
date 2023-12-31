package io.axe.bank.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String email;

    private String password;
}
/*

{
    "email": "ultrices.mauris.ipsum@icloud.edu",
    "password": "ol129GO1as"
}

*/