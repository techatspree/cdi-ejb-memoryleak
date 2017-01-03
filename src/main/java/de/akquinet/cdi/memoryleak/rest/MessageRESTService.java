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

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/message")
public class MessageRESTService {

    @Inject
    private ServiceDispatcher dispatcher;

    @GET
    @Path( "/messageWithLeak" )
    @Produces( MediaType.TEXT_PLAIN)
    public String getMessageWithLeak(@QueryParam("type") String type, @QueryParam("text") String message) {
        return dispatcher.handleMessageWithMemoryLeak(message, type);
    }

    @GET
    @Path( "/messageLeakFixedCDI10" )
    @Produces( MediaType.TEXT_PLAIN)
    public String getMessageLeakFixedCDI10(@QueryParam("type") String type, @QueryParam("text") String message) {
        return dispatcher.handleMessageLeakFixedCDI10( message, type);
    }

    @GET
    @Path( "/messageLeakFixedCDI11" )
    @Produces( MediaType.TEXT_PLAIN)
    public String getMessageLeakFixedCD11(@QueryParam("type") String type, @QueryParam("text") String message) {
        return dispatcher.handleMessageLeakFixedCDI11( message, type);
    }
}
