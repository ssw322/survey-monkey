# SSW 322 Project - Survey Monkey Clone
Howdy and welcome to the GitHub repository for our group's submission for the SSW322 project.  This project will essentially be a clone of the popular survey-hosting site "Survey Monkey."  As of right now, we plan on our technology stack involving:

- Frontend:
    - Android client (supporting mobile only)
    - AWS Android SDK
- Backend:
    - AWS Dynamo DB
    - AWS API Gateway
    - AWS Lambda
- Testing and Integration:
    - JUnit
    - Travis CI

## Architectural Approach
This architecture will mimic a sort of _microservices_ take on the problem.  This will be supported by AWS to avoid having to manage any servers -- we would prefer to avoid that if at all possible.

Various Lambda functions will be placed behind the API Gateway.  These Lambdas will likely be written in Node.js and will interact with Dynamo via the AWS SDK for Node.  This allows us to avoid having to write mappings directly inside of the gateway, and can instead translate incoming calls to responses via plain old JavaScript.

We'll flesh this out as we move further along in the development process -- this is just a rough idea to get things off the ground.