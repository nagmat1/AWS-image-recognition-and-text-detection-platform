# UNRCS642_aws_ass1
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
