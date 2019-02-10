/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.awt.Color;
import java.awt.Font;
import uk.ac.kingston.programming.taskmanager.Constants;
import uk.ac.kingston.programming.taskmanager.controller.StylingListener;
import uk.ac.kingston.programming.taskmanager.data.SettingsManager;

/**
 *
 * @author lucas
 */
public class Styling {

    public static final String DEFAULT_THEME = "Blue";
    public static final String LOGO_IMAGE = "image.png";
    
    private static StylingListener stylingListener;
    static String[] THEMES = new String[] { "Blue", "Green", "Indigo", "Pink"};
    
    public static Font createFontXXXLarge(boolean bold) {
        return createFont(32, bold);
    }
    
    public static Font createFontXXLarge(boolean bold) {
        return createFont(20, bold);
    }
    
    public static Font createFontXLarge(boolean bold) {
        return createFont(18, bold);
    }
    
    public static Font createFontLarge(boolean bold) {
        return createFont(16, bold);
    }
    
    public static Font createFontMedium(boolean bold) {
        return createFont(14, bold);
    }
        
    public static Font createFontSmall(boolean bold) {
        return createFont(12, bold);
    }
    
    public static Font createFont(int size, boolean bold) {
        return new Font(Constants.DEFAULT_FONT_NAME, bold ? Font.BOLD : Font.PLAIN, size);
    } 
    
    public static Color createColor(int transparency) {
        String theme = stylingListener == null ? DEFAULT_THEME : stylingListener.getTheme();
        return createColor(theme, transparency);
    }

    public static Color createColor(String theme, int transparency) {
        
        switch(theme){
            case "Green":
                switch(transparency) {
                    case 100:
                        return new Color(200,230,201);
                    case 200:
                        return new Color(165,214,167);
                    case 300:
                        return new Color(129,199,132);
                    case 400:
                        return new Color(102,187,106);
                    case 500:
                        return new Color(76,175,80);
                    case 600:
                        return new Color(67,160,71);
                    case 700:
                        return new Color(56,142,60);
                    case 800:
                        return new Color(46,125,50);
                    case 900:
                        return new Color(27,94,32);       
                    default:
                        return new Color(76,175,80);
                }
            case "Pink":
                switch(transparency) {
                    case 100:
                        return new Color(248,187,208);
                    case 200:
                        return new Color(244,143,177);
                    case 300:
                        return new Color(240,98,146);
                    case 400:
                        return new Color(236,64,122);
                    case 500:
                        return new Color(233,30,99);
                    case 600:
                        return new Color(216,27,96);
                    case 700:
                        return new Color(194,24,91);
                    case 800:
                        return new Color(173,20,87);
                    case 900:
                        return new Color(136,14,79);       
                    default:
                        return new Color(233,30,99);
                }
            case "Indigo":
                switch(transparency) {
                    case 100:
                        return new Color(197,202,233);
                    case 200:
                        return new Color(159,168,218);
                    case 300:
                        return new Color(121,134,203);
                    case 400:
                        return new Color(92,107,192);
                    case 500:
                        return new Color(63,81,181);
                    case 600:
                        return new Color(57,73,171);
                    case 700:
                        return new Color(48,63,159);
                    case 800:
                        return new Color(40,53,147);
                    case 900:
                        return new Color(26,35,126);
                    default:
                        return new Color(63,81,181);
                }                
            default: // Blue
                switch(transparency) {
                    case 100:
                        return new Color(179,229,252);
                    case 200:
                        return new Color(129,212,250);
                    case 300:
                        return new Color(100,181,246);
                    case 400:
                        return new Color(41,182,246);
                    case 500:
                        return new Color(3,169,244);
                    case 600:
                        return new Color(3,155,229);
                    case 700:
                        return new Color(2,136,209);
                    case 800:
                        return new Color(2,119,189);
                    case 900:
                        return new Color(1,87,155);       
                    default:
                        return new Color(3,169,244);
                }                
        }
            
    }

    /**
     * @param listener the stylingListener to set
     */
    public static void setStylingListener(StylingListener listener) {
        stylingListener = listener;
    }
}
