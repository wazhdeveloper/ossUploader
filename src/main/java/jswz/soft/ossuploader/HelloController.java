package jswz.soft.ossuploader;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import jswz.soft.ossuploader.utils.OssUploadUtil;

import java.io.File;

public class HelloController {
    @FXML
    private Button commitButton;
    @FXML
    private TextArea urlText;
    @FXML
    private TextArea pathText;
    @FXML
    protected void onButtonClick() {
        String text = pathText.getText();
        String url = onUploadFile(text);
        urlText.setText(url);
    }

    public String onUploadFile(String filePath) {
        OssUploadUtil ossUploadUtil = new OssUploadUtil();
        File file = new File(filePath);
        if (file.exists()) {
            String contentType = ossUploadUtil.getContentType(file);
            return ossUploadUtil.uploadFile(file, contentType);
        }
        return null;
    }
}