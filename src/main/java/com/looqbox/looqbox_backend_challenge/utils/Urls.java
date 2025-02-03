package com.looqbox.looqbox_backend_challenge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import com.looqbox.looqbox_backend_challenge.LooqboxBackendChallengeApplication;

public class Urls {
    // Utils class to execute requests for external API's

    static final String CHARSET = "UTF-8";

    public static String getFrom(String url) throws IOException {
        return getFrom(url, "application/json");
    }

    public static String getFrom(String url, String contentType) throws IOException {
        return request(url, "GET", null, contentType);
    }

    public static final String readText(InputStream is, Charset charset) throws IOException {

        byte[] buffer = new byte[4096];
        StringBuilder builder = new StringBuilder(4096);
        int read = is.read(buffer);

        while (read > 0) {
            String item = new String(buffer, 0, read, charset);
            builder.append(item);
            read = is.read(buffer);
        }
        return builder.toString();

    }

    public static final String urlEncode(String input) {
        if (input == null || input.isEmpty())
            return "";
        else
            try {
                return URLEncoder.encode(input, "utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
    }

    private static String httpRequest(String url, String requestMethod, String postData,
            String contentType) throws IOException {
        URL authUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) authUrl.openConnection();

        con.setInstanceFollowRedirects(false);

        con.setRequestMethod(requestMethod);

        con.setRequestProperty("Content-Type", contentType);
        con.setRequestProperty("Accept", CHARSET);
        con.setRequestProperty("Accept", contentType);
        con.setRequestProperty("Application", LooqboxBackendChallengeApplication.class.getName());
        con.setDoOutput(true);

        if (postData != null && !postData.isEmpty()) {
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), Charset.forName(CHARSET));
            wr.write(postData.toString());
            wr.flush();
            wr.close();
        }

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), CHARSET));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException ioe) {
            InputStream errorStream = con.getErrorStream();
            if (errorStream != null) {

                String errorContent;
                try {
                    errorContent = readText(errorStream, Charset.forName(CHARSET));
                } catch (Exception e) {
                    throw ioe;
                }
                throw new RuntimeException(errorContent, ioe);
            } else
                throw ioe;
        }
    }

    private static String httpsRequest(String url, String requestMethod, String postData,
            String contentType) throws IOException {
        URL authUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) authUrl.openConnection();

        con.setInstanceFollowRedirects(true);
        con.setRequestMethod(requestMethod);

        con.setRequestProperty("Content-Type", contentType);
        con.setRequestProperty("Accept", CHARSET);
        con.setRequestProperty("Accept", contentType);
        con.setRequestProperty("Application", LooqboxBackendChallengeApplication.class.getName());
        con.setDoOutput(true);

        if (postData != null && !postData.isEmpty()) {
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), Charset.forName(CHARSET));
            osw.write(postData);
            osw.flush();
            osw.close();
        }

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), CHARSET));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException ioe) {
            InputStream errorStream = con.getErrorStream();
            if (errorStream != null) {

                String errorContent;
                try {
                    errorContent = readText(errorStream, Charset.forName(CHARSET));
                } catch (Exception e) {
                    throw ioe;
                }
                throw new RuntimeException(errorContent, ioe);
            } else
                throw ioe;
        }
    }

    // _____________________________________________________________________________________________
    private static String request(String url, String requestMethod, String postData,
            String contentType) throws IOException {
        if (url.toLowerCase().startsWith("https"))
            return httpsRequest(url, requestMethod, postData, contentType);
        else
            return httpRequest(url, requestMethod, postData, contentType);
    }

}
