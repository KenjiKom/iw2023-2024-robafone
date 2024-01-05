package iw20232024robafone.backend.service;

import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.entity.Complaint;
import iw20232024robafone.backend.repository.ClientRepository;
import iw20232024robafone.backend.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ComplaintService {
    private static final Logger LOGGER = Logger.getLogger(ComplaintService.class.getName());

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) { this.complaintRepository = complaintRepository; }

    public List<Complaint> findAll() { return complaintRepository.findAll(); }

    public Optional<Complaint> findComplaint(Complaint complaint) {return complaintRepository.findById(complaint.getId()); }

    public List<Complaint> findComplaintByUser(String username){
        List<Complaint> complaintsOfUser = new ArrayList();
        List<Complaint> complaintList = complaintRepository.findAll();
        for(int i = 0; i< complaintList.size(); i++){
            if(complaintList.get(i).getClient().getUsername().equals(username)){
                complaintsOfUser.add(complaintList.get(i));
            }
        }
        return complaintsOfUser;
    }

    public long count() {
        return complaintRepository.count();
    }

    public void delete(Complaint complaint) {
        complaintRepository.delete(complaint);
    }

    public void save(Complaint complaint) {
        if (complaint == null) {
            LOGGER.log(Level.SEVERE, "Complaint is null");
            return;
        }
        complaintRepository.save(complaint);
    }
}
