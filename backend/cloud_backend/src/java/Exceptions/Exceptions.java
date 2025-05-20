/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author vladafursa
 */
public class Exceptions{
    public static class Timeout extends Exception {
        public Timeout() {
            super("the server didn't respond after several retries");
        }
    }
    
    public static class ServiceUnavailable extends Exception {
        public ServiceUnavailable() {
            super("the server is unavailable");
        }
    }
}
