package com.group2.FileShare.storage;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.io.File;
import java.net.URL;
import java.util.List;

public class S3StorageService implements IStorage {

	private static S3StorageService service = null;
	private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
	private Bucket s3_bucket;
	private long linkValidityMillis = 0;

	private S3StorageService() {
		String bucket_name;
		if (System.getProperty("FILESHARE_S3_BUCKET_NAME") == null || System.getProperty("FILESHARE_S3_BUCKET_NAME").isEmpty()) {
			// TODO should load default bucket_name from configuration class
			bucket_name = "csci5308-file-share-app";
		} else {
			bucket_name = System.getProperty("FILESHARE_S3_BUCKET_NAME");
		}

		s3_bucket = createS3Bucket(bucket_name);
		// TODO should load linkValidityMillis from configuration class
		linkValidityMillis = 1000 * 60 * 60;
	}

	public static S3StorageService getInstance() {
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
	public String downloadFile(String objectKey) {
		return generateS3PreSignedURL(objectKey);
	}

	@Override
	public boolean uploadFile(File file, String filename) {
		try {
			s3.putObject(s3_bucket.getName(), filename, file);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteFile(String objectKey) {
		String bucketName = s3_bucket.getName();
		try {
			s3.deleteObject(bucketName, objectKey);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			return false;
		}
		return true;
	}

	private String generateS3PreSignedURL(String objectKey) {
		String bucketName = s3_bucket.getName();
		String presignedurl = "";
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += linkValidityMillis;
		expiration.setTime(expTimeMillis);

		try {
			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
					objectKey).withMethod(HttpMethod.GET).withExpiration(expiration);
			URL url = s3.generatePresignedUrl(generatePresignedUrlRequest);
			presignedurl = url.toString();
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}

		return presignedurl;
	}

}