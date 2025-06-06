package agenda_solidaria.agenda_solidaria.service.impl;

import agenda_solidaria.agenda_solidaria.model.Postulate;
import agenda_solidaria.agenda_solidaria.repository.PostulateRepository;
import agenda_solidaria.agenda_solidaria.service.PostulateService;
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
    public Optional<Postulate> getPostulateById(Integer id) {
        return postulateRepository.findById(id);
    }

    @Override
    public Postulate createPostulate(Postulate postulate) {
        return postulateRepository.save(postulate);
    }

    @Override
    public Postulate updatePostulate(Integer id, Postulate postulate) {
        if (postulateRepository.existsById(id)) {
            postulate.setId(id);
            return postulateRepository.save(postulate);
        }
        throw new RuntimeException("Postulate not found with id: " + id);
    }

    @Override
    public void deletePostulate(Integer id) {
        postulateRepository.deleteById(id);
    }

    @Override
    public List<Postulate> getPostulatesByVolunteer(Integer volunteerId) {
        return postulateRepository.findByVolunteerId(volunteerId);
    }

    @Override
    public List<Postulate> getPostulatesByNeed(Integer needId) {
        return postulateRepository.findByNeedId(needId);
    }

    @Override
    public Postulate enablePostulate(Integer id) {
        Postulate postulate = postulateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Postulate not found with id: " + id));
        postulate.setEnabled(true);
        return postulateRepository.save(postulate);
    }

    @Override
    public Postulate disablePostulate(Integer id) {
        Postulate postulate = postulateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Postulate not found with id: " + id));
        postulate.setEnabled(false);
        return postulateRepository.save(postulate);
    }
} 