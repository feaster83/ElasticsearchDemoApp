package demoapp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Locale;

@Component
public final class Exceptions {

    public static String getMessage(ExceptionMessage msg, Object... args) {
        return String.format(msg.getMsg(), args);
    };

    public static enum ExceptionMessage {
        REPOSITORY_ADD_ITEM_FAILED("exception.repository.add_item_failed"),
        REPOSITORY_ADD_ITEM_FAILED_UNEXPECTED("exception.repository.add_item_failed_unexpected"),
        REPOSITORY_RESOLVE_ITEMS_FAILED_UNEXPECTED("exception.repository.resolve_items_failed_unexpected"),
        SERVICE_ADD_NOT_ALLOWED_NOT_NEW("exception.service.not_new_object"),
        SERVICE_INVALID_ARGUMENT("exception.service.invalid_argument");


        @Getter private String id;
        @Getter @Setter private String msg;
        ExceptionMessage(String id) {
            this.id = id;
            this.msg = "Exception id: " + id;
        }
    }

    @Component
    public static class ExceptionMessageConfigurator {
        final Locale locale = LocaleContextHolder.getLocale();

        @Autowired
        private MessageSource messageSource;

        @PostConstruct
        public void initializeEnum() {
            Arrays.stream(ExceptionMessage.values()).parallel().forEach(item -> loadMessage(item));
        }

        private void loadMessage(ExceptionMessage item) {
            item.setMsg(messageSource.getMessage(item.getId(), new Object[0], locale));
        }
    }

}
