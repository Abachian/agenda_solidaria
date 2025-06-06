package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.Image;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<Image> getAllImages();
    Optional<Image> getImageById(Integer id);
    Image createImage(Image image);
    Image updateImage(Integer id, Image image);
    void deleteImage(Integer id);
} 