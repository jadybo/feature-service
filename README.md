# Mettle ~ Feature Service API
## Prerequisites
Gradle, Java >= 17.x, Lombok

## Launching the service
1) Go to the 'root' directory of the source code of this app (if you cloned it from Git, that should be 'feature-service') 
2) Launching:  
   ./gradlew bootRun

NOTE: the database employed - H2 - is in-memory database - meaning that all the data is lost when the service shuts down

##Usage
after executing POSTs against FeatureFlag and FeatureFlagUser - the flags enabled by users will be returned by http://localhost:8080/app/1/enabled-feature-flags
use http://localhost:8080/v3/api-docs to see all available endpoints

## Semantics:
To cater for exact requirements the following model/DB semantics have been picked:
1) if flag overridden on user level - this means it's always ON for this user (regardless of parent flag)
- this means you need to DELETE the FeatureFlag->User association to disable feature flag for this user (will be disabled only if parent FF is still disabled too)

## known REST incompatibilities:
1) ID of newly created object (FeatureFlag) is not returned in Location HTTP header - but returned in response payload as a part of whole object definition.
   - this is to ease object syncing on frontend/react side

## TODO Improvements:
1) spring security for both /admin and /app - authentication and authorization using Roles / @Secure 
2) in /app - {userId} should be taken from JWT token claim instead of {userId} in URL Path variable
3) error handling on REST endpoints
4) potential to employ @Version on JPA Entities to prevent 2 Admins overriding flags simultaneously
5) more /admin endpoints - to enable FF 'globally', to disable FF for given user, 'listing' endpoints
6) full auditability of all feature flags - audit trail
7) groups of users / segmentation - flags to be applied to groups of users with one click 
8) flags of types other than boolean
9) scheduling of feature flags (for instance at midnight)
10) introduce Users table and a FK to it?
11) consider defaulting Flag to 'disabled' at DB level with DEFAULT clause

## Building 
./gradlew build

## Open API v3 REST endpoint docs:
http://localhost:8080/v3/api-docs