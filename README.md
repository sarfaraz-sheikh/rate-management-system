# Rate Management System

## Brief overview:<br/>
Logistics company DPWorld charges customers for shipping goods. Charges are calculated based on rate amt and surcharge amt.

## Features:
1- Spring Boot Ouath2 Authentication using Okta<br/>
2- Spring Boot Netflix Hystrix<br/> 
3- JUnit (Mockito, MockMvc, Mocks, Juniper) <br/>
4- Spring Boot Actuator (info, health and metrics) <br/>
5- Spring Data JPA: H2 Database <br/>
6- Spring Logback for Logging <br/>
7- Custom Exception handling (@ControllerAdvice) <br/>
8- Spring Security (oauth2ResourceServer) + Method level security <br/>
9- Spring RestController: HTTP/Restful with request/response in JSON format. <br/>
10- RestTemplate to fetch Surcharge <br/>

URL to generate (Authorization) Bearer token for API calls: <br/>
https://dev-9482693.okta.com/oauth2/default/v1/authorize?client_id=0oa22bqmtbbV6UnM35d6&redirect_uri=https%3A%2F%2Foidcdebugger.com%2Fdebug&scope=openid&response_type=token&response_mode=form_post&state=somethig&nonce=0thq1jzjcw3g

Okta login creds will be shared seperately via email.

## RMS API Details:

###### HEADERS:
Authorization -> Bearer [oauth-token]<br/>
Content-Type -> application/json<br/>

###### 1- Fetch Rate API (GET):<br/>
http://localhost:8080/api/v1/rms/rate/1 <br/>
Response:
{
    "rate": {
        "rateId": 1,
        "rateDescription": "Base Rate",
        "rateEffectiveDate": "2020-12-08T01:15:00",
        "amount": 5000
    },
    "surcharge": {
        "surchargeRate": 100,
        "surchargeDescription": "VAT"
    }
}

###### 2- Add Rate API (POST):<br/>
http://localhost:8080/api/v1/rms/rate <br/>
Request Body:
{
    "rateDescription": "Premium Rate",
    "rateEffectiveDate": "2020-12-19T14:00:00",
    "amount": 5000
}

Respone:
{
    "rateId": 1,
    "rateDescription": "Premium Rate",
    "rateEffectiveDate": "2020-12-19T14:00:00",
    "amount": 5000
}

###### 3- Update Rate API (PUT):<br/>
http://localhost:8080/api/v1/rms/rate <br/>
Request Body:
{
    "rateId": 1,
    "rateDescription": "Premium Rate",
    "rateEffectiveDate": "2020-12-19T14:00:00",
    "amount": 7000
}

Respone:
{
    "rateId": 1,
    "rateDescription": "Premium Rate",
    "rateEffectiveDate": "2020-12-19T14:00:00",
    "amount": 7000
}

###### 4- Delete Rate API (DELETE):<br/>
http://localhost:8080/api/v1/rms/rate/4 <br/>

###### 5- Fetch All Rates (GET)<br/>
http://localhost:8080/api/v1/rms/rates <br/>

Response:
[
    {
        "rateId": 1,
        "rateDescription": "Base Rate",
        "rateEffectiveDate": "2020-12-08T01:15:00",
        "amount": 5000
    },
    {
        "rateId": 2,
        "rateDescription": "Premium Rate",
        "rateEffectiveDate": "2020-12-09T01:15:00",
        "amount": 8000
    }
]
