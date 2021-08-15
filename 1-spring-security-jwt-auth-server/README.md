# 1-spring-security-jwt-auth-server

## Running the application locally

Execute the `main` method in the `com.github.duninvit.jwtauthserver.JwtAuthApplication` class from your IDE. Service
will run on `8080` port.

## Testing auth server

To add user to database user next command:

```bash
curl -XPOST -H "content-type: application/json" -d "{\"username\":\"danielle\",\"password\":\"12345\"}" http://localhost:8080/user/add
```

To auth user use next command:

```bash
curl -XPOST -H "content-type: application/json" -d "{\"username\":\"danielle\",\"password\":\"12345\"}" http://localhost:8080/user/auth
```

To finish authentication of user use next command (get otp code from db):

```bash
curl -v -XPOST -H "content-type: application/json" -d "{\"username\":\"danielle\",\"code\":\"1735\"}" http://localhost:8080/otp/check
```
