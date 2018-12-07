package com.deep.poc;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

public class Transaction {

	public String transactionId;

	public PublicKey from;
	public PublicKey to;
	public int amount;
	public byte[] signature;

	private static int sequence;

	public Transaction(PublicKey from, PublicKey to, int amount) {
		super();
		this.from = from;
		this.to = to;
		this.amount = amount;
		transactionId = calculateHash();
	}

	private String calculateHash() {
		sequence++; // increase the sequence to avoid 2 identical transactions
					// having the same hash
		return StringUtils
				.applySha256(StringUtils.getStringFromKey(from) + StringUtils.getStringFromKey(to) + amount + sequence);
	}

	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtils.getStringFromKey(from) + StringUtils.getStringFromKey(to) + amount;
		signature = StringUtils.applyECDSASig(privateKey, data);
	}

	public boolean verifySignature() {
		String data = StringUtils.getStringFromKey(from) + StringUtils.getStringFromKey(to) + amount;
		return StringUtils.verifyECDSASig(from, data, signature);
	}

	public boolean processTransaction() {

		if (verifySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
		return true;
	}
	public void printTransaction(){
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "-----------------\n TransactionId: " + transactionId + "\n From        : " + from + "\n To           :" + to + "\n Amount       :" + amount
				+ "\n Signature    :" + Arrays.toString(signature) + "\n-------------------------------------";
	}

}
