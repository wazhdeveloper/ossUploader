module jswz.soft.ossuploader {
    requires javafx.controls;
    requires javafx.fxml;
    requires aliyun.sdk.oss;


    opens jswz.soft.ossuploader to javafx.fxml;
    exports jswz.soft.ossuploader;
}