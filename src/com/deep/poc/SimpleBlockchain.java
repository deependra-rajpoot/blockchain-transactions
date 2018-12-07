package com.deep.poc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleBlockchain {

	public List<SimpleBlock> blocks;
	public int difficulty ;
	public static HashMap<String,Wallet> wallets = new HashMap<String,Wallet>();
	
	public SimpleBlockchain(int difficulty) {
		blocks = new ArrayList<SimpleBlock>();
		this.difficulty = difficulty;
	}
	
	public boolean addBlock(SimpleBlock block){
		block.mineBlock(difficulty);
		return blocks.add(block);
	}
	
	public boolean isValidBlockchain(){
		boolean isValid = true;
		for(int i=0; i<blocks.size()-1;i++){
			if (!blocks.get(i).hash .equals( blocks.get(i+1).previousHash)){
				System.out.println(blocks.get(i).hash);
				System.out.println("Hash of previous block and previous hash of current block do not match");
				System.out.println(blocks.get(i+1).hash);
				isValid = false;
				//break;
			}
			if(!blocks.get(i).hash.equals(blocks.get(i).calculateHash())){
				System.out.println(blocks.get(i).hash );
				System.out.println("Hash and calculated hash of current block do not match");
				System.out.println(blocks.get(i).calculateHash()+"\n"+ blocks.get(i).previousHash+"\n"+ blocks.get(i).nonce+"\n"+blocks.get(i).merkleRoot);
				System.out.println();
				isValid = false;
				//break;
			}
		}
		return isValid;
	}
}







