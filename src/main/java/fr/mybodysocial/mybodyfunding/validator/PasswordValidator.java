package fr.mybodysocial.mybodyfunding.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class PasswordValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern p = Pattern.compile(regex);
        return p.matcher(s).matches();
    }

}
