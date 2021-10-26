import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import software.amazon.awssdk.services.sqs.model.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    final static AmazonS3 S3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
    final static String BUCKET_NAME = "unr-cs442";
    final static String queueURL = "https://sqs.us-east-2.amazonaws.com/471272029757/unr-cs442";


    public static void main(String[] args) {
        List<Message> messages = SQSService.receiveMessages(queueURL);
        List<OutputInformation> outputInformationList = new LinkedList<>();
        for (Message m : messages) {
            OutputInformation outputInformation = new OutputInformation();
            outputInformationList.add(outputInformation);
            if (m.body().equals("-1")) {break;}
            System.out.println(m.body());
            outputInformation.setFileName(m.body());
            S3Object s3Object = S3.getObject(BUCKET_NAME, m.body());
            DetectTextService.detectTextLabels(s3Object.getObjectContent(), outputInformation);
        }
        outputToFile(outputInformationList);
    }

    static void outputToFile(List<OutputInformation> outputInformationList) {
        try (FileWriter fileWriter = new FileWriter("output.txt");){
            for (OutputInformation o : outputInformationList) {
                fileWriter.write(o.fileName);
                fileWriter.write("\n");
                for (String dt : o.detectedText) {
                    fileWriter.write(dt + ", ");
                }
                fileWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
