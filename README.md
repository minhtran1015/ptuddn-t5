# Spring Boot OIDC SSO Demo

This application demonstrates Single Sign-On (SSO) using OpenID Connect (OIDC) protocol with Auth0 as the Identity Provider.

## Features

- **OAuth2/OIDC Authentication** - Complete SSO implementation
- **ID Token Support** - OIDC ID tokens for user identity
- **Refresh Token Support** - Automatic token refresh capability (requires `offline_access` scope)
- **User Profile Access** - Access to user claims and profile information

## Auth0 Setup

1. **Create Auth0 Account**: Sign up at [auth0.com](https://auth0.com)

2. **Create Application**:
   - Go to Applications → Create Application
   - Choose "Regular Web Applications"
   - Select "Java Spring Boot"

3. **Configure Application**:
   - **Allowed Callback URLs**: `http://localhost:8080/login/oauth2/code/auth0`
   - **Allowed Logout URLs**: `http://localhost:8080/`
   - **Allowed Web Origins**: `http://localhost:8080`

4. **Get Credentials**:
   - Note down Domain, Client ID, and Client Secret

## Environment Setup

Set the following environment variables:

```bash
export AUTH0_CLIENT_ID=your-client-id
export AUTH0_CLIENT_SECRET=your-client-secret
export AUTH0_ISSUER_URI=https://your-domain.auth0.com/
```

**Important**: Replace `your-domain`, `your-client-id`, and `your-client-secret` with your actual Auth0 values.

## Running the Application

```bash
./mvnw spring-boot:run
```

Visit `http://localhost:8080` to access the application.

## Endpoints

- `/` - Home page with login options
- `/login` - Initiates OAuth2 login flow
- `/profile` - View user profile and token information (requires authentication)
- `/tokens` - View access and refresh token details (requires authentication)
- `/logout` - Logout and clear session

## Refresh Token Configuration

To enable refresh tokens:

1. The application requests the `offline_access` scope automatically
2. In your Auth0 dashboard, ensure your API allows offline access
3. The refresh token will be returned after successful authentication
4. Spring Security will automatically use it to refresh expired access tokens

## Token Management

The application automatically handles:
- **Access Token**: Used for API authentication
- **Refresh Token**: Used to obtain new access tokens when they expire
- **ID Token**: Contains user identity information (OIDC)

The refresh token functionality is built into Spring Security OAuth2 Client and will automatically refresh expired access tokens when needed.

## Troubleshooting

**Application won't start**: Make sure all three environment variables are set correctly:
- `AUTH0_CLIENT_ID`
- `AUTH0_CLIENT_SECRET`
- `AUTH0_ISSUER_URI` (must end with `/`)

**No refresh token received**: Enable `offline_access` scope in your Auth0 application settings.
