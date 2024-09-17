package me.ankanroychowdhury.scm.configuartions;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Component
public class GmailConfiguration implements CommandLineRunner {

    private static final String APPLICATION_NAME = "GMAIL API Spring";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = GmailConfiguration.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if(in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline").build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private String getContent(Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        getPlainTextFromMessageParts(message.getPayload().getParts(), stringBuilder);
        if (stringBuilder.isEmpty()) {
            stringBuilder.append(message.getPayload().getBody().getData());
        }
        byte[] bodyBytes = Base64.decodeBase64(stringBuilder.toString());
        return new String(bodyBytes, StandardCharsets.UTF_8);
    }

    private void getPlainTextFromMessageParts(List<MessagePart> messageParts, StringBuilder stringBuilder) {
        if (messageParts != null) {
            for (MessagePart messagePart : messageParts) {
                if (messagePart.getMimeType().equals("text/plain")) {
                    stringBuilder.append(messagePart.getBody().getData());
                }
                if (messagePart.getParts() != null) {
                    getPlainTextFromMessageParts(messagePart.getParts(), stringBuilder);
                }
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
//        try {
//            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//                    .setApplicationName(APPLICATION_NAME)
//                    .build();
//            String user = "me";
//            ListLabelsResponse listResponse = service.users().labels().list(user).execute();
//            List<Label> labels = listResponse.getLabels();
//            if (labels.isEmpty()) {
//                System.out.println("No labels found.");
//            } else {
//                System.out.println("Labels:");
//                for (Label label : labels) {
//                    System.out.printf("- %s\n", label.getName());
//                }
//            }
//
//            ListMessagesResponse listMessagesResponse = service.users().messages().list(user).setQ("label:project").execute();
//            List<Message> messages = listMessagesResponse.getMessages();
////            for (Message message : messages) {
////                message = service.users().messages().get(user,message.getId()).setFormat("FULL").execute();
////                MessagePart messagePart = message.getPayload();
////                String messageContent = "";
////                String subject = "";
////                if(messagePart != null){
////                    List<MessagePartHeader> headers = messagePart.getHeaders();
////                    for(MessagePartHeader header : headers){
////                        if(header.getName().equals("Subject")){
////                            subject = header.getValue().trim();
////                            break;
////                        }
////                    }
////                }
////                messageContent = getContent(message);
////                System.out.println("Subject: " + subject);
////                System.out.println("Content: " + messageContent);
////            }
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new Exception(e);
//        }
    }
}
