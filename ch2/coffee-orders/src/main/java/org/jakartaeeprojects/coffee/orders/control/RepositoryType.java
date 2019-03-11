package org.jakartaeeprojects.coffee.orders.control;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({FIELD, TYPE, METHOD})
public @interface RepositoryType {

    Type value();

    enum Type {
        JPA, IN_MEMORY;
    }

}