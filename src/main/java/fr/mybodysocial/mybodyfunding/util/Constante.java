package fr.mybodysocial.mybodyfunding.util;

public class Constante {

    public static final String HEADERS_KEY = "Authorization";
    public static final String HEADERS_PREFIX = "Bearer ";
    public static final String REGISTER_SUCCES = "Enregistrement effectué avec succès";
    public static final String LOGIN_SUCCES = "Connexion effectuée avec succès";
    public static final String USER_EXIST_ERROR = "Un utilisateur existe déjà avec cet email";
    public static final String BASE_URL = "/api/myBodyFunding/v0.3";
    public static final String PROJECT_URL = "http://localhost:8080/"+ BASE_URL + "/project";

    

    public static final String[] WHITELIST= {
            Constante.BASE_URL+"/user/register",
            Constante.BASE_URL+"/user/confirm/**",
            Constante.BASE_URL+"/user/login",
            Constante.BASE_URL+"/user/forget_password/**",
            Constante.BASE_URL+"/user/reset_password/**",
            "/v3/api-docs/**", "/swagger-resources/**","/swagger-ui/**"
            };
}
