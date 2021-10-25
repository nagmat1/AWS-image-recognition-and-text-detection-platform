import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.rekognition.DetectLabelsService;
import com.example.sqs.SQSService;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.model.Image;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Main {
    final static AmazonS3 S3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
    final static String BUCKET_NAME = "unr-cs442";
    final static String QUEUE_NAME = "unr-cs442";

    public static void main(String[] args) {
        String queueURL = SQSService.createQueue(QUEUE_NAME);
        if (queueURL.equals("")) return;
        System.out.println(queueURL);

        ListObjectsV2Result res = S3.listObjectsV2(BUCKET_NAME);
        List<S3ObjectSummary> objects = res.getObjectSummaries();
        List<String> file_names = new ArrayList<>();

        for (S3ObjectSummary os : objects) {
            file_names.add(os.getKey());
        }

        for (String os : file_names) {
            if (!os.equals("")) {
                S3Object object = S3.getObject(new GetObjectRequest(BUCKET_NAME, os));
                InputStream in = object.getObjectContent();
                System.out.format("File name = %s \n", os);
                SdkBytes sourceBytes = SdkBytes.fromInputStream(in);
                Image souImage = Image.builder()
                        .bytes(sourceBytes)
                        .build();

                if (DetectLabelsService.detectImageLabels(souImage)) {
                    SQSService.sendMessage(queueURL, os);
                    //SQSService.receiveMessages(queueURL).forEach(m-> System.out.println(m.body()));
                }
            }
        }

    }
}
