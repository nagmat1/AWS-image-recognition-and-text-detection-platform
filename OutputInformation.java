import java.util.List;

public class OutputInformation {
    String fileName;
    List<String> detectedText;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDetectedText(List<String> detectedText) {
        this.detectedText = detectedText;
    }
}
