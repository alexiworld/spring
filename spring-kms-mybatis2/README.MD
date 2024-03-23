A sample app to encrypt/decrypt data before storing into database using AWS kms. This app uses Spring Boot and MyBatis 2, and demostrates 2 methods of field encryption. The solution creates number of type handlers callback for EncryptedString, HashedString, and LocalDataTime Java types and for fields in the format "enc(vinNumber)". Java type-based type handler callbacks can be found in sqlMapConfig.xml file. Declaration-based type handler callbacks can be found in VehicleEntityDAO, please look for "#field,handler=" or typeHandler="" attribute found in result map declaration.  

AWS KMS uses [envelop encryption](https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#enveloping).

![](diagram.png)
## Step 1 : Enable access
Sometimes access to AWS services may be limited to specific IP address or range of addresses. If so, please ensure you are behind that IP address e.g. connect to corporate network.

## Step 2 : Create symmetric KMS key
Go to AWS KMS service and create a symmetric KMS key. Copy the key ID as you will need it later.

## Step 3 : Generating KMS keys

Generate data key using below command. Refer [AWS documentation](https://docs.aws.amazon.com/cli/latest/reference/kms/generate-data-key.html) for more details.

Run
```bash
aws kms generate-data-key --key-id <key-id> --key-spec AES_256 --profile <can be skipped if AWS_PROFILE set> --region us-west-2
```

## Step 4 : Reflect CiphertextBlob in the code 
Update com.example.springkmsmybatis3.utils.DataKeyProvider class's encryptedData with the returned CiphertextBlob in the previous step. Also, change DataEncryptDecrypt's afterPropertiesSet to use the data key, and not the hard coded local key.

## Step 5 : Run Spring application 

```bash
mvn exec:java -DAWS_PROFILE=<profile> -Dexec.mainClass="com.example.springkmsmybatis3.SpringKMSMyBatis3Application"
```

## Step 6 : Connect to Oracle DB (or H2 DB)
Create a docker PostgreSQL container and exec bash to enter into it, and run psql client to access the DB.

```bash
docker run --rm --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=password -d postgres
docker exec -ti postgres bash
root@a5fb5cc812ad:/# psql -U postgres
```

Open http://localhost:8080/h2-console in a browser, set JDBC URL to jdbc:h2:mem:vehicledb, and specify password as password, and click the Connect button.

## Step 7 : Create VEHICLE table 
For PostgreSQL run:

```bash
CREATE TABLE "VEHICLE"  ("id" SERIAL PRIMARY KEY, "created_by" VARCHAR(255), "created_on" TIMESTAMP, "make" VARCHAR(255), "mode_year" VARCHAR(255), "model" VARCHAR(255), "type" VARCHAR(255), "vin" VARCHAR(255));
```

or for H2 database: 

```bash
CREATE TABLE VEHICLE  ("id" INT PRIMARY KEY auto_increment, "created_by" VARCHAR(255), "created_on" TIMESTAMP, "make" VARCHAR(255), "mode_year" VARCHAR(255), "model" VARCHAR(255), "type" VARCHAR(255), "vin" VARCHAR(255))
```

## Step 8 : Call Vehicle REST API to create a new vehicle entity 

```bash
curl -H "Content-type: application/json" localhost:8080/rest/v1/vehicle -d @payload1.json
```

## Step 9 : Check VEHICLE table 
Switch back to the PSQL client (or for H2 the browser and click the Run button to) rerun the SELECT query. Now, you will see the record with vin and created by being encrypted using respectively Method #1 and #2.

## Step 10 : Call Vehicle REST API to fetch previously created record. 

```bash
curl localhost:8080/rest/v1/vehicle/1 and you will see the record being returned.
```

Sources:

+ https://stackoverflow.com/questions/18507508/mybatis-how-to-get-the-auto-generated-key-of-an-insert-mysql
+ https://stackoverflow.com/questions/15456033/returning-values-from-mybatis-insert-mapped-methods
