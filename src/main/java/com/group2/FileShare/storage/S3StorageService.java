package com.group2.FileShare.storage;


import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class S3StorageService implements IStorage {

    private static S3StorageService service = null;
    private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    private Bucket s3_bucket;


    private S3StorageService() {
    	//TODO should get bucket_name from configuration class
		String bucket_name = "file-share-app";
		s3_bucket = createS3Bucket(bucket_name);
    }
    
    public S3StorageService getInstance() {
    	if (service == null) {
    		service = new S3StorageService();
    	}
    	return service;
    }
    
    private Bucket createS3Bucket(String bucket_name) {
    	Bucket b = null;
    	if (s3.doesBucketExist(bucket_name)) {
    	    System.out.format("Bucket %s already exists.\n", bucket_name);
    	    b = getBucket(bucket_name);
    	} else {
    	    System.out.format("Bucket %s doesnt exist.\n", bucket_name);
    	    try {
    	        b = s3.createBucket(bucket_name);
    	    } catch (AmazonS3Exception e) {
    	        System.err.println(e.getErrorMessage());
    	    }
    	}
    	return b;
    }
    
    
    private Bucket getBucket(String bucket_name) {
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }

	@Override
	public String downloadFile(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadFile(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

    
}