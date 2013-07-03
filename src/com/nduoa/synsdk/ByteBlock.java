package com.nduoa.synsdk;

public class ByteBlock {

	public ByteBlock(byte[] arr){
		byteArr = new byte[arr.length];
		System.arraycopy(arr, 0, byteArr, 0, arr.length);
	}
	
	public ByteBlock(byte[] arr, int startPos, int length){
		byteArr = new byte[length];
		System.arraycopy(arr, startPos, byteArr, 0, length);
	}
	
	public int getLength(){
		return byteArr.length;
	} 
	
	public byte[] byteArr;
}
