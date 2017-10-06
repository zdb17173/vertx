package org.fran.vertx.core.parser;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;

/**
 * Created by fran on 2017/10/6.
 */
public class RecordParserTest {
    public static void main(String[] args){

        final RecordParser parser = RecordParser.newDelimited("\n", h -> {
            System.out.println(h.toString());
        });

        parser.handle(Buffer.buffer("HELLO\nHOW ARE Y"));
        parser.handle(Buffer.buffer("OU?\nI AM"));
        parser.handle(Buffer.buffer("DOING OK"));
        parser.handle(Buffer.buffer("\n"));

        RecordParser.newFixed(4, h -> {
            System.out.println(h.toString());
        });
    }
}