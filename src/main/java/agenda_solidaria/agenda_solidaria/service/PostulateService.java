package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.Postulate;
import java.util.List;
import java.util.Optional;

public interface PostulateService {
    List<Postulate> getAllPostulates();
    Optional<Postulate> getPostulateById(Integer id);
    Postulate createPostulate(Postulate postulate);
    Postulate updatePostulate(Integer id, Postulate postulate);
    void deletePostulate(Integer id);
} 