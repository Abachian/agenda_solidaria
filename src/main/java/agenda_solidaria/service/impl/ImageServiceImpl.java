package agenda_solidaria.service.impl;

import agenda_solidaria.model.Image;
import agenda_solidaria.repository.ImageRepository;
import agenda_solidaria.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Image updateImage(Long id, Image image) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            Image updatedImage = imageOptional.get();
            if (image.getUrl() != null) updatedImage.setUrl(image.getUrl());
            if (image.getDescription() != null) updatedImage.setDescription(image.getDescription());
            if (image.getImageType() != null) updatedImage.setImageType(image.getImageType());
            if (image.getItemType() != null) updatedImage.setItemType(image.getItemType());
            if (image.getItem() != null) updatedImage.setItem(image.getItem());
            return imageRepository.save(updatedImage);
        }
        throw new RuntimeException("Image not found with id: " + id);
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
} 