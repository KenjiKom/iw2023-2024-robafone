package iw20232024robafone.backend.service;

import iw20232024robafone.backend.entity.Servicio;
import iw20232024robafone.backend.entity.Sms;
import iw20232024robafone.backend.repository.ServicioRepository;
import iw20232024robafone.backend.repository.SmsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SmsService {
    private static final Logger LOGGER = Logger.getLogger(SmsService.class.getName());
    private final SmsRepository smsRepository;

    public SmsService(SmsRepository smsRepository) { this.smsRepository = smsRepository; }

    public List<Sms> findAll() { return smsRepository.findAll(); }

    public Optional<Sms> findSms(Sms sms) {return smsRepository.findById(sms.getId()); }

    public long count() {
        return smsRepository.count();
    }

    public void delete(Sms sms) {
        smsRepository.delete(sms);
    }

    public void save(Sms sms) {
        if (sms == null) {
            LOGGER.log(Level.SEVERE, "Sms is null");
            return;
        }
        smsRepository.save(sms);
    }
}
