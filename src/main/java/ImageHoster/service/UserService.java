package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {

    private static final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z]|[A-Z])(?=.*[@#$%]).{3,50})";

    @Autowired
    private UserRepository userRepository;

    //Call the registerUser() method in the UserRepository class to persist the user record in the database
    public void registerUser(User newUser) {
        userRepository.registerUser(newUser);
    }

    //Since we did not have any user in the database, therefore the user with username 'upgrad' and password 'password' was hard-coded
    //This method returned true if the username was 'upgrad' and password is 'password'
    //But now let us change the implementation of this method
    //This method receives the User type object
    //Calls the checkUser() method in the Repository passing the username and password which checks the username and password in the database
    //The Repository returns User type object if user with entered username and password exists in the database
    //Else returns null
    public User login(User user) {
        User existingUser = userRepository.checkUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return existingUser;
        } else {
            return null;
        }
    }

    // This method validates that password must contain atleast 1 alphabet, 1 number & 1 special character
    public boolean isPasswordValid(String password) {
        boolean isValid = false;
        Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

        // Validating the password
        if (PASSWORD_PATTERN.matcher(password).matches()) {
            isValid = true;
        }
        return isValid;
    }

}
