package com.viteksu.kursach.core;

import com.viteksu.kursach.core.messageSystem.MessageSystem;
import com.viteksu.kursach.core.messageSystem.addressService.Address;
import com.viteksu.kursach.core.messageSystem.addressService.AddressService;
import com.viteksu.kursach.core.parameters.PropertyChecker;
import com.viteksu.kursach.web.backEnd.accounts.AccountService;
import com.viteksu.kursach.web.backEnd.accounts.AccountServiceImpl;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;
import com.viteksu.kursach.web.frontEnd.FrontEndService;
import com.viteksu.kursach.web.frontEnd.FrontEndServiceImpl;

import javax.servlet.ServletContext;
import java.io.InputStream;


public class Loader {
    private static Loader loader = null;
    private PropertyChecker propertyChecker = null;

    private Loader() {
        startUp();
    }

    public static Loader getInstance() {
        if (loader == null) {
            synchronized (Loader.class) {
                if (loader == null) {
                    loader = new Loader();
                }
            }
        }
        return loader;
    }

    public PropertyChecker getPropertyChecker(ServletContext servletContext) {
        if (propertyChecker == null) {
            propertyChecker = new PropertyChecker(servletContext);
            new Thread(propertyChecker).start();
            if (propertyChecker != null) {
                synchronized (propertyChecker) {
                    try {
                        propertyChecker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return propertyChecker;
    }

    /*
         * Initialization is here
         * */
    private void startUp() {

        AddressService addressService = AddressService.getInstance();
        MessageSystem messageSystem = new MessageSystem();
        AccountService accountService = new AccountServiceImpl(messageSystem);
        FrontEndService frontEndService = new FrontEndServiceImpl(messageSystem);

        new Thread(accountService).start();
        new Thread(frontEndService).start();
    }
}
