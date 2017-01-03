package de.akquinet.cdi.memoryleak.rest.handler;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

@Stateless
@Default
@MessageType(type = "foo")
public class FooHandler implements Handler{

    @Override
    public String
    handle( String message ) {
        String result = "handling a FOO with message:" + message;
        System.out.println( result );

        return result;
    }
}
