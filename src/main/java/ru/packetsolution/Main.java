package ru.packetsolution;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.File;
import java.util.List;

public class Main {

    static final String bucketName = "sem-backet";

    public static void main(String[] args) {
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentialsProvider.getCredentials()))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                "storage.yandexcloud.net","ru-central1"
                        )
                )
                .build();

        ls(s3, bucketName);
        upload(s3, bucketName, "example.txt", "example.txt");
    }

    static void ls(AmazonS3 s3, String bucketName){

        List<S3ObjectSummary> summaryList = s3.listObjects(bucketName).getObjectSummaries();
        for (S3ObjectSummary obj : summaryList){
            System.out.println(obj);
        }
    }

    static void upload(AmazonS3 s3,
                       String bucketName,
                       String key,
                       String filePath){
        File file = new File(filePath);
        s3.putObject(bucketName, key, file);
    }

}
