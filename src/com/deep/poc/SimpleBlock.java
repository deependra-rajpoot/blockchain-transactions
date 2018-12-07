package com.deep.poc;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlock {
	public String hash;
	public String previousHash;
	public List<Transaction> transactions;
	public int nonce;
	public String merkleRoot;
	
	public SimpleBlock(String previousHash) {
		transactions = new ArrayList<Transaction>();
		this.previousHash = previousHash;		
		
	}

	public boolean addTransaction(Transaction transaction) {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null)
			return false;		
		if((previousHash != "0")) {
			if((transaction.processTransaction() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				SimpleBlockchain.wallets.get(StringUtils.getStringFromKey(transaction.from)).balance += transaction.amount;
				return false;
			}
		}
		SimpleBlockchain.wallets.get(StringUtils.getStringFromKey(transaction.to)).balance += transaction.amount;
		transactions.add(transaction);		
		System.out.println("Transaction Successfully added to Block");
		
		return true;
	}
	
	public String calculateHash() {		
		return StringUtils.applySha256(previousHash+merkleRoot+nonce);
	}	
	
	public void mineBlock(int difficulty){
		merkleRoot = StringUtils.getMerkleRoot(transactions);
		hash=calculateHash();
		String target = new String(new char[difficulty]).replace('\0', '0');
		while(!hash.startsWith(target)){
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block mined: "+hash);
		System.out.println("PrevHash: "+previousHash+"merkleRoot: "+merkleRoot+"nonce: "+nonce);
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtils.applySha256("0"+"0"+"7986"));
		
	}
}




