package ua.mario.bot;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.Webhook;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.updatesreceivers.DefaultExceptionMapper;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Webhook to receive updates
 */
public class DefaultWebhook1 implements Webhook {
    private static final Logger log = LoggerFactory.getLogger(ua.mario.bot.DefaultWebhook1.class);
    private String keystoreServerFile;
    private String keystoreServerPwd;
    private String internalUrl;

    private final RestApi1 restApi;

    private WebhookBot bbot;

    public DefaultWebhook1() {        this.restApi = new RestApi1();    }

    public void setInternalUrl(String internalUrl) {
        this.internalUrl = internalUrl;
    }

    public void setKeyStore(String keyStore, String keyStorePassword) throws TelegramApiException {
        this.keystoreServerFile = keyStore;
        this.keystoreServerPwd = keyStorePassword;
        validateServerKeystoreFile(keyStore);
    }

    public void registerWebhook(WebhookBot callback) {
        restApi.registerCallback(callback);
        System.out.println("register");
        log.info("register");
        log.info(callback.toString());
        System.out.println(callback);
        bbot = callback;
    }

    public void startServer() throws TelegramApiException {
        ResourceConfig rc = new ResourceConfig();
        rc.register(restApi);
        System.out.println(this.restApi.toString());
        rc.register(JacksonFeature.class);
        rc.register(DefaultExceptionMapper.class);
        System.out.println(rc.getProperties().toString());
        log.info("server start");



        final HttpServer grizzlyServer;
        if (keystoreServerFile != null && keystoreServerPwd != null) {
            SSLContextConfigurator sslContext = new SSLContextConfigurator();

            // set up security context
            sslContext.setKeyStoreFile(keystoreServerFile); // contains server keypair
            sslContext.setKeyStorePass(keystoreServerPwd);

            grizzlyServer = GrizzlyHttpServerFactory.createHttpServer(getBaseURI(), rc, true,
                    new SSLEngineConfigurator(sslContext).setClientMode(false).setNeedClientAuth(false));
        } else {
            grizzlyServer = GrizzlyHttpServerFactory.createHttpServer(getBaseURI(), rc);
        }

        try {
            grizzlyServer.getServerConfiguration().addHttpHandler(
                            new HttpHandler() {
                                public void service(Request request, Response response) throws Exception {
                                    final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
                                    final String date = format.format(new Date(System.currentTimeMillis()));
                                    response.setContentType("text/plain");
                                    response.setContentLength(date.length());
                                    response.getWriter().write(date);
                                }
                            },"/time");
            grizzlyServer.getServerConfiguration().addHttpHandler(
                    new HttpHandler() {
                   //     @Override
                        public void service(Request request, Response response) throws Exception {
                            log.info(request.getQueryString());
                            log.info(request.getContext().toString());
                            System.err.println("oki toki");
                            final String rr = "otvet";
                            response.setContentType("text/plain");
                            response.setContentLength(rr.length());
                            response.getWriter().write(rr);
                        }
                    },"/callback/mario_it_bot" );
            grizzlyServer.start();
        } catch (IOException e) {
            throw new TelegramApiException("Error starting webhook server", e);
        }
    }

    private URI getBaseURI() {
        return URI.create(internalUrl);
    }

    private static void validateServerKeystoreFile(String keyStore) throws TelegramApiException {
        File file = new File(keyStore);
        if (!file.exists() || !file.canRead()) {
            throw new TelegramApiException("Can't find or access server keystore file.");
        }
    }
}

