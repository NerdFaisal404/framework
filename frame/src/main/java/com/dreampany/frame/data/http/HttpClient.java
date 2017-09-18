package com.dreampany.frame.data.http;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nuc on 6/11/2016.
 */
public final class HttpClient {
    private String failedURLS = "";
    private String succeededURLS = "";
    private String incorrectURLS = "";

    private boolean verifyUrl(String url) {
        String urlRegex = "^(http|https)://[-a-zA-Z0-9+&@#/%?=~_|,!:.;]*[-a-zA-Z0-9+@#/%=&_|]";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher m = pattern.matcher(url);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public String validateUrl(List<String> urls) throws Exception {

        for (String url : urls) {
            if (verifyUrl(url)) {

                try {
                    URL toUrl = new URL(url);
                    HttpURLConnection http = (HttpURLConnection) toUrl.openConnection();
                    http.setConnectTimeout(5000);
                    if (http.getResponseCode() == URLStatus.HTTP_OK.getStatusCode()) {
                        succeededURLS = succeededURLS + url + "\n";
                    } else {
                        failedURLS = failedURLS + url + "\n";
                    }

                    System.out.println(url + " " + URLStatus.getStatusMessageForStatusCode(http.getResponseCode()));

                } catch (Exception e) {
                    //System.out.print("For url- " + url + "" + e.getMessage());
                    System.out.println(url + " " + e);
                }

            } else {
                incorrectURLS += "\n" + url;
            }
        }

        return succeededURLS;
    }

    public boolean validUrl(String url) {

        if (verifyUrl(url)) {

            try {
                URL toUrl = new URL(url);
                HttpURLConnection http = (HttpURLConnection) toUrl.openConnection();
                http.connect();
              //  http.setConnectTimeout(3000);
           //     http.setReadTimeout(1000);

                if (http.getResponseCode() == URLStatus.HTTP_OK.getStatusCode()) {
                    return true;
                }

                //System.out.println(url + " " + URLStatus.getStatusMessageForStatusCode(http.getResponseCode()));

            } catch (Exception e) {
                //System.out.print("For url- " + url + "" + e.getMessage());
                //System.out.println(url + " " + e);
            }
        }
        return false;
    }
}
