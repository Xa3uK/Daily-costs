package org.fishbone.dailycosts.utils;

import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.services.PersonDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    PersonDetailsService personDetailsService;

    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (personDetailsService.findUserByLogin(user.getLogin()).isPresent()) {
            errors.rejectValue("userName", "400",
                "User with name '" + user.getLogin() + "' already exists");
        }
    }
}
