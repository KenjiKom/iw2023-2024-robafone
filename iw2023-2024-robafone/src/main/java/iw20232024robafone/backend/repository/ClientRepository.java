package iw20232024robafone.backend.repository;

import iw20232024robafone.backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
