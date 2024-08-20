This GitHub repository "AWS Image Recognition and Text Detection Platform" demonstrates a project that sets up two AWS EC2 instances. The first instance retrieves files from an S3 bucket, performs image recognition, and sends filenames with a high confidence of detecting cars (>90%) to an SQS queue. The second instance reads these filenames from the SQS queue, performs text detection on the images, and writes the detected text to a file. The project is built using Java and AWS services.

# AWS image recognition and text detection platform

Aws image recongnition and text recognition

For aws credentials: 
Create ``` ~/.aws/credentials ``` and edit 
[default]
aws_access_key_id =AKIAIOSFODNN7EXAMPLE 
aws_secret_access_key=<place_holder>

# Project Definition : 

In this Project we create 2 instances on AWS cloud.

Instance1 retrieves files from S3 bucket and runs image recognition, then sends file names to SQS queue service where we have car confidence>90%. 

Instance2 retrives file names from SQS and runs text detection and writes the data to file. 
