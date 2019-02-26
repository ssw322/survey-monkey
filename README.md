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

##API Specification
This is where we get into a little bit of documentation.  If you intend to use the survey lemur API to interact with our service via some other frontend, you'll need to follow the below:

Note - any route marked with an asterisk (*) is a locked route, and will require an authorization token.

#### *POST / - Create a new form
```javascript
{
    type: '', // 'test' || 'survey'
    questions: [ /*...*/ ] // any of singleAnswer, multipleAnswer, or writtenAnswer question schemas
}
```

#### GET /{formID} - Retrieve a form
Path parameters:
- formID - the 5-digit character code given to the user

#### *DELETE /{formID} - Delete a form
Path parameters:
- formID - the 5-digit character code given to the user

#### *PATCH /{formID} - Update questions on a form
Path parameters:
- formID - the 5-digit character code given to the user
```javascript
[ /*...*/ ] // any of singleAnswer, multipleAnswer, or writtenAnswer
//Note that you must supply a full question in order to update it.  Partial questions
//will not be accepted.
```

#### *POST /{formID} - Respond to a form
```javascript
{
    formID: '',
    responses: [ /*...*/ ] //any of these response schemas: single answer, multiple answer, written answer
}
```

#### *GET /{formID}/responses - Retrieve responses from a form
```javascript
[
    {
        user: '', //will only be included on a test
        responseID: 0,
        responses: [
            {
                questionID: 0,
                answerID: 0, //follows the three response schemas
                answerText: '' //populated after submission
            }
        ]
    }
]
```

#### POST /users - Create a new user
```javascript
{
    username: '',
    password: '', //passwords must be at least 8 characteres with numbers, uppercase letters, and lowercase letters
    firstName: '',
    lastName: '',
    email: ''
}
```

#### POST /login - Login a user
Headers:
- Authorization - A basic auth username and password

## Schemas
Any reusable item that we reference in the API specification will be outlined here.  For answers to questions, we specify an ID for every choice.  This cuts down on processing time, as string comparisons are no longer necessary -- instead a simple comparison of integers is needed.

### Questions To Client
These are questions which can appear on a form and are sent down from the server on appropriate requests.

#### Single Answer Question
```javascript
{
    questionType: 'singleAnswer',
    prompt: '',
    questionID: 0,
    choices: [
        {
            choiceText: '',
            choiceID: 0
        }
    ]
    answerID: 0, //suppressed when being sent down from server, required when creating questions
    points: 0 //if the question appears on a test, it may inform the user of point value
}
```

#### Multiple Answer Question
```javascript
{
    questionType: 'multipleAnswer',
    prompt: '',
    questionID: 0,
    choices: [
        {
            choiceText: '',
            choiceID: 0
        },
        {
            choiceText: '',
            choiceID: 1
        }
    ]
    answerIDs: [0, 1],
    points: 0
}
```

#### Written Question
```javascript
{
    questionType: 'writtenAnswer',
    prompt: '',
    questionID: 0,
    answer: '',
    points: 0
}
```

### Responses to Server
When sending data up the server, such as in response to a form that the user has filled out, the following schemas will apply.

#### Single Answer
```javascript
{
    questionID: 0,
    answerID: 0
}
```

#### Multiple Answer
```javascript
{
    questionID: 0,
    answerIDs: [0, 1]
}
```

### Written Answer
```javascript
{
    questionID: 0,
    answer: ''
}
```