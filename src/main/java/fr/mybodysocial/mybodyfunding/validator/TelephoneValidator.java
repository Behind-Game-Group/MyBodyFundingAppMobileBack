package fr.mybodysocial.mybodyfunding.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class TelephoneValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //TODO: REGEX to validate email
        String regex = "^(?:0|\\(?\\+33\\)?\\s?|0033\\s?)[1-79](?:[\\.\\-\\s]?\\d\\d){4}$";
        Pattern p = Pattern.compile(regex);
        return p.matcher(s).matches();
    }

}
