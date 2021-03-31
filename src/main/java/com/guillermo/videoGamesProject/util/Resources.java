package com.guillermo.videoGamesProject.util;


import java.net.URL;

public class Resources {
    /**
     * Create an url with the given name.
     * @param Filename
     * @return URL to the ui file
     */
    public static URL getUI(String Filename){
        return Thread.
                currentThread().
                getContextClassLoader().
                getResource(Filename);
    }
}
