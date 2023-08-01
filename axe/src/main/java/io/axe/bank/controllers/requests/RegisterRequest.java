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

    private String streetName;

    private String townName;

    private String postCode;

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
	"streetName": "Ap Risque Rd.",
	"townName": "Newport",
	"postCode": "DB96 6MC",
	"country": "Russian Federation"
}

*/
