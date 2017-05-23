package com.navarna.computerdb.controller;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ControllerServlet extends AbstractAnnotationConfigDispatcherServletInitializer {

    /*
     * Appelle les classes de configuration indiquer.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { ControllerConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    /*
     * Indique le depart de l'application.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}