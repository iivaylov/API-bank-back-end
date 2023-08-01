package io.axe.bank.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String confirmPassword;

    private String phoneNumber;

    private String townName;

    private String country;
}

/*

{
	"firstname": "Laura",
	"lastname": "Perez",
	"email": "ultrices.mauris.ipsum@icloud.edu",
	"password": "ol129GO1as",
	"confirmPassword": "ol129GO1as",
	"phoneNumber": "1-823-332-9607",
	"townName": "Newport",
	"country": "Russian Federation"
}

*/
