package de.akquinet.cdi.memoryleak.rest.handler;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Documented
@Retention( RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})

public @interface MessageType {

    String type() default "none";
}
