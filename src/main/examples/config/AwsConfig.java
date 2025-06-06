package ar.com.vwa.extranet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaAsyncClient;
import software.amazon.awssdk.services.lambda.LambdaAsyncClientBuilder;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.S3Presigner.Builder;

@Configuration
@ConfigurationProperties(prefix = "amazon.aws")
public class AwsConfig {

	private String region = "us-east-1";

	private String accessKeyId;
	private String secretAccessKey;
	
	// dynamo
	private String dynamodbEndpoint;
	
	@Value("${extranet.files.s3.bucket}")
	private String filesBucketName;
	
	@Value("${spring.profiles.active}")
	private String activeProfile;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getSecretAccessKey() {
		return secretAccessKey;
	}

	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}
	
	public String getDynamodbEndpoint() {
		return dynamodbEndpoint;
	}
	
	public void setDynamodbEndpoint(String dynamodbEndpoint) {
		this.dynamodbEndpoint = dynamodbEndpoint;
	}
	
	public String getFilesBucketName() {
		return filesBucketName;
	}
	public void setFilesBucketName(String filesBucketName) {
		this.filesBucketName = filesBucketName;
	}
	
	public AwsCredentials amazonAWSCredentials() {
		return AwsBasicCredentials.create(this.getAccessKeyId(), 
				this.getSecretAccessKey());
	}
	
	public void configureCredentialsProvider(AwsClientBuilder<?, ?> builder) {
		if(activeProfile.contains("local"))
			builder.credentialsProvider(StaticCredentialsProvider.create(this.amazonAWSCredentials()));
	}

	public void configureCredentialsProvider(Builder builder) {
		if(activeProfile.contains("local"))
			builder.credentialsProvider(StaticCredentialsProvider.create(this.amazonAWSCredentials()));
	}
	
	@Bean
	public S3Client s3Client() {
		S3ClientBuilder builder = S3Client.builder()
	            .region(Region.of(getRegion()));
		
		this.configureCredentialsProvider(builder);
	    return builder.build();
	}
	
	@Bean
	public S3Presigner presigner() {
		 Builder builder = S3Presigner.builder()
				 .region(Region.of(getRegion()));
		 
		this.configureCredentialsProvider(builder);
		return builder.build();
	}
	
	/* Lambdas */
	@Bean
	public LambdaAsyncClient lambdaClient() {
		LambdaAsyncClientBuilder builder = LambdaAsyncClient.builder()
	            .region(Region.of(this.getRegion()));
		
		this.configureCredentialsProvider(builder);
		return builder.build();
	}
	
	/*
	@Bean
	public SqsClient sqsClient() {
		SqsClientBuilder builder = SqsClient.builder()
				.region(Region.of(this.getRegion()));
		this.configureCredentialsProvider(builder);
		return builder.build();
	}
	*/
}
