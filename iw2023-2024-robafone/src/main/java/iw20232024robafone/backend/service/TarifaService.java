package iw20232024robafone.backend.service;

import iw20232024robafone.backend.entity.Complaint;
import iw20232024robafone.backend.entity.Sms;
import iw20232024robafone.backend.entity.Tarifa;
import iw20232024robafone.backend.repository.ComplaintRepository;
import iw20232024robafone.backend.repository.TarifaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TarifaService {
    private static final Logger LOGGER = Logger.getLogger(TarifaService.class.getName());

    private final TarifaRepository tarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository) { this.tarifaRepository = tarifaRepository; }

    public List<Tarifa> findAll() { return tarifaRepository.findAll(); }

    public Optional<Tarifa> findSms(Tarifa tarifa) {return tarifaRepository.findById(tarifa.getId()); }


    public long count() {
        return tarifaRepository.count();
    }

    public void delete(Tarifa tarifa) {
        tarifaRepository.delete(tarifa);
    }

    public void save(Tarifa tarifa) {
        if (tarifa == null) {
            LOGGER.log(Level.SEVERE, "Complaint is null");
            return;
        }
        tarifaRepository.save(tarifa);
    }

    public List<Tarifa> findTarifaByType(String tipo){
        List<Tarifa> allTarifas = tarifaRepository.findAll();
        List<Tarifa> filteredTarifas = new ArrayList<>();

        for(int i = 0; i< allTarifas.size(); i++){

            if(allTarifas.get(i).getTipo() == tipo){
                filteredTarifas.add(allTarifas.get(i));
            }

        }

        return filteredTarifas;

    }
}
