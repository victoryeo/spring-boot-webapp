package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.crypto.Credentials;
import java.util.concurrent.ExecutionException;
import java.math.BigInteger;

@EnableJpaRepositories("com.example.demo.persistence.repo") 
@EntityScan("com.example.demo.persistence.model")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);


    Web3j web3j = Web3j.build(new HttpService(
      "https://long-orbital-orb.ethereum-goerli.discover.quiknode.pro/f1cbbcf0a86270f3293d0c177ca7c407198f1d74/")); 
    //for testing with local ganache
    //Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));
    
    //EthBlockNumber result = new EthBlockNumber();
    try {
      EthBlockNumber result = web3j.ethBlockNumber().sendAsync().get();
      System.out.println(" The Block Number is: " + result.getBlockNumber().toString());
    } catch (InterruptedException e) {
      System.out.println(e);
    } catch (ExecutionException e) {
      System.out.println(e);
    }

    String privateKey = "<pk>";
    Credentials credentials = Credentials.create(privateKey);
    Mycontract contract = Mycontract.load(
        "0x6B536ED22412275ed260ea97F2a77f3e0d55BBdF", 
        web3j, 
        credentials, 
        BigInteger.valueOf(100), 
        BigInteger.valueOf(100)
    );
    try {
      Mycontract.ShareDocs transactionReceipt = contract.getCompanyName(BigInteger.valueOf(1)).send();
      System.out.println("Contract return " + transactionReceipt);
    } catch (ExecutionException e) {
      System.out.println(e);
    } catch (Exception e) {
      System.out.println(e);
    }

    web3j.shutdown();
	}

}
