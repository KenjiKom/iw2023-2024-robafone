package iw20232024robafone.backend.repository;

import iw20232024robafone.backend.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepository extends JpaRepository<Sms, Long> {
}
