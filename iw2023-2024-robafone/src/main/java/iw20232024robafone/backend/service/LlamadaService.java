package iw20232024robafone.backend.service;

import iw20232024robafone.backend.entity.Llamada;
import iw20232024robafone.backend.repository.LlamadaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LlamadaService {
    private static final Logger LOGGER = Logger.getLogger(LlamadaService.class.getName());
    private final LlamadaRepository llamadaRepository;

    public LlamadaService(LlamadaRepository llamadaRepository) { this.llamadaRepository = llamadaRepository; }

    public List<Llamada> findAll() { return llamadaRepository.findAll(); }

    public Optional<Llamada> findCall(Llamada llamada) { return llamadaRepository.findById(llamada.getId()); }

    public long count() {
        return llamadaRepository.count();
    }

    public void delete(Llamada llamada) {
        llamadaRepository.delete(llamada);
    }

    public void save(Llamada llamada) {
        if (llamada == null) {
            LOGGER.log(Level.SEVERE, "Llamada is null");
            return;
        }
        llamadaRepository.save(llamada);
    }

    public List<Llamada> findCallByUser(String username){
        List<Llamada> complaintsOfUser = new ArrayList();
        List<Llamada> complaintList = llamadaRepository.findAll();
        for(int i = 0; i< complaintList.size(); i++){
            if(complaintList.get(i).getClient().getUsername().equals(username)){
                complaintsOfUser.add(complaintList.get(i));
            }
        }
        return complaintsOfUser;
    }
}
