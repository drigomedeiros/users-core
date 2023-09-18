# Welcome to Users App

This repository contains the domain module with no infrastructure of a fictional users app. The intention is to use this repo as the base for testing multiple Java frameworks and technologies, providing a comparison between them.

## The Users App

This is a simple web application domain module that does one only action: provide a list of registered users in some repository. For get this awesome functionality, the user must be authenticated and authorized to access the users list.

## How to see it working

As this is a core domain module, there is no frameworks and technologies added. So all you can do here is building and executing tests by typing **./gradlew clean build** on the repository root.

As the result of the command above you can see what the app is expected to do:

```bash
App

  Test should present authentication form as the default page PASSED

Authentication Page

  Test should present authentication form as the default page PASSED
  Test should present an error message right above authentication form when the user login or password are incorrect PASSED
  Test should return redirection to users page if authentication goes well PASSED

Users List Page

  Test should redirect to authentication page when the user is not authenticated/authorized PASSED
  Test should present a message when there is no users registered PASSED
  Test should present a list of users to the user PASSED

Authentication Service

  Test should authenticate a user PASSED
  Test should not authenticate a user when login or password are incorrect PASSED

Authorization Service

  Test should authorize a user PASSED
  Test should not authorize a user when login or password are incorrect PASSED

List Users Service

  Test should return all users PASSED
  Test should return empty list when there is no user registered PASSED
```

## Other repositories developed on top of this

- [Jakarta MVC Project]($)
- [Jakarta EE Backend Project]($)
- [Jakarta MVC Project (Only Frontend)]($)
- [Springboot MVC Frontend Project]($)
- [Springboot Backend Project]($)
- [React Frontend Project]($)