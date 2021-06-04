package com.example.helpworx.QueryDslConfig;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomPostgreSQLDialect extends PostgreSQL10Dialect {
    public CustomPostgreSQLDialect() {
        super();
        this.registerFunction("to_char", new StandardSQLFunction("to_char", StandardBasicTypes.STRING));
    }
}
