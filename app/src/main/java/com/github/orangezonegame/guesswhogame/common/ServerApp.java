package com.github.orangezonegame.guesswhogame.common;

import android.app.Application;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Me on 7/1/18.
 */

public class ServerApp extends Application {

    private Server mServer = new Server();

    public void send(String event, Map json) {
        mServer.send(event, json);
    }

    public void attachListener(String event, Emitter.Listener listener) {
        mServer.attachListener(event, listener);
    }

    public void detachListener(String event, Emitter.Listener listener) {
        mServer.detachListener(event, listener);
    }

    public Socket getSocket() {
        return mServer.getSocket();
    }
}