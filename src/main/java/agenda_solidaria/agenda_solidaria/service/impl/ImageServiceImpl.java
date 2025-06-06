package agenda_solidaria.agenda_solidaria.service.impl;

import agenda_solidaria.agenda_solidaria.model.Image;
import agenda_solidaria.agenda_solidaria.repository.ImageRepository;
import agenda_solidaria.agenda_solidaria.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private final ImageRepository imageRepository;

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> getImageById(Integer id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Image updateImage(Integer id, Image image) {
        if (imageRepository.existsById(id)) {
            image.setId(id);
            return imageRepository.save(image);
        }
        throw new RuntimeException("Image not found with id: " + id);
    }

    @Override
    public void deleteImage(Integer id) {
        imageRepository.deleteById(id);
    }
} 