package com.hedstrom.spring.boot.security.saml.web.controllers;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/saml")
public class SSOController {

    private static final Logger LOG = LoggerFactory.getLogger(SSOController.class);

    @Autowired
    private MetadataManager metadata;

    @RequestMapping(value = "/discovery", method = RequestMethod.GET)
    public String idpSelection(HttpServletRequest request, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            LOG.debug("current auth instance from security context is null");
        } else {
            LOG.debug("current auth instance from security context is " + this.getClass().getSimpleName());
        }

        if (auth == null || (auth instanceof AnonymousAuthenticationToken)) {
            Set<String> idps = metadata.getIDPEntityNames();
            for (String idp : idps)
                LOG.info("configured IDP for SSO is " + idp);
            model.addAttribute("idps", idps);
            return "pages/discovery";
        } else {
            LOG.warn("current user is already logged.");
            return "redirect:/landing";
        }

    }

}
