#!/bin/bash

echo "================================"
echo "Spring Boot OIDC SSO Application"
echo "================================"
echo ""

# Load environment variables from .env file
if [ -f .env ]; then
    echo "✓ Found .env file"
    echo "Loading environment variables..."

    # Export variables from .env file
    set -a
    source .env
    set +a

    echo "✓ Environment variables loaded"
    echo ""
    echo "Configuration:"
    echo "  CLIENT_ID: ${AUTH0_CLIENT_ID:0:20}..."
    echo "  ISSUER_URI: $AUTH0_ISSUER_URI"
    echo ""
else
    echo "⚠️  Warning: .env file not found!"
    echo ""
    echo "To configure Auth0:"
    echo "  1. Copy the template: cp .env.template .env"
    echo "  2. Edit .env with your Auth0 credentials"
    echo "  3. Run this script again"
    echo ""
    echo "The application will start in demo mode (OAuth2 disabled)."
    echo ""
fi

# Run the Spring Boot application
echo "Starting Spring Boot application..."
echo "Visit: http://localhost:8080"
echo ""
./mvnw spring-boot:run
