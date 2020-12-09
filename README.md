# Rate Management System

Rate Management System

# Features:
1- Ouath2 Authentication using Okta
2- Spring Hystrix 
3- JUnit (Mockito, MockMvc, Mocks, Juniper)
4- Spring Actuator (info, health and metrics)
5- Spring Data JPA: H2 Database
6- Spring Logback for Logging
7- Custom Exception handling (@ControllerAdvice)
8- Spring Security (oauth2ResourceServer) + Method level security
9- HTTP/Restful with request/response in JSON format.
10- RestTemplate to fetch Surcharge

URL to generate (Authorization) Bearer token for API calls:
https://dev-9482693.okta.com/oauth2/default/v1/authorize?client_id=0oa22bqmtbbV6UnM35d6&redirect_uri=https%3A%2F%2Foidcdebugger.com%2Fdebug&scope=openid&response_type=token&response_mode=form_post&state=somethig&nonce=0thq1jzjcw3g

Okta login creds will be shared seperately via email.

# RMS API Details:

HEADERS:
Authorization -> Bearer [oauth-token]
Content-Type -> application/json

1- Fetch Rate API (GET):

http://localhost:8080/api/v1/rms/rate/1
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

2- Add Rate API (POST):

http://localhost:8080/api/v1/rms/rate
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

3- Update Rate API (PUT):

http://localhost:8080/api/v1/rms/rate
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

4- Delete Rate API (DELETE):

http://localhost:8080/api/v1/rms/rate/4

5- Fetch All Rates (GET)

http://localhost:8080/api/v1/rms/rates

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
