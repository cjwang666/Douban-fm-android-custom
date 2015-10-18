package com.zzxhdzj.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlApiResponse extends ApiResponse {
    private Document document;

    public XmlApiResponse(int httpCode) {
        super(httpCode);
    }

    public XmlApiResponse(int httpCode, Map<String, List<String>> headers) {
        super(httpCode, headers);
    }

    @Override
    public void consumeResponse(InputStream responseBody) throws IOException, SAXException, ParserConfigurationException {
        if (isSuccess()) {
            document = Xmls.getDocument(responseBody);
        }
    }

    public Document getResponseDocument() {
        return document;
    }
}
