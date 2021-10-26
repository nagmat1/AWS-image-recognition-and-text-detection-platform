import com.amazonaws.services.s3.model.S3ObjectInputStream;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.util.LinkedList;
import java.util.List;
// snippet-end:[rekognition.java2.detect_text.import]

/**
 * To run this Java V2 code example, ensure that you have setup your development environment, including your credentials.
 * <p>
 * For information, see this documentation topic:
 * <p>
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class DetectTextService {
    final static Region region = Region.US_EAST_1;
    final static RekognitionClient rekClient = RekognitionClient.builder()
            .region(region)
            .build();

    public static void detectTextLabels(S3ObjectInputStream inputStream, OutputInformation outputInformation) {

        try {
            SdkBytes sourceBytes = SdkBytes.fromInputStream(inputStream);

            // Create an Image object for the source image
            Image souImage = Image.builder()
                    .bytes(sourceBytes)
                    .build();

            DetectTextRequest textRequest = DetectTextRequest.builder()
                    .image(souImage)
                    .build();

            DetectTextResponse textResponse = rekClient.detectText(textRequest);
            List<TextDetection> textCollection = textResponse.textDetections();
            List<String> textDetectedString = new LinkedList<>();

            for (TextDetection td : textCollection) {
                textDetectedString.add(td.detectedText());
            }
            outputInformation.setDetectedText(textDetectedString);
//
//            System.out.println("Detected lines and words");
//            for (TextDetection text: textCollection) {
//                System.out.println("Detected: " + text.detectedText());
//                System.out.println("Confidence: " + text.confidence().toString());
//                System.out.println("Id : " + text.id());
//                System.out.println("Parent Id: " + text.parentId());
//                System.out.println("Type: " + text.type());
//                System.out.println();
//            }

        } catch (RekognitionException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
