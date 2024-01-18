package iw20232024robafone.backend.repository;

import iw20232024robafone.backend.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import iw20232024robafone.backend.entity.Tarifa;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
}
