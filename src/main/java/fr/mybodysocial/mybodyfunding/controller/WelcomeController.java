package fr.mybodysocial.mybodyfunding.controller;


import fr.mybodysocial.mybodyfunding.util.Constante;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(Constante.BASE_URL + "/documentation")
@AllArgsConstructor
public class WelcomeController {

    private static final Logger LOG = LogManager.getLogger();


    /**
     * Redirect to Swagger URL
     *
     * @return the root page (swagger here)
     */
    @GetMapping
    public ModelAndView welcome() {
        WelcomeController.LOG.debug("--> welcome");
        return new ModelAndView("redirect:/swagger-ui.html");
    }

}
