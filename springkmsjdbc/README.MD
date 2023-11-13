
+ If you need to be behind corporate network, please first connect so you can use AWS services.
+ Create AWS KMS symmetric key
+ Run
  aws kms generate-data-key --key-id &lt;key-id&gt; --key-spec AES_256 --profile &lt;can be skipped if AWS_PROFILE set&gt; --region us-west-2
+ Update com.example.springkmsjdbc.utils.DataKeyProvider class's encryptedData with the returned CiphertextBlob in the previous step.
+ Run mvn exec:java -DAWS_PROFILE=&lt;profile&gt; -Dexec.mainClass="com.example.springkmsjdbc.SpringKMSJDBCApplication"
+ Open http://localhost:8080/h2-console in a browser, set JDBC URL to jdbc:h2:mem:vehicledb, and specify password as password, and click the Connect button.
+ Select VEHICLE table, you will see SELECT statement being filled in on the right. Click the Run button. Table is empty.
+ Run curl -H "Content-type: application/json" localhost:8080/rest/v1/vehicle -d @payload.json
+ Switch back to the browser and click the Run button to rerun the SELECT query. Now, you will see the record with vin being encrypted.
+ Run curl localhost:8080/rest/v1/vehicle/1 and you will see the record being returned.
  ~                                                                                        