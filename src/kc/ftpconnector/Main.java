package kc.ftpconnector;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static final String ADDRESS = "ftp.example.com";

    private static void doWork(FTPClient ftp) throws IOException {
/*        String remote = "imageIDs.json";
        InputStream inputStream = ftp.retrieveFileStream(remote);
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            stringBuilder.append(inputStr);
        }
        inputStream.close();
        streamReader.close();
        log(ADDRESS, "Read " + remote);
        JSONArray images = new JSONArray(stringBuilder.toString());
        log(images);*/
    }

    public static void main(String[] args) {
        long ms = -1;
        long initTime = System.currentTimeMillis();
        
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(ADDRESS);
            log(ADDRESS, "Connecting...");
            log(ADDRESS, ftp.getReplyString());
            int reply = ftp.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                error(ADDRESS,"No connection");
            } else {
                log(ADDRESS, "Connected");
                boolean loggedIn = ftp.login("androidapp", "December12345!");
                if (!loggedIn) {
                    error(ADDRESS, "Couldn't login");
                } else {
                    log(ADDRESS, "Logged in");

                    log(Arrays.toString(ftp.listNames()));

                    boolean loggedOut = ftp.logout();
                    if (!loggedOut) {
                        error(ADDRESS, "Couldn't logout");
                    } else {
                        log(ADDRESS, "Logged out");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                    log(ADDRESS, "Disconnected");
                    ms = System.currentTimeMillis() - initTime;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log(ADDRESS, ms);
    }

    private static void log(Object... msgs) {
        for (Object msg : msgs) {
            System.out.print(String.valueOf(msg) + " ");
        }
        System.out.println();
    }

    private static void error(Object... errs) {
        System.out.print("> ");
        for (Object err : errs) {
            System.out.print(String.valueOf(err) + " ");
        }
        System.out.println();
    }
}
