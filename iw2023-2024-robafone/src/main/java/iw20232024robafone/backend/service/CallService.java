package iw20232024robafone.backend.service;

import iw20232024robafone.backend.entity.Call;
import iw20232024robafone.backend.entity.Invoice;
import iw20232024robafone.backend.repository.CallRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CallService {
    private static final Logger LOGGER = Logger.getLogger(CallService.class.getName());
    private final CallRepository callRepository;

    public CallService(CallRepository callRepository) { this.callRepository = callRepository; }

    public List<Call> findAll() { return callRepository.findAll(); }

    public Optional<Call> findCall(Call call) { return callRepository.findById(call.getId()); }

    public long count() {
        return callRepository.count();
    }

    public void delete(Call call) {
        callRepository.delete(call);
    }

    public void save(Call call) {
        if (call == null) {
            LOGGER.log(Level.SEVERE, "Call is null");
            return;
        }
        callRepository.save(call);
    }

    public List<Call> findCallByUser(String username){
        List<Call> complaintsOfUser = new ArrayList();
        List<Call> complaintList = callRepository.findAll();
        for(int i = 0; i< complaintList.size(); i++){
            if(complaintList.get(i).getClient().getUsername().equals(username)){
                complaintsOfUser.add(complaintList.get(i));
            }
        }
        return complaintsOfUser;
    }
}
