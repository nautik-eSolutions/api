package com.nautik.api.service.port;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.PortImage;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.dto.port.PortImageDto;
import com.nautik.api.repository.port.PortImageRepository;
import com.nautik.api.repository.port.PortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3PortImageService {
    // valors autowired
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final PortRepository portRepository;
    private final PortImageRepository portImageRepository;

    @Value("${application.aws.s3.bucket:}")
    private String bucket;

    @Value("${application.aws.s3.folder:images}")
    private String folder;

    @Value("${application.aws.s3.presigned-duration-minutes:60}")
    private long presignedDurationMinutes;

    public PortImageDto uploadPortImage(Integer portId, MultipartFile file) {
        validateImage(file);

        Port port = portRepository.findById(portId)
                .orElseThrow(() -> new EntityNotFoundException("Port not found exception"));

        String key = buildPortImageKey(portId, file.getOriginalFilename());

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read the uploaded image", e);
        } catch (S3Exception e) {
            throw new IllegalStateException("An error occurred while uploading the image: " + e.awsErrorDetails().errorMessage(), e);
        }

        PortImage portImage = new PortImage(port, key);
        portImageRepository.save(portImage);

        String presignedUrl = getPresignedUrl(key);
        return new PortImageDto(portImage.getId(), key, presignedUrl);
    }

    public List<PortImageDto> getPortImages(Integer portId) {
        return portImageRepository.findAllByPortId(portId)
                .stream()
                .map(img -> new PortImageDto(img.getId(), img.getImageKey(), getPresignedUrl(img.getImageKey())))
                .toList();
    }

    public String getPresignedUrl(String key) {
        if (!StringUtils.hasText(key)) return null;

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(Math.max(1, presignedDurationMinutes)))
                    .getObjectRequest(getObjectRequest)
                    .build();

            return s3Presigner.presignGetObject(presignRequest).url().toString();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot generate signed url", e);
        }
    }

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("You need to upload at least one image");
        }
        if (!StringUtils.hasText(file.getContentType()) || !file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }
    }

    private String buildPortImageKey(Integer portId, String originalFilename) {
        String extension = "";
        if (StringUtils.hasText(originalFilename)) {
            int lastDot = originalFilename.lastIndexOf('.');
            if (lastDot >= 0) {
                extension = originalFilename.substring(lastDot);
            }
        }
        String objectFolder = StringUtils.hasText(folder) ? folder : "images";
        return objectFolder + "/ports/" + portId + "/" + UUID.randomUUID() + extension;
    }
}