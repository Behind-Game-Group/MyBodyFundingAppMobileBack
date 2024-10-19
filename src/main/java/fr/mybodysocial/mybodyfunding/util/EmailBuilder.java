package fr.mybodysocial.mybodyfunding.util;

public class EmailBuilder {

    public static String buildTokenConfirmationEmail(String name, String link) {
        return "Hi " + name + ",\n" + "\n" +
                "Bienvenue sur My Body Funding ! Pour commencer, vous devez confirmer votre adresse e-mail.\n\n\n" +
                link + "\n" + " le lien va expiré dans 15 min \n" + "Cordialement";
    }

    public static String buildTokenResetEmail(String name, String link){
        return "Hi " + name + ",\n" + "\n" +
                "Cliquer sur ce lien pour reset votre mot de passe ! " +
        link + "\n" + " le lien va expiré dans 15 min \n" + "Cordialement";
    }
}
