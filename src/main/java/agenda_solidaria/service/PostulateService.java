package agenda_solidaria.service;

import java.util.List;
import java.util.Optional;

import agenda_solidaria.model.Postulate;

public interface PostulateService {
    List<Postulate> getAllPostulates();
    Optional<Postulate> getPostulateById(Long id);
    Postulate createPostulate(Postulate postulate);
    Postulate updatePostulate(Long id, Postulate postulate);
    void deletePostulate(Long id);
    List<Postulate> getPostulatesByVolunteer(Long volunteerId);
    List<Postulate> getPostulatesByNeed(Long needId);
    Postulate enablePostulate(Long id);
    Postulate disablePostulate(Long id);
} 