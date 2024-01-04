package iw20232024robafone.backend.repository;

import iw20232024robafone.backend.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRepository extends JpaRepository<Call, Long> {
}
