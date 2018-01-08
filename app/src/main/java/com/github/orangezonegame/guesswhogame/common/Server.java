package com.github.orangezonegame.guesswhogame.common;

import android.util.Log;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
/**
 * Created by Me on 31/12/17.
 */

public class Server {
    private Socket mSocket;

    public Socket getSocket() {
        return mSocket;
    }

    public Server() {
        try {
            mSocket = IO.socket(Constants.SERVER_URL);
            Log.i("Server", "Connected");

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mSocket.connect();
    }

    public void send(String event, Map json) {
        JSONObject data = new JSONObject(json) ;
        mSocket.emit(event, data);
    }

    public void attachListener(String event, Emitter.Listener listener) {
        mSocket.on(event, listener);
    }

    public void detachListener(String event, Emitter.Listener listener) {
        mSocket.off(event, listener);
    }
}
