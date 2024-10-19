package fr.mybodysocial.mybodyfunding.validator;

import fr.mybodysocial.mybodyfunding.enums.AppUserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;


@Service
@AllArgsConstructor
public class NotAdminValidator implements Predicate<AppUserRole> {

    @Override
    public boolean test(AppUserRole appUserRole) {
        return appUserRole != AppUserRole.ADMIN;
    }
}
