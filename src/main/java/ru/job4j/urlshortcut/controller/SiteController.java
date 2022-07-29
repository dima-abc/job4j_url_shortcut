package ru.job4j.urlshortcut.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.config.CodeGenerate;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.service.SiteService;

import java.util.*;

/**
 * 3. Мидл
 * 3.4. Spring
 * 3.4.9.2.Контрольные вопросы
 * 2. Сервис - UrlShortCut [#13799]
 * SiteController rest контроллер модели site.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 29.07.2022
 */
@RestController
public class SiteController {
    private static final Logger LOG = LoggerFactory.getLogger(SiteController.class.getSimpleName());
    private static final int PASS_LENGTH = 5;
    private final SiteService sites;
    private final CodeGenerate codeGenerate;

    public SiteController(SiteService sites, CodeGenerate codeGenerate) {
        this.sites = sites;
        this.codeGenerate = codeGenerate;
    }

    /**
     * Метод регистрации сайта
     *
     * @param body
     * @return
     */
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody Map<String, String> body) {
        LOG.info("Registration site={}", body.toString());
        String login = body.get("site");
        Optional<Site> siteRegister = this.sites.findSiteByLogin(login);
        String password = codeGenerate.generate(PASS_LENGTH);
        Site newSite = Site.of(login, password, false);
        if (siteRegister.isEmpty()) {
            newSite.setRegistration(true);
            this.sites.save(newSite);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new LinkedHashMap<String, String>() {{
                    put("registration", String.valueOf(newSite.isRegistration()));
                    put("login", newSite.getLogin());
                    put("password", password);
                }});
    }
}
