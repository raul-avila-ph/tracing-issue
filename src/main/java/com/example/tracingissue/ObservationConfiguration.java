package com.example.tracingissue;

import io.micrometer.observation.GlobalObservationConvention;
import io.micrometer.observation.ObservationFilter;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationPredicate;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationRegistryCustomizer;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ObservationConfiguration {

    @Bean
    @SuppressWarnings("unchecked")
    ObservationRegistry observationRegistry(
            ObjectProvider<ObservationRegistryCustomizer<?>> observationRegistryCustomizers,
            ObjectProvider<ObservationPredicate> observationPredicates,
            ObjectProvider<GlobalObservationConvention<?>> observationConventions,
            ObjectProvider<ObservationHandler<?>> observationHandlers,
            ObjectProvider<ObservationFilter> observationFilters
    ) {
        final var registry = ObservationRegistry.create();

        observationPredicates.orderedStream().forEach(registry.observationConfig()::observationPredicate);

        observationConventions.orderedStream().forEach(registry.observationConfig()::observationConvention);

        // We can't configure this as it's done in ObservationRegistryConfigurer, because ObservationHandlerGrouping
        // is package private
        observationHandlers.orderedStream().forEach(registry.observationConfig()::observationHandler);

        observationFilters.orderedStream().forEach(registry.observationConfig()::observationFilter);

        LambdaSafe.callbacks(ObservationRegistryCustomizer.class, asOrderedList(observationRegistryCustomizers), registry)
                .withLogger(ObservationConfiguration.class)
                .invoke((customizer) -> customizer.customize(registry));

        return registry;
    }

    private <T> List<T> asOrderedList(ObjectProvider<T> provider) {
        return provider.orderedStream().toList();
    }
}
