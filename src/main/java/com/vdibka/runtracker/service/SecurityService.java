package com.vdibka.runtracker.service;

public interface SecurityService {
    String findLoggedInUsername();
    
    void autologin(String username, String password);
}
