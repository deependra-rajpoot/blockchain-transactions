package com.deep.poc;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SimpleTest {
	private static Wallet walletA;
	private static Wallet walletB;
	private static SimpleBlockchain blockchain;

	public static Transaction genesisTransaction;
	

	public static void main(String[] args) {	
		//Setup Bouncey castle as a Security Provider

		Security.addProvider(new BouncyCastleProvider());
		blockchain = new SimpleBlockchain(3);
		//Create the new wallets
		walletA = new Wallet();
		walletB = new Wallet();
		
		
		//Test public and private keys
//		System.out.println("Private and public keys:");
//		System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
//		System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
//		//Create a test transaction from WalletA to walletB 
//		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
//		transaction.generateSignature(walletA.privateKey);
//		//Verify the signature works and verify it from the public key
//		System.out.println("Is signature verified");
//		System.out.println(transaction.verifySignature());
		
		Wallet coinbase = new Wallet();
		
		genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 100);
		genesisTransaction.generateSignature(coinbase.privateKey);	 //manually sign the genesis transaction	
		genesisTransaction.transactionId = "0"; //manually set the transaction id
		
		System.out.println("Creating and Mining Genesis block... ");
		SimpleBlock genesis = new SimpleBlock("0");
		genesis.addTransaction(genesisTransaction);
		blockchain.addBlock(genesis);
		
		//testing
				SimpleBlock block1 = new SimpleBlock(genesis.hash);
				System.out.println("\nWalletA's balance is: " + walletA.balance);
				
				System.out.println("\nWalletA is Attempting to send funds "+40+" to WalletB...");
				block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40));
				
				blockchain.addBlock(block1);
				
				System.out.println("\nWalletA's balance is: " + walletA.balance);
				System.out.println("WalletB's balance is: " + walletB.balance);
				
				SimpleBlock block2 = new SimpleBlock(block1.hash);
				System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
				block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000));
				blockchain.addBlock(block2);
				
				System.out.println("\nWalletA's balance is: " + walletA.balance);
				System.out.println("WalletB's balance is: " + walletB.balance);
				
				SimpleBlock block3 = new SimpleBlock(block2.hash);
				System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
				block3.addTransaction(walletB.sendFunds( walletA.publicKey, 20));
				blockchain.addBlock(block3);
				
				System.out.println("\nWalletA's balance is: " + walletA.balance);
				System.out.println("WalletB's balance is: " + walletB.balance);
				
				for (SimpleBlock b : blockchain.blocks){
					System.out.println("----------------::Block::--------------");
					for(Transaction t: b.transactions){
						t.printTransaction();
					}
				}
				System.out.println(blockchain.isValidBlockchain());				
		
	}
}

