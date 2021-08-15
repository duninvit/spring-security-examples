# 1-spring-security-jwt-resource-server

## Running the application locally

Execute the `main` method in the `com.github.duninvit.jwtresourceserver.JwtResourceApplication` class from your IDE.
Service will run on `9000` port.

## Testing resource server

To auth user use next command:

```bash
curl -H "username:danielle" -H "password:12345" http://localhost:9000/login
```

To finish authentication of user use next command (get otp code from db):

```bash
curl -v -H "username:danielle" -H "code:7418" http://localhost:9000/login
```

To call secured endpoint use next command (get token from response of previous command):

```bash
curl -H "Authorization:eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImRhbmllbGxlIn0.umxQ98tgEq8GKa7J2njjF7RMNtlWjIDfmwHQtAHGFmc" http://localhost:9000/hello
```
