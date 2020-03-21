package de.hilfstelefon.backend.repository;

import de.hilfstelefon.backend.domain.TwilioCall;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TwilioCallRepository  implements PanacheRepository<TwilioCall> {
}
