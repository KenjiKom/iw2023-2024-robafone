package iw20232024robafone.backend.service;

import iw20232024robafone.backend.entity.Invoice;
import iw20232024robafone.backend.entity.Servicio;
import iw20232024robafone.backend.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InvoiceService {
    private static final Logger LOGGER = Logger.getLogger(InvoiceService.class.getName());
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) { this.invoiceRepository = invoiceRepository; }

    public List<Invoice> findAll() { return invoiceRepository.findAll(); }

    public Optional<Invoice> findInvoice(Invoice invoice) {return invoiceRepository.findById(invoice.getId()); }

    public long count() {
        return invoiceRepository.count();
    }

    public void delete(Invoice invoice) {
        invoiceRepository.delete(invoice);
    }

    public void save(Invoice invoice) {
        if (invoice == null) {
            LOGGER.log(Level.SEVERE, "Invoice is null");
            return;
        }
        invoiceRepository.save(invoice);
    }

    public List<Invoice> findServiciotByUser(String username){
        List<Invoice> complaintsOfUser = new ArrayList();
        List<Invoice> complaintList = invoiceRepository.findAll();
        for(int i = 0; i< complaintList.size(); i++){
            if(complaintList.get(i).getClient().getUsername().equals(username)){
                complaintsOfUser.add(complaintList.get(i));
            }
        }
        return complaintsOfUser;
    }
}
