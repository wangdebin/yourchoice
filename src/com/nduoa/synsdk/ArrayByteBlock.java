package com.nduoa.synsdk;

import java.util.ArrayList;
import java.util.List;

public class ArrayByteBlock {

	private List<ByteBlock> mBlockList;
	
	public ArrayByteBlock(){
		mBlockList = new ArrayList<ByteBlock>();
	}
	
	public void addByteBlock(ByteBlock block){
		mBlockList.add(block);
	}
	
	public void addByteBlock(byte[] byteArr, int startPos, int length){
		mBlockList.add(new ByteBlock(byteArr, startPos, length));
	}
	
	public void addByteBlock(byte[] byteArr){
		mBlockList.add(new ByteBlock(byteArr));
	}
	
	public byte[] toByteArr(){
		int length = 0;
		for(int idx = mBlockList.size() -1; idx >= 0; -- idx){
			length += mBlockList.get(idx).getLength();
		}
		byte[] tmpArr = new byte[length];
		for(int idx = mBlockList.size() - 1; idx >= 0; -- idx){
			int blockLength =  mBlockList.get(idx).getLength();
			int startPos = length - mBlockList.get(idx).getLength();
			System.arraycopy(mBlockList.get(idx).byteArr, 0, tmpArr, startPos, blockLength);
			length = startPos; 
		}
		return tmpArr;
	}
	
	public String toStr(){
		if(mBlockList.size() == 0) return "";
		byte[] tmpArr = toByteArr();
		return new String(tmpArr);
	}
}
