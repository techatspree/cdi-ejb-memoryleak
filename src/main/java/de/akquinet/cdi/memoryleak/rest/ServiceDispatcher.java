package de.akquinet.cdi.memoryleak.rest;

import de.akquinet.cdi.memoryleak.rest.handler.Handler;
import de.akquinet.cdi.memoryleak.rest.handler.MessageType;

import javax.ejb.Stateless;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import java.util.Set;

@Stateless
public class ServiceDispatcher {

    @Inject
    private Instance<Handler> handlers;

    @Inject
    private BeanManager beanManager;

    /**
     * Using approach witch will lead to memory leaks with CDI 1.0
     */
    public String handleMessageWithMemoryLeak(final String message, final String messageType) {
        Instance<Handler> bean = handlers.select( new MessageTypeQualifier() {
            @Override
            public String type() {
                return messageType;
            }
        });
        return bean.get().handle( message );
    }

    /**
     * Using different approach with CDI 1.0 to correctly doing CDI resource management
     */
    public String handleMessageLeakFixedCDI10( final String message, final String messageType) {
        Set<Bean<?>> beans = beanManager.getBeans( Handler.class, new MessageTypeQualifier() {
            @Override
            public String type() {
                return messageType;
            }
        });
        Bean<Handler> bean = (Bean<Handler>) beans.iterator().next();
        CreationalContext<?> creationalContext = beanManager.createCreationalContext( bean );
        Handler handler = (Handler) beanManager.getReference( bean, Handler.class, creationalContext);
        String result = handler.handle( message );
        creationalContext.release();

        return result;
    }

    /**
     * Using newly introduced #destroy() Method with CDI 1.1 to prevent memory leak
     */
    public String handleMessageLeakFixedCDI11( final String message, final String messageType) {
        Instance<Handler> bean = handlers.select( new MessageTypeQualifier() {
            @Override
            public String type() {
                return messageType;
            }
        });
        Handler handler = bean.get();
        String result = handler.handle( message );
        bean.destroy(handler);
        return result;
    }


    private abstract class MessageTypeQualifier extends AnnotationLiteral<MessageType> implements MessageType {}
}
