package jswz.soft.ossuploader.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;

import java.io.File;


/**
 * @author wazh
 * @since 2023-06-15-19:50
 */
public class OssUploadUtil {
    private String endpoint = "https://oss-cn-nanjing.aliyuncs.com";
    private String accessKeyId = "";
    private String accessKeySecret = "";
    private String bucketName = "edu-nuist";

    public Boolean existBucket() {
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        boolean exist = oss.doesBucketExist(bucketName);
        oss.shutdown();
        return exist;
    }

    public void createBucket() {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 创建CreateBucketRequest对象。
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            // 如果创建存储空间的同时需要指定存储类型、存储空间的读写权限、数据容灾类型。
            createBucketRequest.setStorageClass(StorageClass.Standard);//标准存储
            // 设置存储空间读写权限为公共读，默认为私有。
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            // 创建存储空间。
            ossClient.createBucket(createBucketRequest);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public String uploadFile(File file, String contentType) {
        if (!existBucket()) createBucket();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String YEAR = DataUtil.getYear();
        String MONTH = DataUtil.getMonth();
        String DAY = DataUtil.getDay();
        String objectPath = YEAR + "/" + MONTH + "/" + DAY + "/" + file.getName();//上传到oss的路径
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, objectPath);
            // 指定上传的本地文件。
            uploadFileRequest.setUploadFile(file.toString());
            // 指定上传并发线程数，默认为1。
            uploadFileRequest.setTaskNum(5);
            // 指定上传的分片大小，范围为100KB~5GB，默认为文件大小/10000。
            uploadFileRequest.setPartSize(10 * 1024 * 1024);
            // 开启断点续传，默认关闭。
            uploadFileRequest.setEnableCheckpoint(true);
            uploadFileRequest.setCheckpointFile("uploadFile.ucp");
            // 文件的元数据。
            uploadFileRequest.setObjectMetadata(meta);
            // 断点续传上传。
            ossClient.uploadFile(uploadFileRequest);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return endpoint.replaceFirst("https://", "https://" + bucketName + ".") + "/" + objectPath;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     */
    public String getContentType(File file) {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        switch (fileExtension) {
            case ".bmp":
                return "image/bmp";
            case ".gif":
                return "image/gif";
            case ".jpeg":
                return "image/jpeg";
            case ".jpg":
                return "image/jpg";
            case ".png":
                return "image/png";
            case ".txt":
                return "text/plain";
            case ".vsd":
                return "application/vnd.visio";
            case ".ppt":
            case ".pptx":
                return "application/vnd.ms-powerpoint";
            case ".doc":
            case ".docx":
                return "application/msword";
            case ".xml":
                return "text/xml";
            case ".mp4":
                return "video/mp4";
            case ".mp3":
                return "audio/mp3";
            default:
                return "text/html";
        }
    }
}
