/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.model;

/**
 *
 * @author lucas
 */
public final class AppException extends RuntimeException{
    
    public AppException(String message) {
        super(message);
    }
}
