package agenda_solidaria.service.impl;

import agenda_solidaria.model.Postulate;
import agenda_solidaria.repository.PostulateRepository;
import agenda_solidaria.service.PostulateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostulateServiceImpl implements PostulateService {

    @Autowired
    private PostulateRepository postulateRepository;

    @Override
    public List<Postulate> getAllPostulates() {
        return postulateRepository.findAll();
    }

    @Override
    public Optional<Postulate> getPostulateById(Long id) {
        return postulateRepository.findById(id);
    }

    @Override
    public Postulate createPostulate(Postulate postulate) {
        return postulateRepository.save(postulate);
    }

    @Override
    public Postulate updatePostulate(Long id, Postulate postulate) {
        Optional<Postulate> postulateOptional = postulateRepository.findById(id);
        if (postulateOptional.isPresent()) {
            Postulate updatedPostulate = postulateOptional.get();
            if (postulate.getVolunteer() != null) updatedPostulate.setVolunteer(postulate.getVolunteer());
            if (postulate.getNeed() != null) updatedPostulate.setNeed(postulate.getNeed());
            if (postulate.getPostulated_date() != null) updatedPostulate.setPostulated_date(postulate.getPostulated_date());
            if (updatedPostulate.getAcepted() != postulate.getAcepted()) updatedPostulate.setAcepted((postulate.getAcepted()));
            return postulateRepository.save(updatedPostulate);
        }
        throw new RuntimeException("Postulate not found with id: " + id);
    }

    @Override
    public void deletePostulate(Long id) {
        postulateRepository.deleteById(id);
    }

    @Override
    public List<Postulate> getPostulatesByVolunteer(Long volunteerId) {
        return postulateRepository.findByVolunteerId(volunteerId);
    }

    @Override
    public List<Postulate> getPostulatesByNeed(Long needId) {
        return postulateRepository.findByNeedId(needId);
    }

    @Override
    public Postulate enablePostulate(Long id) {
        Postulate postulate = postulateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Postulate not found with id: " + id));
        postulate.setAcepted(true);
        return postulateRepository.save(postulate);
    }

    @Override
    public Postulate disablePostulate(Long id) {
        Postulate postulate = postulateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Postulate not found with id: " + id));
        postulate.setAcepted(false);
        return postulateRepository.save(postulate);
    }
} 