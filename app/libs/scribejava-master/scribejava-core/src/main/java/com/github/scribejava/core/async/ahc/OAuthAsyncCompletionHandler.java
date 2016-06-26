package com.github.scribejava.core.async.ahc;

import com.github.scribejava.core.model.OAuthAsyncRequestCallback;
import com.github.scribejava.core.model.OAuthRequestAsync;
import com.github.scribejava.core.model.Response;
import io.netty.handler.codec.http.HttpHeaders;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.asynchttpclient.AsyncCompletionHandler;

public class OAuthAsyncCompletionHandler<T> extends AsyncCompletionHandler<T> {

    private final OAuthAsyncRequestCallback<T> callback;
    private final OAuthRequestAsync.ResponseConverter<T> converter;

    public OAuthAsyncCompletionHandler(OAuthAsyncRequestCallback<T> callback,
            OAuthRequestAsync.ResponseConverter<T> converter) {
        this.callback = callback;
        this.converter = converter;
    }

    @Override
    public T onCompleted(org.asynchttpclient.Response ahcResponse) throws IOException {
        final HttpHeaders map = ahcResponse.getHeaders();
        final Map<String, String> headersMap = new HashMap<>();
        for (Map.Entry<String, String> header : map) {
            headersMap.put(header.getKey(), header.getValue());
        }
        final Response response = new Response(ahcResponse.getStatusCode(), ahcResponse.getStatusText(), headersMap,
                ahcResponse.getResponseBody(), ahcResponse.getResponseBodyAsStream());

        @SuppressWarnings("unchecked")
        final T t = converter == null ? (T) response : converter.convert(response);
        if (callback != null) {
            callback.onCompleted(t);
        }
        return t;
    }

    @Override
    public void onThrowable(Throwable t) {
        if (callback != null) {
            callback.onThrowable(t);
        }
    }
};
