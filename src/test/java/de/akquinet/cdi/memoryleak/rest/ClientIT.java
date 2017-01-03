/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.akquinet.cdi.memoryleak.rest;

import de.akquinet.cdi.memoryleak.rest.categories.MemoryLeak;
import de.akquinet.cdi.memoryleak.rest.categories.MemoryLeakFixCDI10;
import de.akquinet.cdi.memoryleak.rest.categories.MemoryLeakFixCDI11;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

public class ClientIT {

    private static final String REST_MEMORYLEAK_TARGET_URL = "http://localhost:8080/cdi-ejb-memoryleak/rest/message/messageWithLeak";
    private static final String REST_FIX_CDI10_TARGET_URL = "http://localhost:8080/cdi-ejb-memoryleak/rest/message/messageLeakFixedCDI10";
    private static final String REST_FIX_CDI11_TARGET_URL = "http://localhost:8080/cdi-ejb-memoryleak/rest/message/messageLeakFixedCDI11";

    private Logger log = Logger.getLogger(ClientIT.class.getName());

    @Category(MemoryLeak.class)
    @Test
    public void memoryLeakTest() {
        log.info("Running test with memory leak");
        for ( int i = 0; i < 100000; i++ ) {
            boolean even = (i % 2 == 0);
            callEndpoint((even) ? "foo" : "bar", REST_MEMORYLEAK_TARGET_URL);
        }
    }


    @Category(MemoryLeakFixCDI10.class)
    @Test
    public void memoryLeakFixCDI10Test() {
        log.info("Running test with CDI 1.0 Fix");
        for ( int i = 0; i < 100000; i++ ) {
            boolean even = (i % 2 == 0);
            callEndpoint((even) ? "foo" : "bar", REST_FIX_CDI10_TARGET_URL);
        }
    }

    @Category(MemoryLeakFixCDI11.class)
    @Test
    public void memoryLeakFixCDI11Test() {
        log.info("Running test with CDI 1.1 Fix");
        for ( int i = 0; i < 100000; i++ ) {
            boolean even = (i % 2 == 0);
            callEndpoint((even) ? "foo" : "bar", REST_FIX_CDI11_TARGET_URL);
        }
    }

    private void callEndpoint(String type, String targetURL) {
        WebTarget webTarget = ClientBuilder.newClient().target( targetURL );
        webTarget = webTarget.queryParam("text", "Hello World").queryParam( "type", type);
        webTarget.request( MediaType.TEXT_PLAIN).get( String.class);
    }
}
