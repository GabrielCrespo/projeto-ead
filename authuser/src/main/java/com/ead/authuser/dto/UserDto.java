package com.ead.authuser.dto;

import com.ead.authuser.validation.PasswordConstraint;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(

        @NotBlank(groups = {UserView.RegistrationPost.class}, message = "Username is mandatory")
        @Size(groups = {UserView.RegistrationPost.class}, min = 4, max = 50, message = "Size must be between 4 and 50")
        @JsonView(UserView.RegistrationPost.class)
        String username,

        @NotBlank(message = "Email is mandatory", groups = {UserView.RegistrationPost.class})
        @Email(message = "Email must be in the expected format", groups = {UserView.RegistrationPost.class})
        @JsonView(UserView.RegistrationPost.class)
        String email,

        @NotBlank(message = "Password is mandatory", groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
        @Size(min = 6, max = 20, message = "Size must be between 6 and 20", groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
        @PasswordConstraint(groups = { UserView.RegistrationPost.class, UserView.PasswordPut.class })
        @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
        String password,

        @NotBlank(message = " Old password is mandatory", groups = UserView.PasswordPut.class)
        @Size(min = 6, max = 20, message = "Size must be between 6 and 20", groups = UserView.PasswordPut.class)
        @PasswordConstraint(groups = UserView.PasswordPut.class)
        @JsonView(UserView.PasswordPut.class)
        String oldPassword,

        @NotBlank(message = "Fullname is mandatory", groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
        @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
        String fullName,

        @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
        String phoneNumber,

        @NotBlank(message = "Image URL is mandatory", groups = UserView.ImagePut.class)
        @JsonView(UserView.ImagePut.class) String
                imageUrl) {

    public interface UserView {

        interface RegistrationPost {
        }

        interface UserPut {
        }

        interface PasswordPut {
        }

        interface ImagePut {
        }

    }

}
