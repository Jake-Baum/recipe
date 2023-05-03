package uk.jakebaum.recipe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class CommonsRequestLoggingFilterConfig {

  @Bean
  public CommonsRequestLoggingFilter requestLoggingFilter(@Value("${log.requests:false}") boolean logRequests) {
    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();

    if (logRequests) {
      loggingFilter.setIncludeClientInfo(true);
      loggingFilter.setIncludeQueryString(true);
      loggingFilter.setIncludePayload(true);
      loggingFilter.setIncludeHeaders(true);
      loggingFilter.setMaxPayloadLength(64000);
    }

    return loggingFilter;
  }

}
