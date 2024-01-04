package iw20232024robafone.backend.service;

import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ClientService {
    private static final Logger LOGGER = Logger.getLogger(ClientService.class.getName());
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) { this.clientRepository = clientRepository; }

    public List<Client> findAll() { return clientRepository.findAll(); }

    public Optional<Client> findClient(Client client) { return clientRepository.findById(client.getId()); }

    public long count() {
        return clientRepository.count();
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public void save(Client client) {
        if (client == null) {
            LOGGER.log(Level.SEVERE, "Client is null");
            return;
        }
        clientRepository.save(client);
    }
}
