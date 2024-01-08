package iw20232024robafone.backend.service;

import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.entity.Complaint;
import iw20232024robafone.backend.entity.Servicio;
import iw20232024robafone.backend.repository.ServicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
@Transactional
@Service
public class ServicioService {
    private static final Logger LOGGER = Logger.getLogger(ServicioService.class.getName());
    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) { this.servicioRepository = servicioRepository; }

    public List<Servicio> findAll() { return servicioRepository.findAll(); }

    public Optional<Servicio> findServicio(Servicio servicio) {return servicioRepository.findById(servicio.getId()); }

    public long count() {
        return servicioRepository.count();
    }

    public void delete(Servicio servicio) {
        servicioRepository.delete(servicio);
    }

    public void save(Servicio servicio) {
        if (servicio == null) {
            LOGGER.log(Level.SEVERE, "Servicio is null");
            return;
        }
        servicioRepository.save(servicio);
    }

    public List<Servicio> findServiciotByUser(String username){
        List<Servicio> complaintsOfUser = new ArrayList();
        List<Servicio> complaintList = servicioRepository.findAll();
        for(int i = 0; i< complaintList.size(); i++){
            if(complaintList.get(i).getClient().getUsername().equals(username)){
                complaintsOfUser.add(complaintList.get(i));
            }
        }
        return complaintsOfUser;
    }
}
