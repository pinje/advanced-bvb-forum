package com.bvb.user.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.AllArgsConstructor;

public class RegistrationSteps {
    private User user;

    @Given("I am a guest user")
    public void iAmAGuestUser() {
        user = createNewUser();
    }

    @When("I register")
    public void registerUser() {
        register(user);
    }

    @Then("I am redirected to the login page")
    public void iAmRedirectedToTheLoginPage() {
        checkRegistration(user);
    }

    private User createNewUser() {
        return new User("user123", "123", "user@gmail.com");
    }

    private void register(User user) {
        // Implement your logic to register the user
//          # here you can use a tool, perhaps selenium, to goto the registration page
//          # and fill in the registration from. You can use the user to get the email,
//          # password and any other registration details
    }

    private void checkRegistration(User user) {
        // Implement your logic to check if the user is registered
//          # here you will look at where you are in the UI and look at the page to
//          # confirm the user is registered
    }

    @AllArgsConstructor
    private static class User {
        // Define user attributes
        private String username;
        private String password;
        private String email;
    }
}
