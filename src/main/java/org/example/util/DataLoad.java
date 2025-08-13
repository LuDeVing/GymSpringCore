package org.example.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.facade.GymFacade;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class DataLoad {

    private final GymFacade gym;

    @Value("${storage.file}")
    private String storagePath;

    private static final Logger logger = LoggerFactory.getLogger(DataLoad.class);


    @Autowired
    public DataLoad(GymFacade gym){
        this.gym = gym;
    }

    @PostConstruct
    public void getData() throws IOException {
        logger.info("getData() called, path={}", storagePath);

        InputStream is = null;

        try {
            if (storagePath == null) {
                logger.warn("No initial data resource found");
                return;
            }

            // Check if path starts with "classpath:" prefix
            if (storagePath.startsWith("classpath:")) {
                String path = storagePath.substring("classpath:".length());
                is = getClass().getClassLoader().getResourceAsStream(path);
                if (is == null) {
                    logger.warn("Classpath resource not found: {}", path);
                    return;
                }
            } else {
                // Otherwise treat as file system path
                is = new java.io.FileInputStream(storagePath);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(is);

            if (root.has("trainers")) {
                for (JsonNode t : root.get("trainers")) {
                    gym.createTrainer(
                            t.path("firstName").asText(""),
                            t.path("lastName").asText(""),
                            t.path("active").asBoolean(true),
                            t.path("specialization").asText("")
                    );
                }
            }

            if (root.has("trainees")) {
                for (JsonNode t : root.get("trainees")) {
                    gym.createTrainee(
                            t.path("firstName").asText(""),
                            t.path("lastName").asText(""),
                            t.path("active").asBoolean(false),
                            java.time.LocalDate.parse(t.path("dateOfBirth").asText("1970-01-01")),
                            t.path("address").asText("")
                    );
                }
            }

            if (root.has("trainings")) {
                for (JsonNode t : root.get("trainings")) {
                    gym.createTraining(
                            t.path("traineeId").asLong(0),
                            t.path("trainerId").asLong(0),
                            t.path("trainingName").asText(""),
                            t.path("trainingType").path("trainingTypeName").asText(""),
                            java.time.LocalDate.parse(t.path("trainingDate").asText("1970-01-01")),
                            t.path("trainingDuration").asInt(0)
                    );
                }
            }


            logger.info("Storage initialized from file");

        } catch (IOException e) {
            logger.warn("Initial data was not loaded because of IOException", e);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


}
