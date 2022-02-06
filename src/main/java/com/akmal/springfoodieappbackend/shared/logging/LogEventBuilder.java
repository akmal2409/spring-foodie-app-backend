package com.akmal.springfoodieappbackend.shared.logging;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.LinkedList;
import java.util.List;
import net.logstash.logback.argument.StructuredArgument;
import org.springframework.util.StringUtils;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 05/02/2022 - 19:53
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public class LogEventBuilder {
  private String type;
  private String component;
  private String operation;
  private String status;
  private String took;

  private LogEventBuilder(Class<?> clazz, String method) {
    this.component = String.format("%s#%s", clazz.getName(), method);
  }

  public static LogEventBuilder component(Class<?> clazz, String method) {
    return new LogEventBuilder(clazz, method);
  }

  public LogEventBuilder type(String type) {
    this.type = type;
    return this;
  }

  public LogEventBuilder operation(String operation) {
    this.operation = operation;
    return this;
  }

  public LogEventBuilder status(String status) {
    this.status = status;
    return this;
  }

  public LogEventBuilder took(String took) {
    this.took = took;
    return this;
  }

  @SuppressWarnings("ConstantConditions")
  public StructuredArgument[] kvs() {
    final List<StructuredArgument> args = new LinkedList<>();

    args.add(kv("component", this.component));

    if (StringUtils.hasText(this.operation)) {
      args.add(kv("operation", this.operation));
    }

    if (StringUtils.hasText(this.type)) {
      args.add(kv("type", this.type));
    }

    if (StringUtils.hasText(this.status)) {
      args.add(kv("status", this.status));
    }

    if (StringUtils.hasText(this.took)) {
      args.add(kv("took", this.took));
    }

    return (StructuredArgument[]) args.toArray();
  }
}
